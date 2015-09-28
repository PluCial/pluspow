package com.pluspow.service;

import java.util.List;
import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.App;
import com.pluspow.dao.TransHistoryDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.meta.TransHistoryMeta;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.model.TransHistory;


public class TransHistoryService {
    
    /** DAO */
    private static final TransHistoryDao dao = new TransHistoryDao();
    
    /**
     * ヒストリ一覧を取得
     * @param spot
     * @return
     */
    public static List<TransHistory> getHistoryList(Spot spot) {
        return dao.getHistoryList(spot, App.TRANS_HISTORY_LIST_LIMIT);
    }
    
    /**
     * 新しいヒストリーモデルを生成
     * @param spot
     * @param baseLang
     * @param transLang
     * @param transType
     * @param transStatus
     * @param transCharCount
     * @return
     */
    private static TransHistory getNewHistory(
            Spot spot, 
            Lang baseLang, 
            Lang transLang, 
            TransType transType, 
            TransStatus transStatus, 
            int transCharCount) {
        
        TransHistory model = new TransHistory();
        model.setKey(createKey(spot));
        model.setBaseLang(baseLang);
        model.setTransLang(transLang);
        model.setTransType(transType);
        model.setTransStatus(transStatus);
        model.setCharUnitPrice(transType.getPrice());
        model.getSpotRef().setModel(spot);
        
        model.setTransCharCount(transCharCount);
        model.setTransCost(transCharCount * transType.getPrice());
        
        return model;
    }
    
    /**
     * スポットの翻訳ヒストリーの追加
     * @param spot
     * @param baseLang
     * @param transLang
     * @param transType
     * @param transStatus
     * @param contentsType
     * @param transCharCount
     * @param charUnitPrice
     * @param transCost
     * @return
     */
    protected static TransHistory addSpotHistory(
            Transaction tx,
            Spot spot, 
            Lang baseLang, 
            Lang transLang, 
            TransType transType, 
            TransStatus transStatus, 
            int transCharCount) {
        
        // 翻訳履歴の設定
        TransHistory model = getNewHistory(spot, baseLang, transLang, transType, transStatus, transCharCount);
        model.setObjectType(ObjectType.SPOT);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * アイテム翻訳履歴の追加
     * @param tx
     * @param spot
     * @param baseLang
     * @param transLang
     * @param transType
     * @param transStatus
     * @param transCharCount
     * @return
     */
    protected static TransHistory addItemHistory(
            Transaction tx,
            Spot spot, 
            Item item,
            Lang baseLang, 
            Lang transLang, 
            TransType transType, 
            TransStatus transStatus, 
            int transCharCount) {
        
        // 翻訳履歴の設定
        TransHistory model = getNewHistory(spot, baseLang, transLang, transType, transStatus, transCharCount);
        model.setObjectType(ObjectType.ITEM);
        model.getItemRef().setModel(item);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    protected static Key createKey(String keyString) {
        return Datastore.createKey(TransHistoryMeta.get(), keyString);
    }
    
    /**
     * キーの作成
     * @return
     */
    private static Key createKey(Spot spot) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(spot.getKey().getId() + "_" + uniqueKey.toString());
    }

}
