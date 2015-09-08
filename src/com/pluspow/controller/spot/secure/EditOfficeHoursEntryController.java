package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.DayOfWeek;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.OfficeHoursService;

public class EditOfficeHoursEntryController extends BaseController {
    
    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        

        boolean isOpen = asBoolean("isOpenFlg");
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(asString("dayOfWeek"));
        
        
        if(isOpen) {
            // 営業日の場合
            if(!isOpenvalidate()) {
                requestScope("status", "NG");
                return forward("/client/ajax_response.jsp");
            }
            
            // 必要なリクエストパラメーターを取得
            
            String[] openTime = asString("openTime").split(":");
            String[] closeTime = asString("closeTime").split(":");
            
            // 営業時間を更新
            OfficeHoursService.update(
                spot, 
                dayOfWeek, 
                Integer.valueOf(openTime[0]), 
                Integer.valueOf(openTime[1]), 
                Integer.valueOf(closeTime[0]), 
                Integer.valueOf(closeTime[1]));
            
        }else {
            // 非営業日に変更
            OfficeHoursService.setIsNotOpen(spot, dayOfWeek);
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
        v.add("isOpenFlg", v.required());
        v.add("dayOfWeek", v.required());
        
        return v.validate();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean isOpenvalidate() {
        Validators v = new Validators(request);
        
        v.add("openTime", v.required());
        v.add("closeTime", v.required());
        
        return v.validate();
    }
}
