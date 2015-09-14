package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.pluspow.enums.Lang;
import com.pluspow.meta.ItemTextResMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemTextRes;

public class ItemTextResDao extends DaoBase<ItemTextRes>{
    
    /** META */
    private static final ItemTextResMeta meta =  ItemTextResMeta.get();
    
    /**
     * アイテムのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<Key> getResourcesKeyList(Item item) {
        return Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey())
                    ).asKeyList();
    }
    
    /**
     * アイテムのリソースリストを取得
     * @param spotId
     * @return
     */
    public List<ItemTextRes> getResourcesList(Item item, Lang lang) {
        return Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey()),
                    meta.lang.equal(lang)
                    ).asList();
    }

}
