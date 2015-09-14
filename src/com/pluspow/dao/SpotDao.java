package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.enums.Lang;
import com.pluspow.meta.SpotMeta;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;

public class SpotDao extends DaoBase<Spot>{
    
    /** META */
    private static final SpotMeta meta = SpotMeta.get();
    
    /**
     * スポットIDからスポットを取得(有効なもののみ)
     * @return
     */
    public Spot getBySpotId(String spotId) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotId.equal(spotId),
                    meta.invalid.equal(false)
                    ).asSingle();
    }
    
    /**
     * スポットIDからスポットを取得(有効なもののみ)
     * @return
     */
    public Spot getBySpotId(String spotId, Lang lang) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotId.equal(spotId),
                    meta.langs.in(lang),
                    meta.invalid.equal(false)
                    ).asSingle();
    }
    
    /**
     * クライアントが作成したスポットリストを取得
     * @return
     */
    public List<Spot> getSpotListByClient(Client client) {
        return  Datastore.query(meta)
                .filter(
                    meta.clientRef.equal(client.getKey())
                    ).asList();
    }

}
