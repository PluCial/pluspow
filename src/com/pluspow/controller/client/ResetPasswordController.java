package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

public class ResetPasswordController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        
        
        
        
        return forward("resetPassword.jsp");
    }
}
