package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;

public class LoginController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        return forward("login.jsp");
    }
}
