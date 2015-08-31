package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;

import com.pluspow.enums.TextResRole;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.TextResources;
import com.pluspow.service.ItemTextResService;
import com.pluspow.service.SpotTextResService;

public class EditTextResourcesController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String resourcesKey = asString("resourcesKey");
        TextResRole resRole = TextResRole.valueOf(asString("resRole"));
        
        // リソースの取得
        TextResources textResources = null;
        if(TextResRole.isSpotRole(resRole)) {
            textResources = SpotTextResService.getResources(resourcesKey);
            
        }else if(TextResRole.isItemRole(resRole)) {
            textResources = ItemTextResService.getResources(resourcesKey);
            
        }

        requestScope("textResources", textResources);
        
        
        return forward("editTextResources.jsp");
    }
}
