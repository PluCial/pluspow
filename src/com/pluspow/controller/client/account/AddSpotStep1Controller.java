package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;

public class AddSpotStep1Controller extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        // 戻って来た場合のための設定
        Spot spot = sessionScope("spotEntryInfo");
        
        if(spot == null) {
            requestScope("email", client.getEmail().getEmail());
            return forward("addSpotStep1.jsp");
        }
        
        requestScope("spotId", spot.getSpotId());
        requestScope("address", spot.getAddress());
        requestScope("phoneNumber", spot.getPhoneNumber());
        requestScope("email", spot.getEmail().getEmail());
        requestScope("owner", true);
        
        
        return forward("addSpotStep1.jsp");
    }
}
