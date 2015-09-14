package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.meta.GcsResMeta;
import com.pluspow.model.GcsRes;
import com.pluspow.model.Spot;

public class GcsResDao extends DaoBase<GcsRes>{
    
    /** META */
    private static final GcsResMeta meta =  GcsResMeta.get();
    
    /**
     * Userのすべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<GcsRes> getUserAllResourcesList(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    ).asList();
    }

}
