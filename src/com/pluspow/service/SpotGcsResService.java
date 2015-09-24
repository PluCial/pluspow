package com.pluspow.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.pluspow.dao.SpotGcsResDao;
import com.pluspow.enums.GcsResRole;
import com.pluspow.enums.Lang;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotGcsRes;


public class SpotGcsResService extends GcsResService {
    
    /** DAO */
    private static final SpotGcsResDao dao = new SpotGcsResDao();
    
    /** ディレクトリ名 */
    private static final String SPOT_DIRECTORY_NAME = "SPOT/";
    
    /**
     * アイコン画像を追加
     * @param spot
     * @param lang
     * @param role
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @return
     * @throws IOException
     */
    public static SpotGcsRes addImageRes(
            Transaction tx, 
            Spot spot, 
            Lang lang, 
            GcsResRole role,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException {
        
        Key key = createKey(spot);
        
        // image objectの取得
        Image image = ImagesServiceFactory.makeImage(fileItem.getData());

        // 画像の切り取り
        imageCrop(image, (float)leftX / (float)image.getWidth(), (float)topY / (float)image.getHeight(), (float)rightX / (float)image.getWidth(), (float)bottomY / (float)image.getHeight());
        
        // ファイルの保存
        GcsFilename gcsFilename = saveImageToGCS(SPOT_DIRECTORY_NAME + key.getName(), fileItem.getContentType(), image);
        
        // servingUrl
        String servingUrl = getServingUrl(gcsFilename);
        
        // 保存する情報の設定
        SpotGcsRes model = new SpotGcsRes();
        model.setKey(key);
        model.setRole(role);
        model.setServingUrl(servingUrl);
        model.setWidth(image.getWidth());
        model.setHeight(image.getHeight());
        model.setContentType(fileItem.getContentType());

        model.getSpotRef().setModel(spot);
        model.setLang(lang);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * 画像追加
     * @param spot
     * @param lang
     * @param role
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @return
     * @throws IOException
     */
    public static SpotGcsRes addImageRes(
            Spot spot, 
            Lang lang, 
            GcsResRole role,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException {
        
        Transaction tx = Datastore.beginTransaction();
        try {
            SpotGcsRes model = addImageRes(tx, spot, lang, role, fileItem, leftX, topY, rightX, bottomY);
            
            tx.commit();
            
            return model;

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    /**
     * アイコン画像の更新
     * @param spot
     * @param resourcesKey
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @return
     * @throws IOException
     * @throws ObjectNotExistException 
     */
    public static SpotGcsRes updateImageRes(
            Spot spot, 
            String resourcesKey,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException, ObjectNotExistException {
        
        if(StringUtil.isEmpty(resourcesKey)) throw new ObjectNotExistException("更新するコンテンツはありません");
        
        SpotGcsRes oldModel = getResources(resourcesKey);
        
        // 古いモデルを無効にする
        oldModel.setInvalid(true);
        dao.put(oldModel);
        
        return addImageRes(spot, oldModel.getLang(), oldModel.getRole(), fileItem, leftX, topY, rightX, bottomY);
    }
    
    /**
     * 言語セットの複製
     * @param tx
     * @param spot
     * @param lang
     */
    protected static void replicationOtherLangRes(Transaction tx, Spot spot, Lang lang) {
        List<SpotGcsRes> resList;
        try {
            resList = getResourcesList(spot, lang);
        } catch (ObjectNotExistException e) {
            // 更新するものがなければそのまま終了
            return;
        }
        
        for(SpotGcsRes res: resList) {
            res.setKey(createKey(spot));
            res.setLang(lang);
            
            Datastore.put(tx, res);
        }
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     * @throws ObjectNotExistException 
     */
    protected static SpotGcsRes getResources(String resourcesKey) throws ObjectNotExistException {
        SpotGcsRes model =  dao.getOrNull(createKey(resourcesKey));
        if(model == null) throw new ObjectNotExistException();
        return model;
    }
    
    /**
     * リソースの取得
     * @param spot
     * @param role
     * @return
     * @throws ObjectNotExistException 
     */
    public static SpotGcsRes getResources(Spot spot, GcsResRole role) throws ObjectNotExistException {
        SpotGcsRes model =  dao.getResources(spot, role);
        
        if(model == null) throw new ObjectNotExistException();
        return model;
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     * @throws ObjectNotExistException 
     */
    protected static List<SpotGcsRes> getResourcesList(Spot spot, Lang lang) throws ObjectNotExistException {
        List<SpotGcsRes> list = dao.getResourcesList(spot, lang);
        if(list == null) throw new ObjectNotExistException();
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     * @throws ObjectNotExistException 
     */
    protected static Map<String, SpotGcsRes> getResourcesMap(Spot spot, Lang lang) throws ObjectNotExistException {
        
        Map<String,SpotGcsRes> map = new HashMap<String,SpotGcsRes>();
        
        List<SpotGcsRes> list = getResourcesList(spot, lang);
        
        for (SpotGcsRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static SpotGcsRes getResourcesByMap(Map<String, SpotGcsRes> resourcesMap, GcsResRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }

}
