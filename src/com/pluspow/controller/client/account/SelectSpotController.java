package com.pluspow.controller.client.account;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public class SelectSpotController extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        List<Spot> spotList = SpotService.getSpotListByClient(client);
        
        requestScope("spotList", spotList == null ? new ArrayList<Spot>() : spotList);
        
        return forward("selectSpot.jsp");
    }
}
