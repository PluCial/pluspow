package com.pluspow.service;

import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.dao.TextResDao;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;


public class TextResService {
    
    /** DAO */
    private static final TextResDao dao = new TextResDao();
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     * @throws ObjectNotExistException 
     */
    public static TextRes getTextRes(String resourcesKey) throws ObjectNotExistException {
        TextRes model =  dao.getOrNull(createKey(resourcesKey));
        if(model == null) throw new ObjectNotExistException();
        return model;
    }
    
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    protected static Key createKey(String keyString) {
        return Datastore.createKey(TextRes.class, keyString);
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
