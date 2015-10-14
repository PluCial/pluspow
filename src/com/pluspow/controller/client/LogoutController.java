package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;

public class LogoutController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        // セッション削除
        removeSessionScope("client");

        return redirect("/client/login");
    }
}
