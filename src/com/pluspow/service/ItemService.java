package com.pluspow.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.ItemDao;
import com.pluspow.enums.ItemType;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.DataMismatchException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.meta.ItemMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TextRes;
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
     */
    public static Item getByKey(Spot spot, String key, Lang lang) throws ObjectNotExistException {
        
        if(key == null || lang == null) throw new ObjectNotExistException();
        
        Item model = dao.get(createKey(key));

        setItemInfo(spot, model, lang);
        
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
     */
    public static Item add(
            Spot spot, 
            ItemType itemType, 
            double price, 
            boolean dutyFree,
            String name, 
            String detail,
            FileItem fileItem, int leftX, int topY, int width, int height) throws IOException, ObjectNotExistException, TooManyException {
        
        // ---------------------------------------------------
        // アイテムの設定
        // ---------------------------------------------------
        Item model = new Item();
        model.setKey(createKey(spot));
        model.setItemType(itemType);
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
            if(langInfo.getActivitys().indexOf(itemType.getActivity()) < 0) {
                langInfo.getActivitys().add(itemType.getActivity());
                Datastore.put(tx, langInfo);
            }
            
            // 言語ユニットの追加
            ItemLangUnitService.addBaseLang(tx, spot, model);
            
            // アイテム名の保存
            ItemTextResService.add(tx, spot, model, spot.getBaseLang(), TextResRole.ITEM_NAME, name);
            
            // アイテム説明の保存
            ItemTextResService.add(tx, spot, model, spot.getBaseLang(), TextResRole.ITEM_DETAIL, detail);
            
            // アイテム画像の保存
            ItemGcsResService.addImageResources(
                tx, 
                spot, 
                model, 
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
        
        return model;
    }
    
    /**
     * 機械翻訳
     * @param spot
     * @param item
     * @param transLang
     * @throws ArgumentException
     * @throws TransException
     * @throws DataMismatchException
     */
    public static void machineTrans(Spot spot, 
            Item item,
            Lang transLang) throws ArgumentException, TransException, DataMismatchException {

        if(spot.getBaseLang() == transLang) throw new ArgumentException();

        // ---------------------------------------------------
        // 翻訳するコンテンツリスト
        // ---------------------------------------------------
        List<ItemTextRes> transContentsList = null;
        try {
            transContentsList = ItemTextResService.getResourcesList(item, item.getBaseLang());
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
                addLang(tx, spot, item, transLang, transContentsList, transResult);
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
     * @param item
     * @param transLang
     * @param transContentsList
     * @param transResult
     * @throws DataMismatchException
     * @throws TooManyException 
     * @throws ArgumentException 
     */
    private static void addLang(
            Transaction tx,
            Spot spot, 
            Item item,
            Lang transLang,
            List<ItemTextRes> transContentsList,
            Document transResult) 
                    throws DataMismatchException, ArgumentException, TooManyException {

        // ---------------------------------------------------
        // Itemの言語リストの追加
        // ---------------------------------------------------
        List<Lang> langsList = item.getLangs();
        if(langsList.indexOf(transLang) < 0) {
            langsList.add(transLang);
        }
        Datastore.put(tx, item);

        // ---------------------------------------------------
        // スポットの言語情報内のアクティビティを更新
        // ---------------------------------------------------
        SpotLangUnit langInfo;
        try {
            langInfo = SpotLangUnitService.get(spot, transLang);

            if(langInfo.getActivitys().indexOf(item.getItemType().getActivity()) < 0) {
                langInfo.getActivitys().add(item.getItemType().getActivity());
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
        ItemLangUnitService.add(tx, spot, item, transLang, TransType.MACHINE, TransStatus.TRANSLATED);

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

            // 翻訳対象の場合
            if(textRes.getRole().isTransTarget()) {
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
    }
    
    /**
     * アイテムの付属情報の設定
     * @param spot
     * @param lang
     * @throws ObjectNotExistException 
     */
    public static void setItemInfo(Spot spot, Item item, Lang lang) throws ObjectNotExistException {
        
        item.setTextResources(ItemTextResService.getResourcesMap(item, lang));
        
        item.setGcsResources(ItemGcsResService.getResourcesList(item));
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
        
        // TODO: キャッシュクリア
    }
    
    /**
     * スポットのアイテムリストを取得
     * @param spot
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static List<Item> getItemList(Spot spot, Lang lang) throws ObjectNotExistException {
        
        List<Item> itemList = dao.getItemList(spot, lang);
        if(itemList == null) throw new ObjectNotExistException();
        
        // 詳細の追加
        for(Item item: itemList) {
            try {
                setItemInfo(spot, item, lang);
            } catch (ObjectNotExistException e) {
            }
        }
        
        return itemList;
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
