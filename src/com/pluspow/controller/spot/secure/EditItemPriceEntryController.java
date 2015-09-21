package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;

public class EditItemPriceEntryController extends BaseController {
    
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
        
        String itemId = asString("itemId");
        String price = asString("price");
        
        Item item = null;
        try {
            item = ItemService.getModelOnly(itemId);
            ItemService.setPrice(item, Double.valueOf(price));
            
        }catch(ObjectNotExistException e) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
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
        v.add("itemId", v.required());
        // 値段
        v.add("price", 
            v.required("金額を入力してください。<br />無料の場合は ¥0 と入力してください。"),
            v.integerType("金額には半角の数字を入力してください。")
        );
        
        return v.validate();
    }
}
