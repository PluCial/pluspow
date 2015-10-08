package com.pluspow.controller;

import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.exception.NoLoginException;
import com.pluspow.model.Client;

public class IndexController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        
        requestScope("isLocal", String.valueOf(isLocal()));
        
        Lang localeLang = getLang();

        requestScope("localeLang", localeLang);
        
        Properties appProp = getAppProp(localeLang);
        requestScope("appProp", appProp);
        
        // -------------------------------------
        // ログインチェックと設定
        // -------------------------------------
        try {
            Client client = getLoginClient(); // 存在しない場合エラー
            requestScope("client", client);
            requestScope("isClientLogged", String.valueOf(true));

        }catch(NoLoginException e) {}
        
        
        return forward("index.jsp");
    }
}
