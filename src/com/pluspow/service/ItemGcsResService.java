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
import com.pluspow.exception.ObjectNotExistException;
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
    protected static ItemGcsRes addImageRes(
            Transaction tx, 
            Spot spot, 
            Item item,
            Lang lang,
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
        model.setLang(lang);

        // 保存
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * 画像の追加
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
    public static ItemGcsRes addImageRes(
            Spot spot, 
            Item item,
            Lang lang,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException {
        
        Transaction tx = Datastore.beginTransaction();
        
        try {
            ItemGcsRes model = addImageRes(tx, spot, item, lang, fileItem, leftX, topY, rightX, bottomY);
            
            tx.commit();
            
            return model;

        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }

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
     * @throws ObjectNotExistException 
     */
    public static ItemGcsRes updateImageRes(
            Spot spot,
            Item item, 
            String resourcesKey,
            FileItem fileItem,
            int leftX, 
            int topY, 
            int rightX,
            int bottomY) throws IOException, ObjectNotExistException {
        
        if(StringUtil.isEmpty(resourcesKey)) throw new ObjectNotExistException("更新するコンテンツはありません");
        
        ItemGcsRes oldModel = getResources(resourcesKey);
        
        ItemGcsRes newModel = null;
        Transaction tx = Datastore.beginTransaction();
        try {
            // 古いモデルを無効にする
            oldModel.setInvalid(true);
            Datastore.put(tx, oldModel);
            
            // 新しいモデルを作成
            newModel = addImageRes(tx, spot, item, oldModel.getLang(), fileItem, leftX, topY, rightX, bottomY);

            tx.commit();
            
        }finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
        
        return newModel;
    }
    
    /**
     * 言語セットの複製
     * @param tx
     * @param spot
     * @param item
     * @param lang
     */
    protected static void replicationOtherLangRes(Transaction tx, Spot spot, Item item, Lang lang) {
        List<ItemGcsRes> resList;
        try {
            resList = getResourcesList(item, item.getBaseLang());
            
        } catch (ObjectNotExistException e) {
            // 複製するものがない場合は何も処理せずに終了
            return;
        }
        
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
     * @throws ObjectNotExistException 
     */
    public static ItemGcsRes getResources(String resourcesKey) throws ObjectNotExistException {
        ItemGcsRes model =  dao.getOrNull(createKey(resourcesKey));
        if(model == null) throw new ObjectNotExistException();
        return model;
    }
    
    /**
     * リソースリストを取得
     * @param target
     * @return
     * @throws ObjectNotExistException 
     */
    protected static List<ItemGcsRes> getResourcesList(Item item, Lang lang) throws ObjectNotExistException {
        List<ItemGcsRes> list = dao.getResourcesList(item, lang);
        if(list == null) throw new ObjectNotExistException();
        return list;
    }

}
