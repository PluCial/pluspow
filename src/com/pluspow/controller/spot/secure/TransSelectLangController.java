package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;

public class TransSelectLangController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        return forward("transSelectLang.jsp");
    }
}
