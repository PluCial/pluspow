package com.pluspow.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ServletContextLocator;

import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.NoLoginException;
import com.pluspow.model.Client;

public abstract class BaseController extends Controller {
    
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(BaseController.class.getName());

    /**
     * デバイスがスマートフォンであるか判定
     * @param request
     * @return
     */
    protected boolean isSmartPhone() {

        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if(userAgent != null && (userAgent.indexOf("iphone") > 0 || userAgent.indexOf("android") > 0)) {
            return true;
        }

        return false;
    }
    
    /**
     * 開発環境かどうか
     * @return
     */
    protected boolean isLocal(){
        return ServletContextLocator.get().getServerInfo().indexOf("Development") >= 0;
    }
    
    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
    public Client getLoginClient() throws NoLoginException {

        Client client = sessionScope("client");

        if(client == null) throw new NoLoginException();

        return client;
    }

    
    /**
     * エラーハンドリング
     */
    @Override
    protected Navigation handleError(Throwable error) throws Throwable {
        
        // 404エラー
        if(error instanceof NoContentsException) {
            return forward("/error404");
        }
        
        // 開発環境ではなく、404ではないエラーが発生した場合エラーログを出力
        logger.log(Level.WARNING, error.getMessage(), error);
        
        // 500エラー画面に遷移する
        return redirect("/error500");
    }
    
    /**
     * ページタイトルの設定
     * @return
     */
    public void setPageTitle(String title) {
        requestScope("pageTitle", title);
    }
    
    /**
     * ページDescriptionの設定
     * @return
     */
    public void setPageDescription(String pageDescription) {
        requestScope("pageDescription", pageDescription);
    }
    
    /**
     * アクセスしているドメインURLを取得
     */
    public String getAccessDomeinUrl() {
        
        String scheme = request.getScheme() != null ? request.getScheme() + "://" : "";
        String serverName = request.getServerName() != null ? request.getServerName() : "";
        String serverPort = request.getServerPort() > 8000 ? ":" + request.getServerPort() : "";
        
        return scheme + serverName + serverPort;
    }


}
