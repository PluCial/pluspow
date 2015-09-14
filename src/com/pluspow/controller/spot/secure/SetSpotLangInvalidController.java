package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public class SetSpotLangInvalidController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        boolean invalid = asBoolean("invalid");
        Lang lang = Lang.valueOf(asString("lang"));
        
        SpotService.setInvalid(spot, lang, invalid);

        return redirect("/+" + spot.getSpotId() + "/l-" + spot.getBaseLang().toString() + "/");
    }
}
