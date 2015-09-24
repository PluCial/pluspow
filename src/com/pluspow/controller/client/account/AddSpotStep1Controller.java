package com.pluspow.controller.client.account;

import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.enums.ServicePlan;
import com.pluspow.exception.AddSpotLimtException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public class AddSpotStep1Controller extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        Spot spot = sessionScope("spotEntryInfo");
        
        // ---------------------------------------------------
        // 新規作成の場合
        // ---------------------------------------------------
        if(spot == null) {
            
            // ---------------------------------------------------
            // スポット作成制限チェック
            // ---------------------------------------------------
            List<Spot> spotList = SpotService.getSpotListByClient(client);
            try {
                for(Spot spotModel: spotList) {
                    if(spotModel.getPlan() == ServicePlan.FREE) throw new AddSpotLimtException();
                }
                
            } catch (AddSpotLimtException e) {

                return forward("/client/account/selectSpot?limitOver=true");
            }
            
            requestScope("email", client.getEmail().getEmail());
            return forward("addSpotStep1.jsp");
        }
        
        // ---------------------------------------------------
        // 次の画面から戻って来た戻って来た場合、セッション上の登録項目を
        // Formにセット
        // ---------------------------------------------------
        requestScope("spotId", spot.getSpotId());
        requestScope("address", spot.getAddress());
        requestScope("phoneNumber", spot.getPhoneNumber());
        requestScope("email", spot.getEmail().getEmail());
        requestScope("owner", true);
        
        
        return forward("addSpotStep1.jsp");
    }
}
