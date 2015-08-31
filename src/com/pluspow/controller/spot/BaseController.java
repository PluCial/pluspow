package com.pluspow.controller.spot;


import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.SupportLang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        // -------------------------------------
        // スポットモデルの取得（付属情報なし）
        // -------------------------------------
        Spot spot = getSpotModelOnly();
        
        // -------------------------------------
        // 言語の指定がない場合はベース言語にリダイレクトする
        // -------------------------------------
        String langString = asString("lang");
        if(StringUtil.isEmpty(langString)) {
            return redirect("/+" + spot.getSpotId() + "/l-" + spot.getBaseLang().getLangKey() + "/");
        }
        
        // -------------------------------------
        //言語の取得
        // -------------------------------------
        SupportLang lang = null;
        try {
            lang = SupportLang.valueOf(langString);
            requestScope("lang", lang);

        }catch(IllegalArgumentException e) {

            if(langString.equals("add")) {

                // 言語の追加処理へ
                return redirect("/spot/secure/transSelectLang?spotId=" + spot.getSpotId());
                
            }else {
                throw new NoContentsException();
            }
        }
        
        // -------------------------------------
        // スポット情報を取得
        // -------------------------------------
        spot = SpotService.getSpot(spot.getSpotId(), lang);
        if(spot == null) throw new NoContentsException();
        requestScope("spot", spot);
        
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
     * 対象スポットの取得
     * 
     * @return
     * @throws NoContentsException
     */
    public Spot getSpotModelOnly() throws NoContentsException {

        String spotId = asString("spotId");
        
        // spot Id がNullの場合
        if(StringUtil.isEmpty(spotId)) {
            throw new NoContentsException();
        }
        
        // スポットの取得
        Spot spot = SpotService.getSpotModelOnly(spotId);

        if(spot == null) throw new NoContentsException();

        return spot;
    }

    /**
     * リクエスト処理
     * @param spot
     * @param client
     * @param isClientLogged
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Spot spot, SupportLang lang, Client client, boolean isClientLogged, boolean isOwner) throws Exception;

}
