package com.pluspow.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slim3.datastore.Datastore;
import org.slim3.memcache.Memcache;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.constants.MemcacheKey;
import com.pluspow.dao.SpotDao;
import com.pluspow.enums.DayOfWeek;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.DataMismatchException;
import com.pluspow.exception.GeocodeStatusException;
import com.pluspow.exception.GeocoderLocationTypeException;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.meta.SpotMeta;
import com.pluspow.model.Client;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.SpotTextRes;
import com.pluspow.model.TransCredit;
import com.pluspow.utils.Utils;


public class SpotService {
    
    /** DAO */
    private static final SpotDao dao = new SpotDao();

    
    /**
     * スポットの追加(ステップ１の設定 永久化しない)
     * @param spot
     * @param spotId
     * @param address
     * @param phoneNumber
     * @param email
     * @param geoModel
     */
    public static Spot setStep1(
            Client client,
            String spotId, 
            Lang lang,
            String address, 
            String phoneNumber, 
            String email,
            GeoModel geoModel) {
        
        Spot model = new Spot();
        model.setSpotId(spotId);
        model.setBaseLang(lang);
        model.setEmail(new Email(email));
        
        // クライアントとの関連
        model.getClientRef().setModel(client);
        
        // 言語情報の設定
        model.setLangUnit(SpotLangUnitService.getNewModel(model, lang, phoneNumber, geoModel));
        
        // テキストリソースの設定
        SpotTextRes addressRes = new SpotTextRes();
        addressRes.setStringToContent(address);
        addressRes.setRole(TextResRole.SPOT_ADDRESS);
        model.setAddressRes(addressRes);
        
        return model;
    }
    
    /**
     * スポットの追加(ステップ２の設定 永久化しない)
     * @param spotId
     * @param address
     * @param phoneNumber
     * @param email
     * @return
     */
    public static void setStep2(Spot model, String name, String catchCopy, String content) {
        
        // 名前
        SpotTextRes nameRes = new SpotTextRes();
        nameRes.setStringToContent(name);
        nameRes.setRole(TextResRole.SPOT_NAME);
        model.setNameRes(nameRes);
        
        // キャッチフレーズ
        SpotTextRes catchCopyRes = new SpotTextRes();
        catchCopyRes.setStringToContent(catchCopy);
        catchCopyRes.setRole(TextResRole.SPOT_CATCH_COPY);
        model.setCatchCopyRes(catchCopyRes);
        
        // 詳細
        SpotTextRes detailRes = new SpotTextRes();
        detailRes.setStringToContent(content);
        detailRes.setRole(TextResRole.SPOT_DETAIL);
        model.setDetailRes(detailRes);
    }
    
    /**
     * Freeプランスタート
     * @param model
     * @param template
     * @param client
     * @return
     * @throws TooManyException
     */
    public static Spot startFreePlan(
            Spot spot,
            Client client) throws TooManyException {
        
        // SpotIdの重複確認(時間差で登録される場合があるため、念のための最終チェック)
        if(dao.getBySpotId(spot.getSpotId()) != null) {
            throw new TooManyException("スポットIdが既に登録されています。");
        }
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // スポットキーの作成
            Key spotKey = createKey();
            spot.setKey(spotKey);
            
            // 翻訳貯蓄の追加
            TransCredit credit = TransCreditService.add(tx, spot);
            
            // スポットの保存
            spot.getTransCreditRef().setModel(credit);
            Datastore.put(tx, spot);
            
            // 言語情報の追加
            SpotLangUnitService.addBaseLang(tx, spot, spot.getLangUnit());
    
            // テキストリソースの追加
            SpotTextRes nameRes = spot.getNameRes();
            SpotTextResService.add(tx, spot, spot.getBaseLang(), TextResRole.SPOT_NAME, nameRes.getContentString());
            
            SpotTextRes catchCopyRes = spot.getCatchCopyRes();
            SpotTextResService.add(tx, spot, spot.getBaseLang(), TextResRole.SPOT_CATCH_COPY, catchCopyRes.getContentString());
            
            SpotTextRes detailRes = spot.getDetailRes();
            SpotTextResService.add(tx, spot, spot.getBaseLang(), TextResRole.SPOT_DETAIL, detailRes.getContentString());
            
            SpotTextRes addressRes = spot.getAddressRes();
            SpotTextResService.add(tx, spot, spot.getBaseLang(), TextResRole.SPOT_ADDRESS, addressRes.getContentString());
            
            // 営業時間の追加
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.MON);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.TUE);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.WED);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.THU);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.FRI);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.SAT);
            OfficeHoursService.addDefault(tx, spot, DayOfWeek.SUN);
            
            tx.commit();
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }

        return spot;
    }
    
    /**
     * キーから対処のスポットを取得
     * @param spotId
     * @return
     * @throws ObjectNotExistException 
     */
    public static Spot getSpotModelOnly(String spotId) throws ObjectNotExistException {

        Spot spot = dao.getBySpotId(spotId);
        if(spot == null) throw new ObjectNotExistException();
        
        return spot;
    }
    
    /**
     * スポットの取得(ベース言語)
     * @param spotId
     * @return
     * @throws ObjectNotExistException 
     */
    public static Spot getSpotBaseLang(String spotId) throws ObjectNotExistException {
        Spot spot = getSpotModelOnly(spotId);
        
        return getSpot(spotId, spot.getBaseLang());
    }
    
    /**
     * スポットを取得
     * @param keyString
     * @return
     * @throws NoContentsException 
     */
    public static Spot getSpot(String spotId, Lang lang) throws ObjectNotExistException {
        
        Spot model = Memcache.get(MemcacheKey.getSpotKey(spotId, lang));
        if(model != null) return model;

        model = getSpotModelOnly(spotId);
        
        // 付属情報の追加
        setSpotInfo(model, lang);

        return model;
    }
    
    /**
     * スポットの付属情報の設定
     * @param spot
     * @param lang
     * @throws ObjectNotExistException 
     */
    private static void setSpotInfo(Spot spot, Lang lang) throws ObjectNotExistException {
        
        // ---------------------------------------------------
        // 言語情報の設定
        // ---------------------------------------------------
        List<SpotLangUnit> langUnitList = SpotLangUnitService.getList(spot);
        for(SpotLangUnit langUnit: langUnitList) {
            // サーポート言語リストの設定
            if(!langUnit.isInvalid() 
                    && langUnit.getTransStatus() == TransStatus.TRANSLATED
                    ) {
                spot.getLangs().add(langUnit.getLang());
            }
            
            // 言語情報の設定
            if(langUnit.getLang() == lang) {
                spot.setLangUnit(langUnit);
            }
        }
        
        // ---------------------------------------------------
        // テキストリソースの設定
        // ---------------------------------------------------
        spot.setTextResources(SpotTextResService.getResourcesMap(spot, lang));
        
        // ---------------------------------------------------
        // GCS
        // ---------------------------------------------------
        spot.setGcsResources(SpotGcsResService.getResourcesMap(spot, lang));

        // ---------------------------------------------------
        // 営業時間
        // ---------------------------------------------------
        spot.setOfficeHourList(OfficeHoursService.getOfficeHourList(spot));
    }
    
    /**
     * クライアントからベース言語スポットリストを取得
     * @return
     * @throws ObjectNotExistException 
     */
    public static List<Spot> getSpotListByClient(Client client) throws ObjectNotExistException {
        
        List<Spot> modelOnlylist = dao.getSpotListByClient(client);
        if(modelOnlylist == null) new ObjectNotExistException();
        
        List<Spot> list = new ArrayList<Spot>();
        for(Spot spot: modelOnlylist) {
            Spot spotModel;
            try {
                spotModel = getSpotBaseLang(spot.getSpotId());
                list.add(spotModel);
            } catch (ObjectNotExistException e) {}
        }
        
        return list;
    }
    
    /**
     * 機械翻訳
     * @param spot
     * @param transLang
     * @throws ArgumentException
     * @throws TransException
     * @throws IOException
     * @throws DataMismatchException 
     */
    public static void machineTrans(
            Spot spot,
            Lang transLang) throws ArgumentException, TransException, IOException, DataMismatchException {
        
        if(spot.getBaseLang() == transLang) throw new ArgumentException();
        
        // ---------------------------------------------------
        // 翻訳するコンテンツリスト
        // ---------------------------------------------------
        List<SpotTextRes> transContentsList;
        try {
            transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());

        } catch (ObjectNotExistException e2) {
            // 翻訳するコンテンツがなければそのまま終了
            return;
        }
        
        // ---------------------------------------------------
        // 翻訳文字数のカウント
        // ---------------------------------------------------
        int transCharCount = 0;
        for(SpotTextRes transcontents: transContentsList) {
            if(transcontents.getRole().isTransTarget()) {
                transCharCount = transCharCount + transcontents.getContentString().length();
            }
        }
        
        // ---------------------------------------------------
        // 翻訳処理(Google API)
        // ---------------------------------------------------
        String translatedContents;
        try {
            translatedContents = TransService.machineTrans(
                spot.getBaseLang(),
                transLang,
                transContentsList);
        } catch (IOException e1) {
            throw new TransException(e1);
        }
        
        // ---------------------------------------------------
        // 翻訳結果の保存
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // HTMLに変換
            Document transResult = Jsoup.parse(translatedContents);

            try {
                SpotLangUnitService.get(spot, transLang);
                
                // ---------------------------------------------------
                // 再翻訳の保存処理
                // ---------------------------------------------------
                langReTrans(tx, spot, transLang, transContentsList, transResult);

            } catch (ObjectNotExistException e) {
                // ---------------------------------------------------
                // 新規翻訳の保存処理
                // ---------------------------------------------------
                addLang(tx, spot, transLang, transContentsList, transResult);
            }
            
            // 翻訳クレジットの更新
            TransCreditService.update(
                tx, 
                spot, 
                transCharCount, 
                transCharCount * TransType.MACHINE.getPrice());

            // 翻訳履歴の追加
            TransHistoryService.add(
                tx, 
                spot, 
                spot.getBaseLang(), 
                transLang, 
                TransType.MACHINE, 
                TransStatus.TRANSLATED, 
                transCharCount);

            // コミット
            tx.commit();
            
        } catch (TooManyException e) {
            throw new TransException("データー不整合");
            
        } catch(GeocodeStatusException e) {
            throw new TransException("住所の再取得に失敗しました。");
            
        } catch(GeocoderLocationTypeException e) {
            throw new TransException("住所が不完全なため、再取得できない。");
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }

    }
    
    /**
     * 言語の追加
     * @param tx
     * @param spot
     * @param transLang
     * @param transContentsList
     * @param transResult
     * @throws IOException
     * @throws GeocodeStatusException
     * @throws GeocoderLocationTypeException
     * @throws ArgumentException
     * @throws TooManyException
     */
    private static void addLang(
            Transaction tx,
            Spot spot, 
            Lang transLang,
            List<SpotTextRes> transContentsList,
            Document transResult) throws IOException, GeocodeStatusException, GeocoderLocationTypeException, ArgumentException, TooManyException {

        // ---------------------------------------------------
        // 住所の対象する言語で再取得
        // ---------------------------------------------------
        GeoModel geoModel = GeoService.getGeoModel(spot.getAddress(), transLang);

        // ---------------------------------------------------
        // 翻訳したテキストリソースを追加
        // ---------------------------------------------------
        for(SpotTextRes textRes: transContentsList) {

            // 翻訳対象の場合
            if(textRes.getRole().isTransTarget()) {
                // 改行が含まれるため、text()ではなくhtml()で取得する
                String tcText = transResult.getElementById(textRes.getKey().getName()).html();

                // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
                String strTmp = Utils.clearTextIndention(tcText);
                String content = Utils.changeBrToTextIndention(strTmp);

                SpotTextResService.add(tx, spot, transLang, textRes.getRole(), content);
            }
        }

        // ---------------------------------------------------
        // 住所のテキストリソースはGMOのフォーマットアドレスを使用
        // ---------------------------------------------------
        SpotTextResService.add(tx, spot, transLang, TextResRole.SPOT_ADDRESS, geoModel.getFormattedAddress());

        // ---------------------------------------------------
        // 言語情報の追加
        // ---------------------------------------------------
        SpotLangUnitService.add(tx, spot, transLang, TransType.MACHINE, TransStatus.TRANSLATED, geoModel);

        // ---------------------------------------------------
        // Gcsリソースの複製
        // ---------------------------------------------------
        SpotGcsResService.replicationOtherLangRes(tx, spot, transLang);
    }
    
    /**
     * 再翻訳
     * @param spot
     * @throws ObjectNotExistException 
     * @throws TransException
     * @throws DataMismatchException 
     */
    private static void langReTrans(
            Transaction tx,
            Spot spot, 
            Lang transLang,
            List<SpotTextRes> transContentsList,
            Document transResult) throws TransException, DataMismatchException {

        // ---------------------------------------------------
        // 対象言語のテキストリソースマップを取得
        // ---------------------------------------------------
        Map<String, SpotTextRes> resMap;
        try {
            resMap = SpotTextResService.getResourcesMap(spot, transLang);
            
        } catch (ObjectNotExistException e) {
            throw new DataMismatchException();
        }

        // ---------------------------------------------------
        // 再翻訳対象のリソースMAPを取得
        // ---------------------------------------------------
        for(SpotTextRes textRes: transContentsList) {

            // 翻訳対象の場合
            if(textRes.getRole().isTransTarget()) {
                // 改行が含まれるため、text()ではなくhtml()で取得する
                String tcText = transResult.getElementById(textRes.getKey().getName()).html();

                // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
                String strTmp = Utils.clearTextIndention(tcText);
                String content = Utils.changeBrToTextIndention(strTmp);

                // 対象のリソースをリソースマップから取得し、内容を差し替える
                SpotTextRes spotTextRes = resMap.get(textRes.getRole().toString());
                spotTextRes.setStringToContent(content);

                // リソースを更新
                Datastore.put(tx, spotTextRes);
            }
        }
    }
    
    /**
     * 言語の有効無効切り替え
     * @param spot
     * @param lang
     * @param invalid
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     */
    public static void setInvalid(Spot spot, Lang lang, boolean invalid) throws ObjectNotExistException, ArgumentException {
        
        // ベース言語の有効無効切り替えはできない。
        if(spot.getBaseLang() == lang) throw new ArgumentException();
        
        SpotLangUnit langUnit = SpotLangUnitService.get(spot, lang);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            langUnit.setInvalid(invalid);
            Datastore.put(tx, langUnit);
            
            // コミット
            tx.commit();
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * 削除
     * @param spot
     */
    public static void delete(Spot spot) {
        
        Transaction tx = Datastore.beginTransaction();
        try {
            // スポットを無効にする
            spot.setInvalid(true);
            Datastore.put(tx, spot);
            
            // TODO: テキスト検索Documentの削除
            
            // コミット
            tx.commit();
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey() {
        return Datastore.allocateId(SpotMeta.get());
    }

}
