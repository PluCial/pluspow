package com.pluspow.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ServletContextLocator;
import org.slim3.util.StringUtil;

import com.pluspow.App;
import com.pluspow.enums.Lang;
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
        System.out.println(error.getMessage());
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
     * パラメーターから言語を取得
     * @return
     */
    public Lang getLangByParameter() {
        String langString = asString("lang");
        if(StringUtil.isEmpty(langString)) {
            return null;
        }
        
        try {
            return Lang.valueOf(langString);
            
        }catch(IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * サブドメインから言語を取得
     * @return
     */
    public Lang getLangBySubDomain() {
        try {
            String serverName = request.getServerName() != null ? request.getServerName() : "";
            String[] names = serverName.split("\\.");
            
            return Lang.valueOf(names[0]);
            
        }catch(IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * アクセスしているドメインURLを取得
     */
    public String getAccessDomeinUrl() {
        
        String scheme = getScheme() != null ? getScheme() + "://" : "";
        String serverName = getServerName() != null ? getServerName() : "";
        String serverPort = request.getServerPort() > 8000 ? ":" + request.getServerPort() : "";
        
        return scheme + serverName + serverPort;
    }
    
    /**
     * request scheme の取得
     * @return
     */
    public String getScheme() {
        return request.getScheme();
    }
    
    /**
     * request serverName
     * @return
     */
    public String getServerName() {
        return request.getServerName();
    }
    
    /**
     * Lang 要素を除いたドメインを返す
     * @return
     */
    public String getMainDomain() {
        try {
            String serverName = getServerName() != null ? getServerName() : "";
            String[] names = serverName.split("\\.");
            
            if(names.length > 2) {
                
                return names[names.length - 2] + "." + names[names.length - 1];
            }
            
            return serverName;
            
        }catch(Exception e) {
            return null;
        }
    }
    
    /**
     * 言語の取得
     * <pre>
     * 優先順位：
     * ①パラメーター
     * ②ドメイン
     * ③両方がなければ、サービスの基準言語
     * </pre>
     * @return
     */
    public Lang getLang() {
        Lang lang = null;
        
        Lang paramLang = getLangByParameter();
        Lang subDomainLang = getLangBySubDomain();
        
        if(paramLang != null) {
            lang = paramLang;
            
        }else if(paramLang == null && subDomainLang != null) {
            lang = subDomainLang;
            
        }else {
            lang = App.APP_BASE_LANG;
        }
        
        return lang;
    }
    
    /**
     * プロパティを取得
     * @return
     * @throws IOException 
     */
    public Properties getAppProp(Lang lang) throws IOException {
        Properties prop = new Properties();
        InputStream in = this.getClass().getResourceAsStream("/application_" + lang.toString() + ".properties");
        try {
            prop.load(in);
        } finally {
            try {
                in.close();
            } catch (Exception e) {}
        }
        
        return prop;
    }
}
