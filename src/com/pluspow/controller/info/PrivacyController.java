package com.pluspow.controller.info;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;

public class PrivacyController extends BaseController {

    @Override
    protected Navigation execute(Lang lang, Client client,
            boolean isClientLogged) throws Exception {
        return forward("privacy.jsp");
    }
}
