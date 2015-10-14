package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.SearchApiException;
import com.pluspow.model.Client;
import com.pluspow.model.ItemTextRes;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;
import com.pluspow.service.ItemTextResService;
import com.pluspow.service.MemcacheService;
import com.pluspow.service.SearchApiService;
import com.pluspow.service.SpotTextResService;
import com.pluspow.service.TextResService;

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
        
        // リソースの取得
        TextRes textRes = TextResService.getTextRes(resourcesKey);
        
        if(textRes.getRole().isSpotRole()) {
            // Spotリソースを更新
            SpotTextResService.update(resourcesKey, content);
            
            // 検索APIの更新
            try {
                SearchApiService.putDocument(spot, textRes.getLang());
            }catch(Exception e) {
                throw new SearchApiException();
            }
            
            // キャッシュクリア
            MemcacheService.deleteSpot(spot, textRes.getLang());
            
            
            
        }else if(textRes.getRole().isItemRole()) {
            // アイテムのリソースを更新
            ItemTextRes itemRes = ItemTextResService.update(resourcesKey, content);
            
            // キャッシュクリア
            MemcacheService.deleteItem(itemRes.getItemRef().getKey().getName(), itemRes.getLang());
            
        }else {
            throw new ArgumentException();
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
        
        // コンテンツ
        v.add("content", v.required());
        
        return v.validate();
    }
}
