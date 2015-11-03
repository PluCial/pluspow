package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.datastore.Sort;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
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
    
    /**
     * 指定したアクティビティのアイテムが存在するかをチェック
     * @param spot
     * @param lang
     * @param num
     * @return
     */
    public List<Item> checkActivityHasOtherItem(Spot spot, Lang lang, SpotActivity activity) {
        return  Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.langs.in(lang),
                    meta.activity.equal(activity),
                    meta.invalid.equal(false)
                        )
                        .sort(new Sort(meta.sortOrder))
                        .limit(1)
                        .asList();
    }
    
    /**
     * 対象の言語の有効なアイテムの最も高い値段を取得
     * @param spot
     * @param lang
     * @return
     */
    public Double getItemMaxPrice(Spot spot, Lang lang) {

        List<Item> list = Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.langs.in(lang),
                    meta.invalid.equal(false)
                        )
                        .sort(meta.price.desc)
                        .limit(1)
                        .asList();

        return list == null || list.size() <= 0 ? 0 : list.get(0).getPrice();
    }

}
