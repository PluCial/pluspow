package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.SpotTextResDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TextResRole;
import com.pluspow.exception.ObjectNotExistException;
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
    protected static SpotTextRes add(
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
     * @throws ObjectNotExistException 
     */
    public static SpotTextRes update(String resourcesKey, String content) throws ObjectNotExistException {
        SpotTextRes model = getResources(resourcesKey);

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
    protected static SpotTextRes getResources(String resourcesKey) throws ObjectNotExistException {
        SpotTextRes model = dao.get(createKey(resourcesKey));
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
    public static List<SpotTextRes> getResourcesList(Spot spot, Lang lang) throws ObjectNotExistException {
        
        List<SpotTextRes> list = dao.getResourcesList(spot, lang);
        if(list == null) throw new ObjectNotExistException();
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     * @throws ObjectNotExistException 
     */
    public static Map<String, SpotTextRes> getResourcesMap(Spot spot, Lang lang) throws ObjectNotExistException {
        
        List<SpotTextRes> list = getResourcesList(spot, lang);
        
        return getResourcesMap(list);
    }
    
    /**
     * リストからリソースマップを取得
     * @param spotTextResList
     * @return
     */
    protected static Map<String, SpotTextRes> getResourcesMap(List<SpotTextRes> spotTextResList) {
        
        Map<String,SpotTextRes> map = new HashMap<String,SpotTextRes>();
        for (SpotTextRes i : spotTextResList) map.put(i.getRole().toString(),i);

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
