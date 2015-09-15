package com.pluspow.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.model.LangUnit;
import com.pluspow.model.Spot;


public class LangUnitService {
    
    /** DAO */
//    private static final LangUnitDao dao = new LangUnitDao();
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    private static Key createKey(String keyString) {
        return Datastore.createKey(LangUnit.class, keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    protected static Key createKey(Spot spot) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(spot.getKey().getId() + "_" + uniqueKey.toString());
    }

}
