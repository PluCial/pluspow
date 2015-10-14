package com.pluspow.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.App;
import com.pluspow.dao.SpotDao;
import com.pluspow.enums.Country;
import com.pluspow.enums.DayOfWeek;
import com.pluspow.enums.Lang;
import com.pluspow.enums.PlanLimitType;
import com.pluspow.enums.ServicePlan;
import com.pluspow.enums.SpotActivity;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.DataMismatchException;
import com.pluspow.exception.GeocodeStatusException;
import com.pluspow.exception.GeocoderLocationTypeException;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.PlanLimitException;
import com.pluspow.exception.SearchApiException;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.meta.SpotMeta;
import com.pluspow.model.Client;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
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
            Country country,
            String address, 
            int floor,
            GeoModel geoModel) {
        
        Spot model = new Spot();
        model.setSpotId(spotId);
        model.setBaseLang(client.getLang());
        model.setEmail(client.getEmail());
        
        // 住所情報の設定
        model.setAddress(address);
        model.setFloor(floor);
        
        // 座標の設定
        model.setLat(geoModel.getLat().doubleValue());
        model.setLng(geoModel.getLng().doubleValue());
        
        // 国の設定
        model.setCountry(country);
        
        // クライアントとの関連
        model.getClientRef().setModel(client);
        
        // 言語ユニットの設定
        SpotLangUnit info = new SpotLangUnit();
        info.getSpotRef().setModel(model);
        info.setBaseLang(client.getLang());
        info.setLang(client.getLang());
        SpotLangUnitService.setSpotGeo(info, geoModel);
        model.setLangUnit(info);
        
        return model;
    }
    
    /**
     * スポットの追加(ステップ２の設定 永久化しない)
     * @param model
     * @param phoneCountry
     * @param phoneNumber
     * @param name
     * @param catchCopy
     * @param content
     */
    public static void setStep2(Spot model, Country phoneCountry,
            String phoneNumber,String name, String catchCopy, String content) {
        
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
        
        // 電話番号
        model.getLangUnit().setPhoneCountry(phoneCountry);
        model.getLangUnit().setPhoneNumber(new PhoneNumber(phoneNumber));
    }
    
    /**
     * Freeプランスタート
     * @param model
     * @param template
     * @param client
     * @return
     * @throws TooManyException
     * @throws ObjectNotExistException 
     */
    public static Spot startFreePlan(
            Spot spot) throws TooManyException, ObjectNotExistException {
        
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
        
        // 検索APIの登録
        try {
            SearchApiService.putDocument(spot, spot.getBaseLang());
        }catch(Exception e) {
            // 影響する機能が多いため、更新できないとしても無視。
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
     * <pre>
     * 付属情報のみをキャッシュし、スポットの基本情報は毎回取得する
     * </pre>
     * @param keyString
     * @return
     * @throws NoContentsException 
     */
    public static Spot getSpot(String spotId, Lang lang) throws ObjectNotExistException {
        
        // ---------------------------------------------------
        // キャッシュからスポット情報を取得
        // ---------------------------------------------------
        Spot model = null;
        try {
            model = MemcacheService.getSpot(spotId, lang);

        }catch(ObjectNotExistException e) {

            // ---------------------------------------------------
            // キャッシュに存在しない場合は付属情報含めをDBから再取得
            // ---------------------------------------------------
            model = getSpotModelOnly(spotId);
            try {
                setSpotInfo(model, lang);

            } catch (ArgumentException e1) {
                // langUnitListを取得する時に引数のSpotにプランオブジェクトが存在しない場合に発生するが、
                // setSpotInfo() 内でプランを設定しているため、発生することがありません。
                // 呼び出し側に対してこのExceptionを隠します。
            }

            // ---------------------------------------------------
            // キャッシュの追加
            // ---------------------------------------------------
            MemcacheService.addSpot(model, lang);
        };

        return model;
    }
    
    /**
     * スポットの付属情報の設定
     * @param spot
     * @param lang
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     */
    private static void setSpotInfo(Spot spot, Lang lang) throws ObjectNotExistException, ArgumentException {
        
        // ---------------------------------------------------
        // プランの設定
        // ---------------------------------------------------
        spot.setPlan(SpotService.getSpotPlan(spot));
        
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
        
        // フリープランに切り替わった場合に一つ目の言語以外はlangUnitList から除外されるため、
        // langUnitが取得できない。
        if(spot.getLangUnit() == null) {
            throw new ObjectNotExistException();
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
        
        // ---------------------------------------------------
        // スポットのアクティビティチェック・更新
        // アイテムを削除した場合の処理分散のため、
        // チェックし、必要に応じて更新する。
        // また、繰り返し処理の途中でCollectionが変更するとConcurrentModificationException
        // が発生するため、Iteratorで繰り返し処理を行う。
        // ---------------------------------------------------
        boolean isChanged = false;
        List<SpotActivity> activitys = spot.getLangUnit().getActivitys();
        if(activitys != null && activitys.size() > 0) {
            Iterator<SpotActivity> iter = activitys.iterator();
            while (iter.hasNext()) {
                SpotActivity activity = iter.next();
                // 存在しない場合
                if(!ItemService.checkActivityHasOtherItem(spot, lang, activity)) {
                    // アクティビティを更新
                    // Iteratorに対してremoveを行うと元のリストも変更される。
                    iter.remove();
                    isChanged = true;
                }
            }
        }
        // 更新があった場合
        if(isChanged) {
            spot.getLangUnit().setActivitys(activitys);
            SpotLangUnitService.update(spot.getLangUnit());
            
            // ドキュメントのアクティビティを更新
            try {
                SearchApiService.putDocument(spot, lang);
            }catch(Exception e) {
                // 影響する機能が多いため、更新できないとしても無視。
            }
        }
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
                spotModel = getSpot(spot.getSpotId(), spot.getBaseLang());
                list.add(spotModel);
            } catch (ObjectNotExistException e) {}
        }
        
        return list;
    }
    
    /**
     * 機械翻訳
     * @param spot
     * @param baseLang
     * @param transLang
     * @throws ArgumentException
     * @throws TransException
     * @throws IOException
     * @throws DataMismatchException
     * @throws PlanLimitException
     */
    public static void machineTrans(
            Spot spot,
            Lang baseLang,
            Lang transLang) throws ArgumentException, TransException, IOException, DataMismatchException, PlanLimitException {
        
        if(baseLang == transLang) throw new ArgumentException();
        
        ServicePlan plan = SpotService.getSpotPlan(spot);
        
        // ---------------------------------------------------
        // 言語数のプラン制限確認
        // プランによって表示されてない言語は再翻訳させないために、
        // 言語の追加と再翻訳両方に言語数の制限チェックを実施する
        // ---------------------------------------------------
        if(spot.getLangs().size() >= plan.getTransLangMaxCount()) {
            if(spot.getLangs().indexOf(transLang) < 0) { // 再翻訳ではないの場合
                throw new PlanLimitException(PlanLimitType.TRANS_LANG_MAX_COUNT);
            }
        }
        
        // ---------------------------------------------------
        // 翻訳するコンテンツリスト
        // ---------------------------------------------------
        List<SpotTextRes> transContentsList;
        try {
            transContentsList = SpotTextResService.getResourcesList(spot, baseLang);
            if(transContentsList.size() <= 0) throw new ObjectNotExistException();

        } catch (ObjectNotExistException e2) {
            // 翻訳するコンテンツがなければそのまま終了
            return;
        }
        
        // ---------------------------------------------------
        // 翻訳文字数のカウント
        // ---------------------------------------------------
        int transCharCount = 0;
        for(SpotTextRes transcontents: transContentsList) {
            transCharCount = transCharCount + transcontents.getContentString().length();
        }
        
        // ---------------------------------------------------
        // 言語文字数のプラン制限確認
        // ---------------------------------------------------
        TransCredit credit = TransCreditService.get(spot);
        if(plan.getTransCharMaxCount() > 0 
                && (credit.getTransCharCount() + transCharCount) >= plan.getTransCharMaxCount()) {
            throw new PlanLimitException(PlanLimitType.TRANS_CHAR_MAX_COUNT);
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
                addLang(tx, spot, baseLang, transLang, transContentsList, transResult);
            }
            
            // 翻訳クレジットの更新
            TransCreditService.update(
                tx, 
                spot, 
                transCharCount, 
                transCharCount * TransType.MACHINE.getPrice());

            // 翻訳履歴の追加
            TransHistoryService.addSpotHistory(
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
        
        // 検索APIの登録
        try {
            SearchApiService.putDocument(spot, transLang);
        }catch(Exception e) {
            // 影響する機能が多いため、更新できないとしても無視。
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
    private static SpotLangUnit addLang(
            Transaction tx,
            Spot spot, 
            Lang baseLang,
            Lang transLang,
            List<SpotTextRes> transContentsList,
            Document transResult) throws IOException, GeocodeStatusException, GeocoderLocationTypeException, ArgumentException, TooManyException {
        
        // ---------------------------------------------------
        // 住所の対象する言語で再取得
        // ---------------------------------------------------
        GeoModel geoModel = null;
        try {
            geoModel = GeoService.getGeoModel(spot.getAddress(), transLang);
        }catch(GeocoderLocationTypeException e) {
            // ステータスは正常で、稀に発生する意味不明なエラー。
            // 英語で一度再取得し、それを利用
            geoModel = GeoService.getGeoModel(spot.getAddress(), Lang.en);
        }

        // ---------------------------------------------------
        // 翻訳したテキストリソースを追加
        // ---------------------------------------------------
        for(SpotTextRes textRes: transContentsList) {
            // 改行が含まれるため、text()ではなくhtml()で取得する
            String tcText = transResult.getElementById(textRes.getKey().getName()).html();

            // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
            String strTmp = Utils.clearTextIndention(tcText);
            String content = Utils.changeBrToTextIndention(strTmp);

            SpotTextResService.add(tx, spot, transLang, textRes.getRole(), content);
        }

        // ---------------------------------------------------
        // 言語情報の追加
        // ---------------------------------------------------
        SpotLangUnit langUnit = SpotLangUnitService.add(
            tx, 
            spot, 
            baseLang, 
            transLang, 
            spot.getPhoneCountry(), 
            spot.getPhoneNumber(),
            TransType.MACHINE, 
            TransStatus.TRANSLATED, geoModel);

        // ---------------------------------------------------
        // Gcsリソースの複製
        // ---------------------------------------------------
        SpotGcsResService.replicationOtherLangRes(tx, spot, transLang);
        
        return langUnit;
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
    
    /**
     * 言語の有効無効切り替え
     * @param spot
     * @param lang
     * @param invalid
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     * @throws PlanLimitException 
     * @throws SearchApiException 
     */
    public static void setInvalid(Spot spot, Lang lang, boolean invalid) throws ObjectNotExistException, ArgumentException, PlanLimitException, SearchApiException {
        
        // ベース言語の有効無効切り替えはできない。
        if(spot.getBaseLang() == lang) throw new ArgumentException();
        
        if(!invalid) {
            // ---------------------------------------------------
            // 言語数のプラン制限確認
            // ---------------------------------------------------
            ServicePlan plan = SpotService.getSpotPlan(spot);
            if(spot.getLangs().size() >= plan.getTransLangMaxCount()) {
                throw new PlanLimitException(PlanLimitType.TRANS_LANG_MAX_COUNT);
            }
        }
        
        SpotLangUnit langUnit = SpotLangUnitService.get(spot, lang);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            langUnit.setInvalid(invalid);
            Datastore.put(tx, langUnit);
            
            // 検索APIの更新
            try {
                if(invalid) {
                    SearchApiService.deleteSpot(spot, lang);

                }else {
                    SearchApiService.putDocument(spot, lang);
                }
            }catch(Exception e) {
                throw new SearchApiException();
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
     * 電話番号の設定
     * @param spot
     * @param lang
     * @param isDisplayFlg
     * @param phoneNumber
     * @throws ObjectNotExistException
     * @throws ArgumentException
     */
    public static void setPhoneNumber(
            Spot spot, 
            Lang lang, 
            Country phoneCountry,
            boolean isDisplayFlg, 
            String phoneNumber) throws ObjectNotExistException, ArgumentException {
        
        SpotLangUnit langUnit = SpotLangUnitService.get(spot, lang);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            if(isDisplayFlg) {
                if(phoneCountry == null || StringUtil.isEmpty(phoneNumber)) throw new ArgumentException();
                langUnit.setPhoneCountry(phoneCountry);
                langUnit.setPhoneNumber(new PhoneNumber(phoneNumber));
            }
            
            langUnit.setPhoneDisplayFlg(isDisplayFlg);
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
     * 利用プランの取得
     * @param spot
     * @return
     */
    public static ServicePlan getSpotPlan(Spot spot) {
        // プラン
        try {
            SpotPayPlan payPlan = SpotPayPlanService.getPlan(spot);
            return payPlan.getPlan();
            
        } catch (ObjectNotExistException e) {
            if(App.SAMPLE_SPOT_ID.equals(spot.getSpotId())) {
                return ServicePlan.STANDARD;
            }
            
            return ServicePlan.FREE;
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
            
            // 検索APIからの削除
            // (プランによってはspot.getLangs() からすべて取得できない可能性があるため、
            // SpotLangUnitServiceから言語リストを取得。未登録のものを削除)
            List<SpotLangUnit> langUnitList = SpotLangUnitService.getAllList(spot);
            for(SpotLangUnit langUnit: langUnitList) {
                try {
                    SearchApiService.deleteSpot(spot, langUnit.getLang());
            
                }catch(IllegalArgumentException e) {
                    // ドキュメントが存在しない場合のエラーをキャッチし、無視する
                }
            }
            
            // コミット
            tx.commit();
            
        } catch (ObjectNotExistException e) {
            return;
            
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
