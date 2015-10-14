package com.pluspow.controller.client;

import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.App;
import com.pluspow.enums.Lang;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        Lang lang = null;
        Lang paramLang = getLangByParameter();
        Lang subDomainLang = getLangBySubDomain();
        
        // -------------------------------------
        // 言語の選定
        // -------------------------------------
        if(isLocal()) {
            lang = Lang.ja;
            
        }else if(paramLang != null) {
            lang = paramLang;
            
        }else if(paramLang == null && subDomainLang != null) {
            lang = subDomainLang;
            
        }else {
            lang = App.APP_BASE_LANG;
        }
        
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
