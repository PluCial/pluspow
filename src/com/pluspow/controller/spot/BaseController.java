package com.pluspow.controller.spot;


import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
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

        Spot spot = null;
        Lang lang = null;
        
        String langString = asString("lang");
        try {
            if(StringUtil.isEmpty(langString)) {
                // -------------------------------------
                // 言語の指定がない場合はベース言語にリダイレクトする
                // -------------------------------------
                spot = SpotService.getSpotModelOnly(spotId);
                return redirect(PathUtils.spotPage(spot, spot.getBaseLang()));

            }else {
                // -------------------------------------
                // スポットと言語を取得
                // -------------------------------------
                try {
                    lang = Lang.valueOf(langString);
                    spot = SpotService.getSpot(spotId, lang);

                }catch(IllegalArgumentException e) {
                    // 存在しない言語パラメーターの場合
                    throw new NoContentsException();

                }
            }
            
        }catch(ObjectNotExistException e) {
            // SPOTが存在しない場合
            throw new NoContentsException();
        }
        
        // -------------------------------------
        //言語の取得
        // -------------------------------------
        requestScope("spot", spot);
        requestScope("lang", lang);
        
        // -------------------------------------
        // リクエストスコープの基本設定
        // -------------------------------------
        setPageTitle(spot.getName());
        setPageDescription(spot.getCatchCopy());
        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        
        // -------------------------------------
        // ログインチェックと設定
        // -------------------------------------
        try {
            Client client = getLoginClient(); // 存在しない場合エラー
            requestScope("client", client);
            requestScope("isClientLogged", String.valueOf(true));
            
            // オーナーチェック
            boolean isOwner = spot.getClientRef().getKey().equals(client.getKey());
            requestScope("isOwner", String.valueOf(isOwner));

            return execute(spot, lang, client, true, isOwner);

        }catch(Exception e) {
            return execute(spot, lang, null, false, false);
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
    protected abstract Navigation execute(Spot spot, Lang lang, Client client, boolean isClientLogged, boolean isOwner) throws Exception;

}
