package com.pluspow.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.dao.TextResDao;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;


public class TextResService {
    
    /** DAO */
    private static final TextResDao dao = new TextResDao();
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static TextRes getTextRes(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(TextRes.class, keyString);
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
