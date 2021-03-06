package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.enums.Lang;
import com.pluspow.meta.ItemGcsResMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemGcsRes;

public class ItemGcsResDao extends DaoBase<ItemGcsRes>{
    
    /** META */
    private static final ItemGcsResMeta meta =  ItemGcsResMeta.get();
    
    /**
     * すべてのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<ItemGcsRes> getResourcesList(Item item, Lang lang) {
        return Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey()),
                    meta.lang.equal(lang),
                    meta.invalid.equal(false)
                    ).asList();
    }

}
