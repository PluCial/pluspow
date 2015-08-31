package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;

import com.pluspow.exception.NoLoginException;
import com.pluspow.model.Client;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        try {
            Client client = getLoginClient();

            requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
            requestScope("isLocal", String.valueOf(isLocal()));
            requestScope("isClientLogged", String.valueOf(client != null));
            requestScope("client", client);
            
            // ログインしている場合
            return execute(client);

        }catch(NoLoginException e) {
            return redirect("/client/login");
        }
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Client client) throws Exception;

}
