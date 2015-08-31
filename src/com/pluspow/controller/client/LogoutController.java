package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

public class LogoutController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // セッション削除
        removeSessionScope("client");

        return redirect("/client/login");
    }
}
