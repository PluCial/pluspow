package com.pluspow.controller.spot;


import java.util.Properties;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.App;
import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.NoLoginException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        String spotId = asString("spotId");
        if(StringUtil.isEmpty(spotId)) {
            throw new NoContentsException();
        }

        Lang lang = null;
        Client client = null;
        boolean isClientLogged = false;
        boolean isOwner = false;
        
        Lang paramLang = getLangByParameter();
        Lang subDomainLang = getLangBySubDomain();
        
        // -------------------------------------
        // ログインチェック
        // -------------------------------------
        try {
            // -------------------------------------
            // ログインしている場合
            // -------------------------------------
            client = getLoginClient(); // 存在しない場合エラー
            isClientLogged = true;

        }catch(NoLoginException e) {
            // ログインしていない、かつパラメーターで言語を指定している場合
            // ローカルの場合は実行しない。
            if(!isLocal() && paramLang != null) {
                if(request.getServletPath().indexOf("selectLang") < 0) {
                    return redirect(PathUtils.spotPageLangSubDomainUrl(getScheme(), spotId, paramLang));
                }
            }
        }
        
        
        // -------------------------------------
        // 言語の選定
        // -------------------------------------
        if(paramLang != null) {
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
        
        // -------------------------------------
        // スポットの取得
        // -------------------------------------
        Spot spot = null;
        try{
            spot = SpotService.getSpot(spotId, lang);
            
        }catch(ObjectNotExistException e) {
            // SPOTが存在しない場合
            throw new NoContentsException();
        }
        
        
        // -------------------------------------
        // スポット表示用リクエストパラメーターの設定
        // -------------------------------------
        requestScope("spot", spot);
        requestScope("lang", lang);

        setPageTitle(spot.getName());
        setPageDescription(spot.getCatchCopy());
        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));

        isOwner = isClientLogged && spot.getClientRef().getKey().equals(client.getKey());
        
        requestScope("client", client);
        requestScope("isClientLogged", String.valueOf(isClientLogged));
        requestScope("isOwner", String.valueOf(isOwner));
        requestScope("isLocal", String.valueOf(isLocal()));
        
        
        requestScope("scheme", getScheme());
        
        return execute(spot, lang, client, isClientLogged, isOwner);
    }
    
    

    /**
     * リクエスト処理
     * @param spot
     * @param lang
     * @param client
     * @param isClientLogged
     * @param isOwner
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Spot spot, Lang lang, Client client, boolean isClientLogged, boolean isOwner) throws Exception;

}
