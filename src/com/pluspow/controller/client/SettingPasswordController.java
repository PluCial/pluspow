package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

public class SettingPasswordController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        
        return forward("settingPassword.jsp");
    }
}
