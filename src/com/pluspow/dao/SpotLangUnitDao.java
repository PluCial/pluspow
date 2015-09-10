package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pluspow.enums.SupportLang;
import com.pluspow.meta.SpotLangUnitMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;

public class SpotLangUnitDao extends DaoBase<SpotLangUnit>{
    
    /** META */
    private static final SpotLangUnitMeta meta = SpotLangUnitMeta.get();
    
    /**
     * スポットIDからスポットを取得(有効なもののみ)
     * @return
     */
    public SpotLangUnit getLangInfo(Spot spot, SupportLang lang) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.lang.equal(lang)
                    ).asSingle();
    }
    
    /**
     * クライアントが作成したスポットリストを取得
     * @return
     */
    public List<SpotLangUnit> getList(Spot spot) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    )
                .sort(new Sort(meta.createDate, SortDirection.ASCENDING)).asList();
    }

}