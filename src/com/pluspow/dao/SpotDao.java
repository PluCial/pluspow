package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

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
     * クライアントが作成したスポットリストを取得
     * @return
     */
    public List<Spot> getSpotListByClient(Client client) {
        return  Datastore.query(meta)
                .filter(
                    meta.clientRef.equal(client.getKey()),
                    meta.invalid.equal(false)
                    ).sort(new Sort(meta.createDate)).asList();
    }

}
