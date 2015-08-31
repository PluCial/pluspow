package com.pluspow.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.pluspow.dao.SpotGcsResDao;
import com.pluspow.enums.SpotGcsResRole;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotGcsRes;


public class SpotGcsResService extends GcsResourcesService {
    
    /** DAO */
    private static final SpotGcsResDao dao = new SpotGcsResDao();
    
    /** ディレクトリ名 */
    private static final String SPOT_DIRECTORY_NAME = "SPOT/";
    
    /**
     * アイコン画像を追加
     * @param spot
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
            SpotGcsResRole role,
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
        
        // 保存
        Datastore.put(model);
        
        return model;
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
     * @throws NoContentsException
     */
    public static SpotGcsRes updateImageRes(
            Spot spot, 
            String resourcesKey,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException, NoContentsException {
        
        if(StringUtil.isEmpty(resourcesKey)) throw new NoContentsException("更新するコンテンツはありません");
        
        SpotGcsRes oldModel = getResources(resourcesKey);
        if(oldModel == null) throw new NoContentsException("更新するコンテンツはありません");
        
        // 古いモデルを無効にする
        oldModel.setInvalid(true);
        dao.put(oldModel);
        
        return addImageRes(spot, oldModel.getRole(), fileItem, leftX, topY, rightX, bottomY);
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static SpotGcsRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースの取得
     * @param spot
     * @param role
     * @return
     */
    public static SpotGcsRes getResources(Spot spot, SpotGcsResRole role) {
        return dao.getResources(spot, role);
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     */
    private static List<SpotGcsRes> getResourcesList(Spot spot) {
        // TODO: キャッシュ対応
        List<SpotGcsRes> list = dao.getResourcesList(spot);
        
        return list;
    }
    
    /**
     * リソースマップを取得
     * @param resourcesList
     * @return
     */
    public static Map<String, SpotGcsRes> getResourcesMap(Spot spot) {
        
        Map<String,SpotGcsRes> map = new HashMap<String,SpotGcsRes>();
        
        List<SpotGcsRes> list = getResourcesList(spot);
        if(list == null) return map;
        
        for (SpotGcsRes i : list) map.put(i.getRole().toString(),i);
        
        return map;
    }
    
    /**
     * リソースの取得
     * @param resourcesMap
     * @param role
     * @return
     */
    public static SpotGcsRes getResourcesByMap(Map<String, SpotGcsRes> resourcesMap, SpotGcsResRole role) {
        if(resourcesMap == null) return null; 
        return resourcesMap.get(role.toString());
    }

}
