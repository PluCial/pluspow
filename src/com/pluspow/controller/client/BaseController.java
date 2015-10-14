package com.pluspow.controller.client;

import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.controller.AppBaseController;
import com.pluspow.enums.Lang;

public abstract class BaseController extends AppBaseController {

    @Override
    protected Navigation run() throws Exception {
        
        Lang lang = getLang();
        
        // -------------------------------------
        // 表示言語
        // -------------------------------------
        requestScope("localeLang", lang);
        Properties appProp = getAppProp(lang);
        requestScope("appProp", appProp);
        
        // URL
        requestScope("requestUrl", request.getServletPath());


        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));

        return execute(lang);
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Lang lang) throws Exception;

}
