package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;

public class AddSpotStep2Controller extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        // 戻って来た場合のための設定
        Spot spot = sessionScope("spotEntryInfo");
        
        if(spot == null) return forward("addSpotStep2.jsp");
        
        if(!StringUtil.isEmpty(spot.getName())) {
            requestScope("name", spot.getName());
        }
        
        if(!StringUtil.isEmpty(spot.getCatchCopy())) {
            requestScope("catchCopy", spot.getCatchCopy());
        }
        
        if(!StringUtil.isEmpty(spot.getDetail())) {
            requestScope("content", spot.getDetail());
        }
        
        return forward("addSpotStep2.jsp");
    }
}
