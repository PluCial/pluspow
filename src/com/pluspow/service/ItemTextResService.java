package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.ItemTextResDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Item;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;


public class ItemTextResService  extends TextResService{
    
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
    protected static ItemTextRes add(
            Transaction tx, 
            Spot spot,
            Item item,
            Lang lang, 
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
     * @throws ObjectNotExistException 
     */
    public static ItemTextRes update(String resourcesKey, String content) throws ObjectNotExistException {
        ItemTextRes model = getResources(resourcesKey);

        model.setStringToContent(content);
        dao.put(model);
        
        return model;
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     * @throws ObjectNotExistException 
     */
    protected static ItemTextRes getResources(String resourcesKey) throws ObjectNotExistException {
        ItemTextRes model = dao.getOrNull(createKey(resourcesKey));
        
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    /**
     * リソースリストを取得
     * @param target(User or Item)
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static List<ItemTextRes> getResourcesList(Item item, Lang lang) throws ObjectNotExistException {
        
        List<ItemTextRes> list = dao.getResourcesList(item, lang);
        if(list == null) throw new ObjectNotExistException();
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     * @throws ObjectNotExistException 
     */
    protected static Map<String, ItemTextRes> getResourcesMap(Item item, Lang lang) throws ObjectNotExistException {
        
        Map<String,ItemTextRes> map = new HashMap<String,ItemTextRes>();
        List<ItemTextRes> list = getResourcesList(item, lang);
        
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
}
