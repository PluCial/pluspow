package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.enums.SupportLang;
import com.pluspow.meta.SpotTextResMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotTextRes;

public class SpotTextResDao extends DaoBase<SpotTextRes>{
    
    /** META */
    private static final SpotTextResMeta meta =  SpotTextResMeta.get();
    
    /**
     * スポットのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<SpotTextRes> getResourcesList(Spot spot, SupportLang lang) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(lang)
                    ).asList();
    }

}
