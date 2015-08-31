package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {


        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));

        return execute();
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute() throws Exception;

}
