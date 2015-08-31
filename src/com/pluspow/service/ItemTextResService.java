package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.ItemTextResDao;
import com.pluspow.enums.SupportLang;
import com.pluspow.enums.TextResRole;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Item;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;


public class ItemTextResService  extends TextResourcesService{
    
    /** DAO */
    private static final ItemTextResDao dao = new ItemTextResDao();
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param user
     * @param item
     * @param lang
     * @param role
     * @param content
     */
    public static ItemTextRes add(
            Transaction tx, 
            Spot spot,
            Item item,
            SupportLang lang, 
            TextResRole role, 
            String content) {
        
        ItemTextRes model = new ItemTextRes();
        model.setKey(createKey(spot));
        model.setLang(lang);
        model.setRole(role);
        model.setStringToContent(content);
        
        model.getSpotRef().setModel(spot);
        model.getItemRef().setModel(item);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * リソースの更新
     * @param resourcesKey
     * @param content
     * @return
     * @throws NoContentsException
     */
    public static ItemTextRes update(String resourcesKey, String content) throws NoContentsException {
        ItemTextRes model = getResources(resourcesKey);
        if(model == null) throw new NoContentsException("更新するコンテンツはありません");

        model.setStringToContent(content);
        dao.put(model);
        
        return model;
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static ItemTextRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target(User or Item)
     * @param lang
     * @return
     */
    public static List<ItemTextRes> getResourcesList(Item item, SupportLang lang) {
        
        List<ItemTextRes> list = dao.getResourcesList(item, lang);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, ItemTextRes> getResourcesMap(Item item, SupportLang lang) {
        
        Map<String,ItemTextRes> map = new HashMap<String,ItemTextRes>();
        List<ItemTextRes> list = getResourcesList(item, lang);
        
        if(list == null) return map;
        
        for (ItemTextRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static ItemTextRes getResourcesByMap(Map<String, ItemTextRes> resourcesMap, TextResRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }
    
    /**
     * アイテムリソースを全削除(用コミット)
     * @param tx
     * @param item
     */
    public static void deleteItemResourcesAll(Transaction tx, Item item) {
        List<Key> keys = dao.getResourcesKeyList(item);

        Datastore.delete(tx, keys);
    }
}
