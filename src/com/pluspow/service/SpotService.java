package com.pluspow.service;

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
import com.pluspow.enums.ServicePlan;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.exception.UnsuitableException;
import com.pluspow.meta.SpotMeta;
import com.pluspow.model.Client;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotPayPlan;
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
        
        // 言語リストに母国語を追加
        model.getLangs().add(lang);
        
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
     * @param keyString
     * @return
     */
    public static Spot getSpotModelOnly(String spotId) {

        try {
            return dao.getBySpotId(spotId);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * スポットの付属情報の設定
     * @param spot
     * @param lang
     */
    private static void setSpotInfo(Spot spot, Lang lang) {
        // 貯蓄の設定
        spot.setTransAcc(TransCreditService.get(spot));
        // 言語情報の設定
        spot.setLangUnit(SpotLangUnitService.get(spot, lang));
        // 翻訳コンテンツの設定
        spot.setTextResources(SpotTextResService.getResourcesMap(spot, lang));
        // プラン
        SpotPayPlan payPlan = SpotPayPlanService.getPlan(spot);
        spot.setPlan(payPlan == null ? ServicePlan.FREE : payPlan.getPlan());
        // GCS
        spot.setGcsResources(SpotGcsResService.getResourcesMap(spot));
        // 営業時間
        spot.setOfficeHourList(OfficeHoursService.getOfficeHourList(spot));
    }
    
    /**
     * スポットを取得
     * @param keyString
     * @return
     */
    public static Spot getSpot(String spotId, Lang lang) {
        
        Spot model = Memcache.get(MemcacheKey.getSpotKey(spotId, lang));
        if(model != null) return model;

        model = dao.getBySpotId(spotId, lang);
        if(model == null) return null;
        
        // サポートしていない言語の場合
        if(model.getLangs().indexOf(lang) < 0) return null;
        
        // 付属情報の追加
        setSpotInfo(model, lang);

        return model;
    }
    
    /**
     * スポットの取得(ベース言語)
     * @param spotId
     * @return
     */
    public static Spot getSpot(String spotId) {
        Spot spot = getSpotModelOnly(spotId);
        if(spot == null) return null;
        
        return getSpot(spotId, spot.getBaseLang());
    }
    
    /**
     * クライアントからベース言語スポットリストを取得
     * @return
     */
    public static List<Spot> getSpotListByClient(Client client) {
        
        List<Spot> modelOnlylist = dao.getSpotListByClient(client);
        if(modelOnlylist == null) return new ArrayList<Spot>();
        
        List<Spot> list = new ArrayList<Spot>();
        for(Spot spot: modelOnlylist) {
            Spot spotModel = getSpot(spot.getSpotId(), spot.getBaseLang());
            if(spotModel != null) {
                list.add(spotModel);
            }
        }
        
        return list;
    }
    
    /**
     * リアルタイム機械翻訳
     * @param otherLang
     * @return
     * @throws UnsuitableException 
     * @throws TransException 
     * @throws TooManyException 
     */
    public static void machineRealTrans(
            Spot spot, 
            Lang transLang) 
            throws UnsuitableException, TransException, TooManyException {
        
        if(spot.getLangs().indexOf(transLang) >= 0) throw new TooManyException("この言語は既に追加されています。");
        
        try {
            // 翻訳するコンテンツリスト
            List<SpotTextRes> transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());
            
            int transCharCount = 0;
            for(SpotTextRes transcontents: transContentsList) {
                if(transcontents.getRole().isTransTarget()) {
                    transCharCount = transCharCount + transcontents.getContentString().length();
                }
            }
            
            // ---------------------------------------------------
            // 翻訳処理
            // ---------------------------------------------------
            String translatedContents = TransService.machineTrans(
                spot.getBaseLang(),
                transLang,
                transContentsList);
            
            // 翻訳結果の取得
            Document document = Jsoup.parse(translatedContents);
            
            // ---------------------------------------------------
            // 住所の対象する言語で再取得
            // ---------------------------------------------------
            GeoModel geoModel = GeoService.getGeoModel(spot.getAddress(), transLang);
            
            // ---------------------------------------------------
            // Spotの言語リストの追加
            // ---------------------------------------------------
            List<Lang> langsList = spot.getLangs();
            if(langsList.indexOf(transLang) < 0) {
                langsList.add(transLang);
            }
            
            // ---------------------------------------------------
            // 保存処理
            // ---------------------------------------------------
            Transaction tx = Datastore.beginTransaction();
            try {

                // スポットの更新
                Datastore.put(tx, spot);

                // 翻訳したコンテンツを追加
                for(SpotTextRes textRes: transContentsList) {
                    
                    // 翻訳対象の場合
                    if(textRes.getRole().isTransTarget()) {
                        // 改行が含まれるため、text()ではなくhtml()で取得する
                        String tcText = document.getElementById(textRes.getKey().getName()).html();

                        // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
                        String strTmp = Utils.clearTextIndention(tcText);
                        String content = Utils.changeBrToTextIndention(strTmp);

                        SpotTextResService.add(tx, spot, transLang, textRes.getRole(), content);
                    }
                }
                
                // 住所のテキストリソースはGMOのフォーマットアドレスを使用
                SpotTextResService.add(tx, spot, transLang, TextResRole.SPOT_ADDRESS, geoModel.getFormattedAddress());
                
                // 言語情報の追加
                SpotLangUnitService.add(tx, spot, transLang, geoModel);
                
                // Gcsリソースの複製
                SpotGcsResService.replicationOtherLangRes(tx, spot, transLang);
            
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
                
            }finally {
                if(tx.isActive()) {
                    tx.rollback();
                }
            }
            
            
        } catch (Exception e) {
            // 翻訳失敗の例外を生成
            e.printStackTrace();
            throw new TransException(e);
        }
    }
    
    /**
     * 再翻訳
     * @param spot
     * @throws TransException
     */
    public static void machineRealReTrans(
            Spot spot) 
            throws TransException {
        
        try {
            // 翻訳するコンテンツリスト
            List<SpotTextRes> transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());
            
            int transCharCount = 0;
            for(SpotTextRes transcontents: transContentsList) {
                if(transcontents.getRole().isTransTarget()) {
                    transCharCount = transCharCount + transcontents.getContentString().length();
                }
            }
            
            // ---------------------------------------------------
            // 翻訳処理
            // ---------------------------------------------------
            String translatedContents = TransService.machineTrans(
                spot.getBaseLang(),
                spot.getLangUnit().getLang(),
                transContentsList);
            
            // 翻訳結果の取得
            Document document = Jsoup.parse(translatedContents);
            
            // 再翻訳対象のリソースMAPを取得
            Map<String, SpotTextRes> resMap = SpotTextResService.getResourcesMap(spot, spot.getLangUnit().getLang());
            
            // ---------------------------------------------------
            // 保存処理
            // ---------------------------------------------------
            Transaction tx = Datastore.beginTransaction();
            try {
                
                // テキストリソースの差し替え
                for(SpotTextRes textRes: transContentsList) {
                    
                    // 翻訳対象の場合
                    if(textRes.getRole().isTransTarget()) {
                        // 改行が含まれるため、text()ではなくhtml()で取得する
                        String tcText = document.getElementById(textRes.getKey().getName()).html();

                        // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
                        String strTmp = Utils.clearTextIndention(tcText);
                        String content = Utils.changeBrToTextIndention(strTmp);
                        
                        // 対象のリソースをリソースマップから取得し、内容を差し替える
                        SpotTextRes spotTextRes = resMap.get(textRes);
                        spotTextRes.setStringToContent(content);
                        
                        // リソースを更新
                        Datastore.put(tx, spotTextRes);
                    }
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
                    spot.getLangUnit().getLang(),
                    TransType.MACHINE, 
                    TransStatus.TRANSLATED, 
                    transCharCount);
                
                // コミット
                tx.commit();
                
            }finally {
                if(tx.isActive()) {
                    tx.rollback();
                }
            }
        
        } catch (Exception e) {
            // 翻訳失敗の例外を生成
            e.printStackTrace();
            throw new TransException(e);
        }
    }
    
    /**
     * 言語の削除
     */
    public static void deleteLang(Spot spot, Lang lang) {

        List<Lang> langsList = spot.getLangs();
        langsList.remove(lang);
        
        List<SpotTextRes> resList = SpotTextResService.getResourcesList(spot, lang);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // スポットの更新
            Datastore.put(tx, spot);
            
            // 言語ユニットの削除
            SpotLangUnitService.delete(tx, spot, lang);
            
            // テキストリソースの削除
            for(SpotTextRes res: resList) {
                Datastore.delete(tx, res.getKey());
            }
            
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
    public static void deleteSpot(Client client, Spot spot) {
        dao.delete(spot.getKey());
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
