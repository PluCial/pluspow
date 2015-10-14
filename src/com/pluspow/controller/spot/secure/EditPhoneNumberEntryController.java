package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Country;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.MemcacheService;
import com.pluspow.service.SpotService;
import com.pluspow.validator.NGValidator;

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

        // ------------------------------------------
        // 国際電話番号の入力チェック
        // ------------------------------------------
        String phoneCountryString = asString("phoneCountryString");
        Country phoneCountry = null;
        try {
            phoneCountry = Country.valueOf(phoneCountryString);

        }catch(Exception e) {
            Validators v = new Validators(request);
            v.add("phoneCountry",
                new NGValidator("国際電話番号コードが正しくありません。"));

            v.validate();

            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }

        
        if(isDisplayFlg) {
            // ------------------------------------------
            // 電話番号を表示する場合
            // ------------------------------------------
            if (!validatePhoneNumber()) {
                requestScope("status", "NG");
                return forward("/client/ajax_response.jsp");
            }
            
            
            String phoneNumber = asString("phoneNumber");
            SpotService.setPhoneNumber(spot, spot.getLangUnit().getLang(), phoneCountry, isDisplayFlg, phoneNumber);
            
        }else {
            // ------------------------------------------
            // 電話番号を表示しない場合
            // ------------------------------------------
            SpotService.setPhoneNumber(spot, spot.getLangUnit().getLang(), phoneCountry, isDisplayFlg, null);
            
        }
        
        // キャッシュクリア
        MemcacheService.deleteSpot(spot, spot.getLangUnit().getLang());
        
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
        
        // 国際電話番号コード
        v.add("phoneCountryString", 
            v.required("国際電話番号コードを入力してください。")
                );
        
        // 電話番号
        v.add("phoneNumber", 
            v.required("電話番号を入力してください。"),
            v.minlength(10, "電話番号は正しくありません。"),
            v.regexp("^[0-9-]+$", "電話番号は正しくありません。")
                );
        
        return v.validate();
    }
}
