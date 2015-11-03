package com.pluspow.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.App;
import com.pluspow.dao.ItemDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.PlanLimitType;
import com.pluspow.enums.ServicePlan;
import com.pluspow.enums.SpotActivity;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.DataMismatchException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.PlanLimitException;
import com.pluspow.exception.SearchApiException;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.meta.ItemMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemLangUnit;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TextRes;
import com.pluspow.model.TransCredit;
import com.pluspow.utils.Utils;

public class ItemService {
    
    /** DAO */
    private static final ItemDao dao = new ItemDao();
    
    /**
     * アイテムの取得
     * @param spot
     * @param key
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     */
    public static Item getModelOnly(String key) throws ObjectNotExistException {
        
        Item model = dao.get(createKey(key));
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    /**
     * アイテムの取得
     * @param spot
     * @param key
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     */
    public static Item getItem(String itemKey, Lang lang) throws ObjectNotExistException, ArgumentException {
        
        if(itemKey == null || lang == null) throw new ArgumentException();

        // ---------------------------------------------------
        // キャッシュからスポット情報を取得
        // ---------------------------------------------------
        Item model = null;
        try {
            model = MemcacheService.getItem(itemKey, lang);
            
        }catch(ObjectNotExistException e) {

            // ---------------------------------------------------
            // キャッシュに存在しない場合は付属情報を含めてDBから再取得
            // ---------------------------------------------------
            model = getModelOnly(itemKey);

            // 付属情報の取得
            setItemInfo(model, lang);

            // ---------------------------------------------------
            // キャッシュの追加
            // ---------------------------------------------------
            MemcacheService.addItem(model, lang);
        }

        return model;
    }
    
    /**
     * 追加
     * @param tx
     * @param spot
     * @param price
     * @param dutyFree
     * @return
     * @throws IOException 
     * @throws ObjectNotExistException 
     * @throws TooManyException 
     * @throws SearchApiException 
     */
    public static Item add(
            Spot spot, 
            SpotActivity activity, 
            double price, 
            boolean dutyFree,
            String name, 
            String detail,
            FileItem fileItem, int leftX, int topY, int width, int height) throws IOException, ObjectNotExistException, TooManyException, SearchApiException {
        
        // ---------------------------------------------------
        // アイテムの設定
        // ---------------------------------------------------
        Item model = new Item();
        model.setKey(createKey(spot));
        model.setActivity(activity);
        model.setBaseLang(spot.getBaseLang());
        model.setPrice(price);
        model.getSpotRef().setModel(spot);
        model.setSpotId(spot.getSpotId());
        model.setSortOrder(spot.getItemSortOrderIndex() + 1.0);
        
        // 言語リストに母国語を追加
        model.getLangs().add(spot.getBaseLang());
        
        // ---------------------------------------------------
        // スポットのアイテムカウントを設定
        // ---------------------------------------------------
        spot.getItemCounts().setItem(spot.getItemCounts().getItem() + 1);
        spot.setItemSortOrderIndex(model.getSortOrder());
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            // スポットとアイテムの保存
            Datastore.put(tx, spot, model);
            
            // スポット言語情報の設定(アクティビティの追加)
            SpotLangUnit langInfo = SpotLangUnitService.get(spot, spot.getBaseLang());
            if(langInfo.getActivitys().indexOf(activity) < 0) {
                langInfo.getActivitys().add(activity);
                Datastore.put(tx, langInfo);
            }
            
            // 言語ユニットの追加
            ItemLangUnitService.addBaseLang(tx, spot, model);
            
            // アイテム名の保存
            ItemTextResService.add(tx, spot, model, spot.getBaseLang(), TextResRole.ITEM_NAME, name);
            
            // アイテム説明の保存
            ItemTextResService.add(tx, spot, model, spot.getBaseLang(), TextResRole.ITEM_DETAIL, detail);
            
            // アイテム画像の保存
            ItemGcsResService.addImageRes(
                tx, 
                spot, 
                model, 
                spot.getBaseLang(),
                fileItem, 
                leftX, 
                topY, 
                leftX + width, 
                topY + height);
            
            tx.commit();
        
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        // 検索APIの登録
//        try {
//            SearchApi.putDocument(spot, spot.getBaseLang());
//        }catch(Exception e) {
//            // 影響する機能が多いため、更新できないとしても無視。
//        }
        
        return model;
    }
    
    /**
     * 機械翻訳
     * @param spot
     * @param item
     * @param baseLang
     * @param transLang
     * @throws ArgumentException
     * @throws TransException
     * @throws DataMismatchException
     * @throws PlanLimitException
     * @throws SearchApiException 
     */
    public static void machineTrans(
            Spot spot, 
            Item item,
            Lang baseLang,
            Lang transLang) throws ArgumentException, TransException, DataMismatchException, PlanLimitException, SearchApiException {

        if(item.getBaseLang() == transLang) throw new ArgumentException();
        
        ServicePlan plan = SpotService.getSpotPlan(spot);

        // ---------------------------------------------------
        // 翻訳するコンテンツリスト
        // ---------------------------------------------------
        List<ItemTextRes> transContentsList = null;
        try {
            transContentsList = ItemTextResService.getResourcesList(item, item.getBaseLang());
            if(transContentsList.size() <= 0) throw new ObjectNotExistException();
            
        } catch (ObjectNotExistException e) {
            // 翻訳するコンテンツがなければそのまま終了
            return;
        }

        // ---------------------------------------------------
        // 翻訳文字数のカウント
        // ---------------------------------------------------
        int transCharCount = 0;
        for(TextRes transcontents: transContentsList) {
            transCharCount = transCharCount + transcontents.getContentString().length();
        }
        
        // ---------------------------------------------------
        // 言語文字数のプラン制限確認
        // ---------------------------------------------------
        TransCredit credit = TransCreditService.get(spot);
        if(plan.getTransCharMaxCount() > 0 
                && (credit.getTransCharCount() + transCharCount) > plan.getTransCharMaxCount()) {
            throw new PlanLimitException(PlanLimitType.TRANS_CHAR_MAX_COUNT);
        }

        // ---------------------------------------------------
        // 翻訳処理(Google API)
        // ---------------------------------------------------
        String translatedContents = null;
        try {
            translatedContents = TransService.machineTrans(
                item.getBaseLang(),
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
                ItemLangUnitService.get(item, transLang);

                // ---------------------------------------------------
                // 再翻訳の保存処理
                // ---------------------------------------------------
                langReTrans(tx, spot, item, transLang, transContentsList, transResult);

            } catch (ObjectNotExistException e) {
                // ---------------------------------------------------
                // 新規翻訳の保存処理
                // ---------------------------------------------------
                addLang(tx, spot, item, baseLang, transLang, transContentsList, transResult);
            }
            
            // 翻訳クレジットの更新
            TransCreditService.update(
                tx, 
                spot, 
                transCharCount, 
                transCharCount * TransType.MACHINE.getPrice());

            // 翻訳履歴の追加
            TransHistoryService.addItemHistory(
                tx, 
                spot, 
                item,
                item.getBaseLang(), 
                transLang, 
                TransType.MACHINE, 
                TransStatus.TRANSLATED, 
                transCharCount);

            // コミット
            tx.commit();
            
        } catch (TooManyException e) {
            throw new TransException("データー不整合");

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        // ---------------------------------------------------
        // 検索APIの登録
        // ---------------------------------------------------
//        try {
//            SearchApi.putDocument(spot, transLang);
//        }catch(Exception e) {
//            // 影響する機能が多いため、更新できないとしても無視。
//        }
    }
            
    
    /**
     * 言語の追加
     * @param tx
     * @param spot
     * @param item
     * @param transLang
     * @param transContentsList
     * @param transResult
     * @throws DataMismatchException
     * @throws TooManyException 
     * @throws ArgumentException 
     * @throws SearchApiException 
     */
    private static void addLang(
            Transaction tx,
            Spot spot, 
            Item item,
            Lang baseLang,
            Lang transLang,
            List<ItemTextRes> transContentsList,
            Document transResult) 
                    throws DataMismatchException, ArgumentException, TooManyException, SearchApiException {
        
        // ---------------------------------------------------
        // Itemの言語リストの追加
        // ---------------------------------------------------
        List<Lang> langsList = item.getLangs();
        if(langsList.indexOf(transLang) < 0) {
            langsList.add(transLang);
            Datastore.put(tx, item);
        }

        // ---------------------------------------------------
        // スポットの言語情報内のアクティビティを更新
        // ---------------------------------------------------
        SpotLangUnit langInfo;
        try {
            langInfo = SpotLangUnitService.get(spot, transLang);

            if(langInfo.getActivitys().indexOf(item.getActivity()) < 0) {
                langInfo.getActivitys().add(item.getActivity());
                Datastore.put(tx, langInfo);
            }
        } catch (ObjectNotExistException e) {
            throw new DataMismatchException();
        }

        // ---------------------------------------------------
        // テキストリソースに翻訳したコンテンツを追加
        // ---------------------------------------------------
        for(ItemTextRes tc: transContentsList) {
            // 改行が含まれるため、text()ではなくhtml()で取得する
            String tcText = transResult.getElementById(tc.getKey().getName()).html();

            // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
            String strTmp = Utils.clearTextIndention(tcText);
            String content = Utils.changeBrToTextIndention(strTmp);

            ItemTextResService.add(tx, spot, item, transLang, tc.getRole(), content);
        }
        
        // ---------------------------------------------------
        // 言語情報の追加
        // ---------------------------------------------------
        ItemLangUnitService.add(
            tx, 
            spot, 
            item, 
            baseLang, 
            transLang, 
            TransType.MACHINE, 
            TransStatus.TRANSLATED);

        // ---------------------------------------------------
        // GCSリソースの複製
        // ---------------------------------------------------
        ItemGcsResService.replicationOtherLangRes(tx, spot, item, transLang);
    }
    
    /**
     * 言語の追加
     * @param tx
     * @param spot
     * @param item
     * @param transLang
     * @param transContentsList
     * @param transResult
     * @throws DataMismatchException
     * @throws TooManyException 
     * @throws ArgumentException 
     */
    private static void langReTrans(
            Transaction tx,
            Spot spot, 
            Item item,
            Lang transLang,
            List<ItemTextRes> transContentsList,
            Document transResult) 
                    throws DataMismatchException, ArgumentException, TooManyException {
        
        // ---------------------------------------------------
        // 対象言語のテキストリソースマップを取得
        // ---------------------------------------------------
        Map<String, ItemTextRes> resMap;
        try {
            resMap = ItemTextResService.getResourcesMap(item, transLang);
            
        } catch (ObjectNotExistException e) {
            throw new DataMismatchException();
        }

        // ---------------------------------------------------
        // 再翻訳対象のリソースMAPを取得
        // ---------------------------------------------------
        for(ItemTextRes textRes: transContentsList) {
            // 改行が含まれるため、text()ではなくhtml()で取得する
            String tcText = transResult.getElementById(textRes.getKey().getName()).html();

            // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
            String strTmp = Utils.clearTextIndention(tcText);
            String content = Utils.changeBrToTextIndention(strTmp);

            // 対象のリソースをリソースマップから取得し、内容を差し替える
            ItemTextRes itemTextRes = resMap.get(textRes.getRole().toString());
            itemTextRes.setStringToContent(content);

            // リソースを更新
            Datastore.put(tx, itemTextRes);
        }
    }
    
    /**
     * アイテムの付属情報の設定
     * @param spot
     * @param lang
     * @throws ObjectNotExistException 
     */
    private static void setItemInfo(Item item, Lang lang) throws ObjectNotExistException {
        // ---------------------------------------------------
        // 言語情報の設定
        // ---------------------------------------------------
        ItemLangUnit langUnit = ItemLangUnitService.get(item, lang);
        item.setLangUnit(langUnit);
        
        // ---------------------------------------------------
        // テキストリソースの設定
        // ---------------------------------------------------
        item.setTextResources(ItemTextResService.getResourcesMap(item, lang));
        
        // ---------------------------------------------------
        // GCS
        // ---------------------------------------------------
        item.setGcsResources(ItemGcsResService.getResourcesList(item, lang));
    }
    
    /**
     * ソート順の変更
     * @param item
     * @param newOrder
     * @return
     */
    public static void changeSortOrder(Item item, double newOrder) {
        item.setSortOrder(newOrder);
        dao.put(item);
    }
    
    /**
     * スポットのアイテムリストを取得
     * @param spot
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static S3QueryResultList<Item> getItemList(Spot spot, Lang lang, String cursor) throws ObjectNotExistException {
        
        S3QueryResultList<Item> itemList = dao.getItemList(spot, lang, App.SPOT_ITEM_LIST_LIMIT, cursor);
        if(itemList == null) throw new ObjectNotExistException();
        
        // 詳細の追加
        for(Item item: itemList) {

            try {
                setItemInfo(item, lang);
            } catch (Exception e) {}
        }
        
        return itemList;
    }
    
    /**
     * スポットのアイテムリストを取得
     * @param spot
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static S3QueryResultList<Item> getEditModeItemList(Spot spot, Lang lang, String cursor) throws ObjectNotExistException {
        
        S3QueryResultList<Item> itemList = dao.getItemList(spot, spot.getBaseLang(), App.SPOT_ITEM_LIST_LIMIT, cursor);
        if(itemList == null) throw new ObjectNotExistException();
        
        // 詳細の追加
        for(Item item: itemList) {
            try {
                if(item.getLangs().indexOf(lang) >= 0) {
                    // 対象の言語をサポートしている場合対象の言語を取得
                    setItemInfo(item, lang);
                    
                }else {
                    // 対象の言語をサポートしていない場合、base言語を取得
                    setItemInfo(item, item.getBaseLang());
                    
                }
            } catch (ObjectNotExistException e) {}
        }
        
        return itemList;
    }

    /**
     * 言語の有効無効切り替え
     * @param item
     * @param lang
     * @param invalid
     * @throws ObjectNotExistException
     * @throws ArgumentException
     * @throws SearchApiException 
     */
    public static void setInvalid(Spot spot, Item item, Lang lang, boolean invalid) throws ObjectNotExistException, ArgumentException, SearchApiException {

        // ベース言語の有効無効切り替えはできない。
        if(item.getBaseLang() == lang) throw new ArgumentException();
        
        // 言語ユニットの取得
        ItemLangUnit itemlangUnit = ItemLangUnitService.get(item, lang);
        SpotLangUnit spotlangUnit = SpotLangUnitService.get(spot, lang);
        
        // スポットのアクティビティを取得
        List<SpotActivity> activitys = spotlangUnit.getActivitys();
        
        // Item内のlangs から対象の言語を追加・削除する
        List<Lang> langsList = item.getLangs();
        if(invalid) {
            // 言語の削除
            // スポットの取得時に同様な処理があるため、ここではスポットのアクティビティを更新しない。
            if(langsList.indexOf(lang) >= 0) {
                langsList.remove(lang);
            }
            
            
        }else {
            // 言語の追加
            if(langsList.indexOf(lang) < 0) {
                langsList.add(lang);
            }
            
            // スポットのアクティビティの追加
            if(activitys.indexOf(item.getActivity()) < 0) {
                activitys.add(item.getActivity());
            }
        }

        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            itemlangUnit.setInvalid(invalid);
            
            Datastore.put(tx, item, itemlangUnit, spotlangUnit);

            // コミット
            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        // ---------------------------------------------------
        // 検索APIの更新
        // ---------------------------------------------------
//        try {
//            SearchApi.putDocument(spot, lang);
//        }catch(Exception e) {
//            // 影響する機能が多いため、更新できないとしても無視。
//        }
    }
    
    /**
     * 指定したアクティビティには他のアイテムが存在するかをチェック
     * @param spot
     * @param lang
     * @param activity
     * @return
     */
    public static boolean checkActivityHasOtherItem(Spot spot, Lang lang, SpotActivity activity) {
        List<Item> itemList =  dao.checkActivityHasOtherItem(spot, lang, activity);
        if(itemList !=null && itemList.size() > 0) return true;
        
        return false;
    }
    
    /**
     * 金額の変更
     * @param item
     * @param price
     */
    public static void setPrice(Item item, double price) {
        item.setPrice(price);
        
        // ---------------------------------------------------
        // 保存処理
        // ---------------------------------------------------
        Transaction tx = Datastore.beginTransaction();
        try {
            Datastore.put(tx, item);

            // コミット
            tx.commit();

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * 対象の言語の有効なアイテムの最も高い値段を取得
     * @param spot
     * @param lang
     * @return
     */
    public static Double getItemMaxPrice(Spot spot, Lang lang) {
        return dao.getItemMaxPrice(spot, lang);
    }
    
    /**
     * 削除
     * <pre>
     * 言語ユニットのアクティビティや検索ドキュメントの更新はスポットを表示したタイミングで行う。
     * </pre>
     * @param spot
     * @throws ObjectNotExistException 
     */
    public static void delete(Spot spot, Item item) throws ObjectNotExistException {
        
        Transaction tx = Datastore.beginTransaction();
        try {
            // アイテムを無効にする
            item.setInvalid(true);
            
            // スポットのアイテム数を変更
            if(spot.getItemCounts().getItem() > 0) {
                spot.getItemCounts().setItem(spot.getItemCounts().getItem() - 1);
            }
            
            Datastore.put(tx, spot, item);
            
            // コミット
            tx.commit();
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(ItemMeta.get(), keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    public static Key createKey(Spot spot) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(spot.getKey().getId() + "_" + uniqueKey.toString());
    }

}
