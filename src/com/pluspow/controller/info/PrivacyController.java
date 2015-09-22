package com.pluspow.controller.info;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class PrivacyController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("privacy.jsp");
    }
}
