package com.pluspow.service;

import java.io.IOException;
import java.util.List;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.pluspow.dao.ItemGcsResDao;
import com.pluspow.enums.GcsResRole;
import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Item;
import com.pluspow.model.ItemGcsRes;
import com.pluspow.model.Spot;


public class ItemGcsResService extends GcsResService {
    
    /** DAO */
    private static final ItemGcsResDao dao = new ItemGcsResDao();
    
    /** ディレクトリ名 */
    private static final String ITEM_DIRECTORY_NAME = "SPOT/ITEM/";
    
    /**
     * 画像の追加
     * @param tx
     * @param spot
     * @param item
     * @param fileItem
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     * @return
     * @throws IOException
     */
    public static ItemGcsRes addImageResources(
            Transaction tx, 
            Spot spot, 
            Item item,
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
        GcsFilename gcsFilename = saveImageToGCS(ITEM_DIRECTORY_NAME + key.getName(), fileItem.getContentType(), image);
        
        // servingUrl
        String servingUrl = getServingUrl(gcsFilename);
        
        // 保存する情報の設定
        ItemGcsRes model = new ItemGcsRes();
        model.setKey(key);
        model.setServingUrl(servingUrl);
        model.setWidth(image.getWidth());
        model.setHeight(image.getHeight());
        model.setContentType(fileItem.getContentType());
        
        model.getSpotRef().setModel(spot);
        model.getItemRef().setModel(item);
        model.setRole(GcsResRole.ITEM_IMAGE);
        model.setLang(spot.getLangUnit().getLang());

        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * 画像の更新
     * @param tx
     * @param spot
     * @param item
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
    public static ItemGcsRes updateImageResources(
            Transaction tx, 
            Spot spot,
            Item item, 
            String resourcesKey,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException, NoContentsException {
        
        if(StringUtil.isEmpty(resourcesKey)) throw new NoContentsException("更新するコンテンツはありません");
        
        ItemGcsRes oldModel = getResources(resourcesKey);
        if(oldModel == null) throw new NoContentsException("更新するコンテンツはありません");
        
        // 古いモデルを無効にする
        oldModel.setInvalid(true);
        dao.put(oldModel);
        
        return addImageResources(tx, spot, item, fileItem, leftX, topY, rightX, bottomY);
    }
    
    /**
     * 言語セットの複製
     * @param tx
     * @param spot
     * @param item
     * @param lang
     */
    public static void replicationOtherLangRes(Transaction tx, Spot spot, Item item, Lang lang) {
        List<ItemGcsRes> resList = getResourcesList(item);
        
        if(resList == null) return;
        
        for(ItemGcsRes res: resList) {
            res.setKey(createKey(spot));
            res.setLang(lang);
            
            Datastore.put(tx, res);
        }
    }
    
    /**
     * リソースの取得
     * @param resourcesKey
     * @return
     */
    public static ItemGcsRes getResources(String resourcesKey) {
        return dao.get(createKey(resourcesKey));
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     */
    public static List<ItemGcsRes> getResourcesList(Item item) {
        List<ItemGcsRes> list = dao.getResourcesList(item);
        
        return list;
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
