package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.SpotTextResDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotTextRes;


public class SpotTextResService  extends TextResService{
    
    /** DAO */
    private static final SpotTextResDao dao = new SpotTextResDao();
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param user
     * @param lang
     * @param role
     * @param content
     */
    public static SpotTextRes add(
            Transaction tx, 
            Spot spot,
            Lang lang, 
            TextResRole role, 
            String content) {
        
        SpotTextRes model = new SpotTextRes();
        model.setKey(createKey(spot));
        model.setLang(lang);
        model.setRole(role);
        model.setStringToContent(content);
        
        model.getSpotRef().setModel(spot);
        
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
    public static SpotTextRes update(String resourcesKey, String content) throws NoContentsException {
        SpotTextRes model = getResources(resourcesKey);
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
    public static SpotTextRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target(User or Item)
     * @param lang
     * @return
     */
    public static List<SpotTextRes> getResourcesList(Spot spot, Lang lang) {
        
        List<SpotTextRes> list = dao.getResourcesList(spot, lang);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, SpotTextRes> getResourcesMap(Spot spot, Lang lang) {
        
        Map<String,SpotTextRes> map = new HashMap<String,SpotTextRes>();
        
        List<SpotTextRes> list = getResourcesList(spot, lang);
        if(list == null) return map;
        
        for (SpotTextRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static SpotTextRes getResourcesByMap(Map<String, SpotTextRes> resourcesMap, TextResRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }
}
