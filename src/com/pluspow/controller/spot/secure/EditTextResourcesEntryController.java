package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.TextResRole;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemTextResService;
import com.pluspow.service.SpotTextResService;

public class EditTextResourcesEntryController extends BaseController {
    
    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String resourcesKey = asString("resourcesKey");
        String content = asString("content");
        TextResRole resRole = TextResRole.valueOf(asString("resRole"));
        
        if(TextResRole.isSpotRole(resRole)) {
            // Spotリソースを更新
            SpotTextResService.update(resourcesKey, content);
            
        }else if(TextResRole.isItemRole(resRole)) {
            // アイテムのリソースを更新
            ItemTextResService.update(resourcesKey, content);
            
        }else {
            throw new IllegalArgumentException();
        }
        
        requestScope("status", "OK");
        return forward("/client/ajax_response.jsp");
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);

        v.add("spotId", v.required());
        v.add("resourcesKey", v.required());
        v.add("resRole", v.required());
        
        // コンテンツ
        v.add("content", v.required());
        
        return v.validate();
    }
}
