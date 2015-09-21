package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public class EditPhoneNumberEntryController extends BaseController {
    
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
        
        boolean isDisplayFlg = asBoolean("isDisplayFlg");
        
        if(isDisplayFlg) {
            if (!validatePhoneNumber()) {
                requestScope("status", "NG");
                return forward("/client/ajax_response.jsp");
            }
            
            String phoneNumber = asString("phoneNumber");
            SpotService.setPhoneNumber(spot, spot.getLangUnit().getLang(), isDisplayFlg, phoneNumber);
            
        }else {
            SpotService.setPhoneNumber(spot, spot.getLangUnit().getLang(), isDisplayFlg, null);
            
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
        v.add("isDisplayFlg", v.required());
        
        return v.validate();
    }
    
    private boolean validatePhoneNumber() {
        Validators v = new Validators(request);
        
        // 電話番号
        v.add("phoneNumber", 
            v.required("電話番号を入力してください。"),
            v.minlength(10, "電話番号は正しくありません。"),
            v.regexp("^[0-9-]+$", "電話番号は正しくありません。")
                );
        
        return v.validate();
    }
}
