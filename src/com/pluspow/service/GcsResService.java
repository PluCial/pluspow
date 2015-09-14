package com.pluspow.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.Transform;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.pluspow.dao.GcsResDao;
import com.pluspow.model.GcsRes;
import com.pluspow.model.Spot;


public class GcsResService {
    
    /** DAO */
    private static final GcsResDao dao = new GcsResDao();
    
    /** ファイル権限 */
    protected static final String GCS_ACL = "public-read";
    
    /**
     * Gcsリソースの取得
     * @param gcsResKeyString
     * @return
     */
    public static GcsRes getGcsRes(String gcsResKeyString) {
        return dao.get(createKey(gcsResKeyString));
    }
    
    /**
     * 画像の一部を切り取る
     * @param image
     * @param leftX
     * @param topY
     * @param rightX
     * @param bottomY
     */
    protected static void imageCrop(Image image, float leftX, float topY, float rightX, float bottomY) {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        Transform imageTransform = ImagesServiceFactory.makeCrop(
            leftX < 0 ? 0 : leftX, 
                topY < 0 ? 0 : topY, 
                    rightX > 1 ? 1.0 : rightX, 
                        bottomY > 1 ? 1.0 : bottomY);
        imagesService.applyTransform(imageTransform, image);
    }
    
    /**
     * ファイルをGCSに保存
     * @param filePath
     * @param fileItem
     * @throws IOException
     */
    protected static GcsFilename saveImageToGCS(String filePath, String mimeType, Image image) throws IOException {
        
        // GCS SERVICE
        GcsService gcsService =
                GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
        
        GcsOutputChannel outputChannel = null;
        try {
            // Gcsファイル名
            GcsFilename gcsFilename = new GcsFilename(
                AppIdentityServiceFactory.getAppIdentityService().getDefaultGcsBucketName(), 
                filePath
             );
            
            // 新規もしくは上書き
            outputChannel = gcsService.createOrReplace(gcsFilename, 
                new GcsFileOptions.Builder()
                .mimeType(mimeType)
                .acl(GCS_ACL)
                .build()
                    );
            
            // 保存
            outputChannel.write(ByteBuffer.wrap(image.getImageData()));
            
            return gcsFilename;
            
        } catch (IOException e) {
            throw e;
            
        }finally {
            if(outputChannel != null) {
                outputChannel.close();
            }
        }
    }
    
    /**
     * ServingUrl の取得
     * @param gcsFilename
     * @return
     */
    protected static String getServingUrl(GcsFilename gcsFilename) {
        ImagesService is = ImagesServiceFactory.getImagesService();
        
        String servingUrl = is.getServingUrl(
            ServingUrlOptions.Builder.withGoogleStorageFileName(
                "/gs/" + gcsFilename.getBucketName() + "/" + gcsFilename.getObjectName()
                ).secureUrl(true));
        
        return servingUrl;
    }
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    public static Key createKey(String keyString) {
        return Datastore.createKey(GcsRes.class, keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    public static Key createKey(Spot spot) {
        // キーを乱数にする
        UUID uniqueKey = UUID.randomUUID();
        return createKey(spot.getKey().getId() + "_" + uniqueKey.toString());
    }

}
