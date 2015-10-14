package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;

public class RegisterController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        return forward("register.jsp");
    }
}
