package com.pluspow.service;

import org.slim3.memcache.Memcache;

import com.pluspow.enums.Lang;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;



public class MemcacheService {
    
    /** ユーザーキー */
    private static final String SPOT_INFO_MEMCACHE_KEY = "spot_info";
    
    /** アイテムキー */
    private static final String ITEM_INFO_MEMCACHE_KEY = "item_info";
    
    /**
     * キャッシュにスポット情報を追加
     * @param model
     */
    public static void addSpot(Spot model, Lang lang) {
        Memcache.put(SPOT_INFO_MEMCACHE_KEY + "_" + model.getSpotId() + "_" + lang.toString(), model);
    }
    
    /**
     * キャッシュからスポット情報を取得
     * @param spotId
     * @return
     * @throws ObjectNotExistException
     */
    public static Spot getSpot(String spotId, Lang lang) throws ObjectNotExistException {
        
        Spot model = Memcache.get(SPOT_INFO_MEMCACHE_KEY + "_" + spotId + "_" + lang.toString());
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    
    /**
     * スポットキャッシュ削除
     * @param userId
     */
    public static void deleteSpot(Spot model, Lang lang) {
        Memcache.delete(SPOT_INFO_MEMCACHE_KEY + "_" + model.getSpotId() + "_" + lang.toString());
    }
    
    /**
     * スポットキャッシュ削除
     * @param userId
     */
    public static void deleteSpotAll(Spot model) {
        for(Lang lang: model.getLangs()) {
            deleteSpot(model, lang);
        }
    }
    
    /**
     * キャッシュにアイテム情報を追加
     * @param model
     */
    public static void addItem(Item model, Lang lang) {
        Memcache.put(ITEM_INFO_MEMCACHE_KEY + "_" + model.getKey().getName() + "_" + lang.toString(), model);
    }
    
    /**
     * キャッシュからアイテム情報を取得
     * @param itemKey
     * @return
     * @throws ObjectNotExistException
     */
    public static Item getItem(String itemKey, Lang lang) throws ObjectNotExistException {
        Item item =  Memcache.get(ITEM_INFO_MEMCACHE_KEY + "_" + itemKey + "_" + lang.toString());
        if(item == null) throw new ObjectNotExistException();
        
        return item;
    }
    
    
    /**
     * アイテムキャッシュ削除
     * @param userId
     */
    public static void deleteItem(Item model, Lang lang) {
        Memcache.delete(ITEM_INFO_MEMCACHE_KEY + "_" + model.getKey().getName() + "_" + lang.toString());
    }
    
    /**
     * アイテムキャッシュ削除
     * @param userId
     */
    public static void deleteItem(String itemKey, Lang lang) {
        Memcache.delete(ITEM_INFO_MEMCACHE_KEY + "_" + itemKey + "_" + lang.toString());
    }
    
    /**
     * スポットキャッシュ削除
     * @param userId
     */
    public static void deleteItemAll(Item model) {
        for(Lang lang: model.getLangs()) {
            deleteItem(model, lang);
        }
    }

}
