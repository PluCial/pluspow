package com.pluspow.controller.spot.secure;

import java.util.ArrayList;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;

public class ItemAddController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot baseSpot) throws Exception {
        
        requestScope("suppertLangList", new ArrayList<Lang>());
        
        return forward("itemAdd.jsp");
    }
}
