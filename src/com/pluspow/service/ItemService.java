package com.pluspow.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.ItemDao;
import com.pluspow.enums.ItemType;
import com.pluspow.enums.SupportLang;
import com.pluspow.enums.TextResRole;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.TooManyException;
import com.pluspow.exception.TransException;
import com.pluspow.exception.UnsuitableException;
import com.pluspow.meta.ItemMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TextResources;
import com.pluspow.utils.Utils;

public class ItemService {
    
    /** DAO */
    private static final ItemDao dao = new ItemDao();
    
    /**
     * 追加
     * @param tx
     * @param spot
     * @param price
     * @param dutyFree
     * @return
     * @throws IOException 
     */
    public static Item add(
            Spot spot, 
            ItemType itemType, 
            double price, 
            boolean dutyFree,
            String name, 
            String detail,
            FileItem fileItem, int leftX, int topY, int width, int height) throws IOException {
        
        // ---------------------------------------------------
        // アイテムの設定
        // ---------------------------------------------------
        Item model = new Item();
        model.setKey(createKey(spot));
        model.setItemType(itemType);
        model.setBaseLang(spot.getBaseLang());
        model.setPrice(price);
        model.getSpotRef().setModel(spot);
        model.setSortOrder(spot.getItemCounts().getItem() + 1);
        
        // 言語リストに母国語を追加
        model.getSupportLangs().add(spot.getBaseLang());
        
        // ---------------------------------------------------
        // スポットのアイテムカウントを設定
        // ---------------------------------------------------
        spot.getItemCounts().setItem(spot.getItemCounts().getItem() + 1);
        
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
     * リアルタイム機械翻訳
     * @param otherLang
     * @return
     * @throws UnsuitableException 
     * @throws TransException 
     * @throws TooManyException 
     */
    public static void machineRealTrans(
            Spot spot, 
            Item item,
            SupportLang transLang) 
            throws UnsuitableException, TransException, TooManyException {
        
        if(item.getSupportLangs().indexOf(transLang) > 0) throw new TooManyException("この言語は既に追加されています。");
        
        try {
            // 翻訳するコンテンツリスト
            List<ItemTextRes> transContentsList = ItemTextResService.getResourcesList(item, spot.getBaseLang());
            
            int transCharCount = 0;
            for(TextResources transcontents: transContentsList) {
                transCharCount = transCharCount + transcontents.getContentString().length();
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
            // Itemの言語リストの追加
            // ---------------------------------------------------
            List<SupportLang> langsList = item.getSupportLangs();
            if(langsList.indexOf(transLang) < 0) {
                langsList.add(transLang);
            }
            
            
            // ---------------------------------------------------
            // 保存処理
            // ---------------------------------------------------
            Transaction tx = Datastore.beginTransaction();
            try {

                // アイテムの更新
                Datastore.put(tx, item);
                
                // スポットの言語情報内のアクティビティを更新
                SpotLangUnit langInfo = SpotLangUnitService.get(spot, transLang);
                if(langInfo.getActivitys().indexOf(item.getItemType().getActivity()) < 0) {
                    langInfo.getActivitys().add(item.getItemType().getActivity());
                    Datastore.put(tx, langInfo);
                }

                // テキストリソースに翻訳したコンテンツを追加
                for(ItemTextRes tc: transContentsList) {
                    // 改行が含まれるため、text()ではなくhtml()で取得する
                    String tcText = document.getElementById(tc.getKey().getName()).html();
                    
                    // getElementById から取得した値に余計な改行が含まれるため、一度手動で除去してからhtml改行をtext改行に置き換える
                    String strTmp = Utils.clearTextIndention(tcText);
                    String content = Utils.changeBrToTextIndention(strTmp);
                    
                    ItemTextResService.add(tx, spot, item, transLang, tc.getRole(), content);
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
     * スポットの付属情報の設定
     * @param spot
     * @param lang
     */
    public static void setItemInfo(Spot spot, Item item, SupportLang lang) {
        
        item.setTextResources(ItemTextResService.getResourcesMap(item, lang));
        
        item.setGcsResources(ItemGcsResService.getResourcesList(item));
    }
    
    /**
     * アイテムの取得
     * @param spot
     * @param key
     * @param lang
     * @return
     * @throws UnsuitableException 
     */
    public static Item getByKey(Spot spot, String key, SupportLang lang) throws UnsuitableException {
        
        if(key == null || lang == null) throw new UnsuitableException();
        
        Item model = dao.get(createKey(key));

        setItemInfo(spot, model, lang);
        
        return model;
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
     */
    public static List<Item> getItemList(Spot spot, SupportLang lang) {
        
        List<Item> itemList = dao.getItemList(spot, lang);
        
        if(itemList == null) return null;
        
        // 詳細の追加
        for(Item item: itemList) {
            setItemInfo(spot, item, lang);
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
