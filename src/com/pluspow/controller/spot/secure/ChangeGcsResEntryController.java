package com.pluspow.controller.spot.secure;

import org.datanucleus.util.StringUtils;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.GcsResRole;
import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemGcsResService;
import com.pluspow.service.ItemService;
import com.pluspow.service.SpotGcsResService;
import com.pluspow.utils.PathUtils;

public class ChangeGcsResEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            
            return forward("/spot/secure/changeGcsRes.jsp");
        }
        
        // リクエストパラメーターの取得
        
        Lang lang = Lang.valueOf(asString("lang"));
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        FileItem fileItem = requestScope("uploadImage");
        
        // 役割の取得
        GcsResRole role = null;
        try {
            role = GcsResRole.valueOf(asString("role"));
        }catch(Exception e) {
            throw new NoContentsException();
        }
        
        if(role.isSpotRole()) {
            // ---------------------------------------------------
            // スポットGCSリソース
            // ---------------------------------------------------
            try {
                // 更新
                String resourcesKey = asString("resourcesKey");
                SpotGcsResService.updateImageRes(spot, resourcesKey, fileItem, imageX, imageY, imageWidth, imageHeight);
                
            } catch (ObjectNotExistException e) {
                // 追加
                SpotGcsResService.addImageRes(
                    spot, 
                    lang,
                    role, 
                    fileItem, 
                    imageX, imageY, imageWidth, imageHeight);
            }
            
            return redirect(PathUtils.spotPage(spot, lang));
            
            
            
        }else if(role.isItemRole()) {
            // ---------------------------------------------------
            // アイテムGCSリソース
            // ---------------------------------------------------
            String itemId = asString("itemId");
            if(StringUtils.isEmpty(itemId)) {
                throw new NoContentsException();
            }
            
            Item item = null;
            try {
                item = ItemService.getByKey(itemId, lang);
            }catch(ObjectNotExistException e) {
                throw new NoContentsException();
            }
            
            try {
                // 更新
                String resourcesKey = asString("resourcesKey");
                ItemGcsResService.updateImageRes(spot, item, resourcesKey, fileItem, imageX, imageY, imageWidth, imageHeight);
                
            } catch (ObjectNotExistException e) {
                // 追加
                ItemGcsResService.addImageRes(spot, item, lang, fileItem, imageX, imageY, imageWidth, imageHeight);
            }
            
            return redirect(PathUtils.itemPage(spot, item, lang));
        }
        
        
        throw new NoContentsException();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // 画像
        v.add("uploadImage", v.required("アイテム画像を添付してください。"));
        
        // 画像情報
        v.add("imageX", v.required());
        v.add("imageY", v.required());
        v.add("imageWidth", v.required());
        v.add("imageHeight", v.required());
        v.add("lang", v.required());
        v.add("role", v.required());

        return v.validate();
    }
}
