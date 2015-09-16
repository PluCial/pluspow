package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.pluspow.enums.Lang;
import com.pluspow.meta.ItemLangUnitMeta;
import com.pluspow.model.Item;
import com.pluspow.model.ItemLangUnit;

public class ItemLangUnitDao extends DaoBase<ItemLangUnit>{
    
    /** META */
    private static final ItemLangUnitMeta meta = ItemLangUnitMeta.get();
    
    /**
     * 言語ユニットを取得(無効なものも含まれる)
     * @return
     */
    public ItemLangUnit getLangInfo(Item item, Lang lang) {
        return  Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey()),
                    meta.lang.equal(lang)
                    ).asSingle();
    }
    
    /**
     * 言語ユニットリストを取得(有効なもののみ)
     * @return
     */
    public List<ItemLangUnit> getList(Item item) {
        return  Datastore.query(meta)
                .filter(
                    meta.itemRef.equal(item.getKey()),
                    meta.invalid.equal(false)
                    )
                .sort(new Sort(meta.createDate, SortDirection.ASCENDING)).asList();
    }

}
