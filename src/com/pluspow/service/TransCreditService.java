package com.pluspow.service;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.exception.TooManyException;
import com.pluspow.model.Spot;
import com.pluspow.model.TransCredit;


public class TransCreditService {
    
    /** DAO */
//    private static final TransCreditDao dao = new TransCreditDao();
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param spot
     * @return
     * @throws TooManyException
     */
    public static TransCredit add(Transaction tx, Spot spot) throws TooManyException {
        
        TransCredit transAcc = new TransCredit();
        transAcc.getSpotRef().setModel(spot);
        
        // 保存
        Datastore.put(tx, transAcc);
        
        return transAcc;
    }
    
    /**
     * 設定
     * @param spot
     * @param transCharCount
     * @param transCost
     * @return
     */
    public static void update(
            Transaction tx,
            Spot spot, 
            int transCharCount, 
            double transCost) {
        
        // 対象スポットの翻訳貯蓄を取得・設定
        TransCredit transAcc = TransCreditService.get(spot);
        
        transAcc.setTransCharCount(transAcc.getTransCharCount() + transCharCount);
        transAcc.setTransCost(transAcc.getTransCost() + transCost);
        
        // 保存
        Datastore.put(tx, transAcc);
    }
    
    /**
     * スポットの翻訳貯蓄を取得
     * @param spot
     * @return
     * @throws EntityNotFoundRuntimeException
     */
    public static TransCredit get(Spot spot) throws EntityNotFoundRuntimeException {
        return spot.getTransCreditRef().getModel();
    }

}
