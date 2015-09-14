package com.pluspow.controller.spot;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.service.SpotLangUnitService;

public class SelectLangController extends BaseController {

    @Override
    protected Navigation execute(Spot spot, Lang lang, Client client,
            boolean isClientLogged, boolean isOwner) throws Exception {
        
        List<SpotLangUnit> langUnitList = SpotLangUnitService.getList(spot, false);
        requestScope("langUnitList", langUnitList != null ? langUnitList : new ArrayList<SpotLangUnit>());
        
        return forward("selectLang.jsp");
    }
}
