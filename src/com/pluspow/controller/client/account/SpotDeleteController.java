package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;

public class SpotDeleteController extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        return forward("spotDelete.jsp");
    }
}
