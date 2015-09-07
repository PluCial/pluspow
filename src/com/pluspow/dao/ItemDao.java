package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.pluspow.enums.SupportLang;
import com.pluspow.meta.ItemMeta;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;

public class ItemDao extends DaoBase<Item>{
    
    /** META */
    private static final ItemMeta meta = ItemMeta.get();
    
    public List<Item> getItemList(Spot spot, SupportLang lang) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.supportLangs.in(lang)
                        )
                 .sort(new Sort(meta.sortOrder))
                 .asList();
    }

}