package com.pluspow.controller.client.account;

import java.util.Properties;

import org.slim3.controller.Navigation;

import com.pluspow.controller.AppBaseController;
import com.pluspow.exception.NoLoginException;
import com.pluspow.model.Client;

public abstract class BaseController extends AppBaseController {

    @Override
    protected Navigation run() throws Exception {

        Client client = null;
        try {
            client = getLoginClient();
        }catch(NoLoginException e) {
            return redirect("/client/login");
        }
        
        // -------------------------------------
        // 表示言語
        // -------------------------------------
        requestScope("localeLang", client.getLang());
        Properties appProp = getAppProp(client.getLang());
        requestScope("appProp", appProp);

        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        requestScope("isClientLogged", String.valueOf(client != null));
        requestScope("client", client);

        return execute(client);
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Client client) throws Exception;

}
