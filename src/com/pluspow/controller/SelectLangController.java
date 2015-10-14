package com.pluspow.controller;

import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.App;
import com.pluspow.enums.Lang;

public class SelectLangController extends BaseController {

    @Override
    public Navigation run() throws Exception {
        
        Lang localeLang = null;
        Lang paramLang = getLangByParameter();
        Lang subDomainLang = getLangBySubDomain();
        
        // -------------------------------------
        // 言語の選定
        // -------------------------------------
        if(paramLang != null) {
            localeLang = paramLang;
            
        }else if(paramLang == null && subDomainLang != null) {
            localeLang = subDomainLang;
            
        }else {
            localeLang = App.APP_BASE_LANG;
        }

        requestScope("localeLang", localeLang);
        Properties appProp = getAppProp(localeLang);
        requestScope("appProp", appProp);
        
        return forward("selectLang.jsp");
    }
}
