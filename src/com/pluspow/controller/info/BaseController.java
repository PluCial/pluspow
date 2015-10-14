package com.pluspow.controller.info;


import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.controller.AppBaseController;
import com.pluspow.enums.Lang;
import com.pluspow.model.Client;

public abstract class BaseController extends AppBaseController {

    @Override
    protected Navigation run() throws Exception {
        
        // -------------------------------------
        // 言語の設定
        // -------------------------------------
        Lang lang = getLang();
        
        requestScope("localeLang", lang);
        Properties appProp = getAppProp(lang);
        requestScope("appProp", appProp);
        
        // -------------------------------------
        // リクエストスコープの基本設定
        // -------------------------------------
        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        
        // -------------------------------------
        // ログインチェックと設定
        // -------------------------------------
        try {
            Client client = getLoginClient(); // 存在しない場合エラー
            requestScope("client", client);
            requestScope("isClientLogged", String.valueOf(true));

            return execute(lang, client, true);

        }catch(Exception e) {
            return execute(lang, null, false);
        }
    }

    /**
     * リクエスト処理
     * @param spot
     * @param client
     * @param isClientLogged
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Lang lang, Client client, boolean isClientLogged) throws Exception;

}
