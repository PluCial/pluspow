package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.ResGroups;
import com.pluspow.enums.SpotGcsResRole;
import com.pluspow.exception.NoContentsException;
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
        ResGroups resGroups = ResGroups.valueOf(asString("resGroups"));
        String resourcesKey = asString("resourcesKey");
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        FileItem fileItem = requestScope("uploadImage");
        
        if(resGroups == ResGroups.SPOT) {
            SpotGcsResRole target = SpotGcsResRole.valueOf(asString("target"));
            
            if(SpotGcsResService.getResources(spot, target) != null) {
                // 更新
                SpotGcsResService.updateImageRes(spot, resourcesKey, fileItem, imageX, imageY, imageWidth, imageHeight);
                
            }else {
                // 追加
                SpotGcsResService.addImageRes(
                    spot, 
                    target, 
                    fileItem, 
                    imageX, imageY, imageWidth, imageHeight);
            }
            
            return redirect("/+" + spot.getSpotId() + "/l-" + spot.getBaseLang().toString() + "/");
            
        }else if(resGroups == ResGroups.ITEM) {
            String itemId = asString("itemId");
            
            return redirect("/+" + spot.getSpotId() + "/l-" + spot.getBaseLang().toString() + "/item/" + itemId);
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

        return v.validate();
    }
}
