package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;
import com.pluspow.service.TextResService;

public class EditTextResourcesController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String resourcesKey = asString("resourcesKey");
        
        // リソースの取得
        TextRes textRes = TextResService.getTextRes(resourcesKey);
        
        if(textRes == null) return null; 

        requestScope("textResources", textRes);
        
        
        return forward("editTextResources.jsp");
    }
}
