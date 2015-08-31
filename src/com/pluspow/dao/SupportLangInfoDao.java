package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pluspow.enums.SupportLang;
import com.pluspow.meta.SupportLangInfoMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SupportLangInfo;

public class SupportLangInfoDao extends DaoBase<SupportLangInfo>{
    
    /** META */
    private static final SupportLangInfoMeta meta = SupportLangInfoMeta.get();
    
    /**
     * スポットIDからスポットを取得(有効なもののみ)
     * @return
     */
    public SupportLangInfo getLangInfo(Spot spot, SupportLang lang) {
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
    public List<SupportLangInfo> getList(Spot spot) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    )
                .sort(new Sort(meta.createDate, SortDirection.ASCENDING)).asList();
    }

}
