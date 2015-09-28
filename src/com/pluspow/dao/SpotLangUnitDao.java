package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pluspow.enums.Lang;
import com.pluspow.meta.SpotLangUnitMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;

public class SpotLangUnitDao extends DaoBase<SpotLangUnit>{
    
    /** META */
    private static final SpotLangUnitMeta meta = SpotLangUnitMeta.get();
    
    /**
     * 言語ユニットを取得(無効なものも含まれる)
     * @return
     */
    public SpotLangUnit getLangInfo(Spot spot, Lang lang) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(lang)
                    ).asSingle();
    }
    
    /**
     * 言語ユニットリストを取得(有効なもののみ)
     * @param spot
     * @param limit
     * @return
     */
    public List<SpotLangUnit> getList(Spot spot, int limit) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.invalid.equal(false)
                    )
                .limit(limit)
                .sort(new Sort(meta.createDate, SortDirection.ASCENDING)).asList();
    }
    
    /**
     * 言語ユニットリストを取得(無効なものも含む)
     * @param spot
     * @param limit
     * @return
     */
    public List<SpotLangUnit> getAllList(Spot spot) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    )
                .sort(
                    new Sort(meta.invalid, SortDirection.ASCENDING),
                    new Sort(meta.createDate, SortDirection.ASCENDING)).asList();
    }

}
