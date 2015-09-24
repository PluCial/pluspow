package com.pluspow.controller.info;


import org.datanucleus.util.StringUtils;
import org.slim3.controller.Navigation;

import com.pluspow.App;
import com.pluspow.enums.Lang;
import com.pluspow.model.Client;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        // -------------------------------------
        // 言語の設定
        // -------------------------------------
        Lang lang = null;
        String langString = asString("lang");
        // 指定してない場合はサービスデフォルト言語に設定
        try {
            if(StringUtils.isEmpty(langString)) {
                lang = App.APP_BASE_LANG;

            }else {
                lang = Lang.valueOf(langString);
                
            }

        }catch(Exception e) {
            lang = App.APP_BASE_LANG;
        }
        requestScope("lang", lang);
        
        // -------------------------------------
        // リクエストスコープの基本設定
        // -------------------------------------
        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        
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
