package com.pluspow.dao;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.meta.ItemMeta;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;

public class ItemDao extends DaoBase<Item>{
    
    /** META */
    private static final ItemMeta meta = ItemMeta.get();
    
    /**
     * アイテムリストの取得
     * @param spot
     * @param lang
     * @param num
     * @return
     */
    private S3QueryResultList<Item> getItemList(Spot spot, Lang lang, int num) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.langs.in(lang),
                    meta.invalid.equal(false)
                        )
                        .sort(new Sort(meta.sortOrder))
                        .limit(num)
                        .asQueryResultList();
    }
    
    /**
     * アイテムリストの取得
     * @param spot
     * @param lang
     * @param num
     * @param cursor
     * @return
     */
    public S3QueryResultList<Item> getItemList(Spot spot, Lang lang, int num, String cursor) {
        
        if (StringUtil.isEmpty(cursor)) return getItemList(spot, lang, num);
        
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.langs.in(lang),
                    meta.invalid.equal(false)
                        )
                        .sort(new Sort(meta.sortOrder))
                        .encodedStartCursor(cursor)
                        .limit(num)
                        .asQueryResultList();
    }

}
