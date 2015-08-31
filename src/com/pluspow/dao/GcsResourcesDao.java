package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.meta.GcsResourcesMeta;
import com.pluspow.model.GcsResources;
import com.pluspow.model.Spot;

public class GcsResourcesDao extends DaoBase<GcsResources>{
    
    /** META */
    private static final GcsResourcesMeta meta =  GcsResourcesMeta.get();
    
    /**
     * Userのすべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<GcsResources> getUserAllResourcesList(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    ).asList();
    }

}
