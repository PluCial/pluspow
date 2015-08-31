package com.pluspow.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.meta.TextResourcesMeta;
import com.pluspow.model.Spot;


public class TextResourcesService {
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(TextResourcesMeta.get(), keyString);
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
