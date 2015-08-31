package com.pluspow.service;

import java.util.List;
import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.App;
import com.pluspow.dao.TransHistoryDao;
import com.pluspow.enums.SupportLang;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.meta.TransHistoryMeta;
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
        return dao.getHistoryList(spot, App.SPOT_HOWTO_LIST_LIMIT);
    }
    
    /**
     * 設定
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
    public static TransHistory add(
            Transaction tx,
            Spot spot, 
            SupportLang baseLang, 
            SupportLang transLang, 
            TransType transType, 
            TransStatus transStatus, 
            int transCharCount) {
        
        // 翻訳履歴の設定
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
    public static Key createKey(String keyString) {
        return Datastore.createKey(TransHistoryMeta.get(), keyString);
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
