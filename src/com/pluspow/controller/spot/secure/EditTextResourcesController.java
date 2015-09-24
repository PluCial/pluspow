package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;
import com.pluspow.service.TextResService;

public class EditTextResourcesController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String resourcesKey = asString("resourcesKey");
        if(StringUtil.isEmpty(resourcesKey)) return null;
        
        // リソースの取得
        TextRes textRes = TextResService.getTextRes(resourcesKey);
        
        if(textRes == null) return null; 

        requestScope("textResources", textRes);
        
        
        return forward("editTextResources.jsp");
    }
}
