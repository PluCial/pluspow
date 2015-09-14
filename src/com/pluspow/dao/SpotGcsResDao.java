package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.enums.SpotGcsResRole;
import com.pluspow.meta.SpotGcsResMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotGcsRes;

public class SpotGcsResDao extends DaoBase<SpotGcsRes>{
    
    /** META */
    private static final SpotGcsResMeta meta =  SpotGcsResMeta.get();
    
    /**
     * アイテムのすべてのリソースリストを取得
     * @param spot
     * @param lang
     * @param role
     * @return
     */
    public SpotGcsRes getResources(Spot spot, SpotGcsResRole role) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(spot.getLangUnit().getLang()),
                    meta.role.equal(role),
                    meta.invalid.equal(false)
                    ).asSingle();
    }
    
    /**
     * アイテムのすべてのリソースリストを取得
     * @param spot
     * @param lang
     * @return
     */
    public List<SpotGcsRes> getResourcesList(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(spot.getLangUnit().getLang()),
                    meta.invalid.equal(false)
                    ).asList();
    }
    
    /**
     * リソースキーリストを取得(無効なものも含む)
     * @param spotId
     * @return
     */
    public List<Key> getResourcesKeyList(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(spot.getLangUnit().getLang())
                    ).asKeyList();
    }

}
