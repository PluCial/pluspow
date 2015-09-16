package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.GcsResRole;
import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotGcsResService;

public class ChangeGcsResEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            
            return forward("/spot/secure/changeGcsRes.jsp");
        }
        
        // リクエストパラメーターの取得
        String resourcesKey = asString("resourcesKey");
        Lang lang = Lang.valueOf(asString("lang"));
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        FileItem fileItem = requestScope("uploadImage");
        
        // 役割の取得
        GcsResRole role = GcsResRole.valueOf(asString("role"));
        
        if(role.isSpotRole()) {
            try {
                SpotGcsResService.getResources(spot, role);
                // 更新
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
            
            return redirect("/+" + spot.getSpotId() + "/l-" + lang.toString() + "/");
            
        }else if(role.isItemRole()) {
            String itemId = asString("itemId");
            
            return redirect("/+" + spot.getSpotId() + "/l-" + lang.toString() + "/item/" + itemId);
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

        return v.validate();
    }
}
