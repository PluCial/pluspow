package com.pluspow.controller.spot.secure;

import java.util.Properties;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.NoLoginException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.PlanLimitException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;

public abstract class BaseController extends com.pluspow.controller.BaseController {

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
        Lang localeLang = client.getLang();
        requestScope("localeLang", localeLang);
        
        Properties appProp = getAppProp(client.getLang());
        requestScope("appProp", appProp);

        // スポットの取得
        Spot spot = getSpot();
        requestScope("spot", spot);

        // オーナーチェック
        boolean isOwner = spot.getClientRef().getKey().equals(client.getKey());
        if(!isOwner) {
            throw new NoContentsException();
        }

        requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
        requestScope("isLocal", String.valueOf(isLocal()));
        requestScope("isClientLogged", String.valueOf(client != null));
        requestScope("client", client);

        
        try {    
            // ログインしている場合
            return execute(client, spot);

        }catch(PlanLimitException e) {
            return redirect(PathUtils.changePlanPage(spot, e.getLimittype()));
        }
    }
    
    /**
     * 登録ユーザーの場合、登録情報を取得する。
     * 登録ユーザーではない、もしくGoogleアカウントにログインしていない場合は、
     * エラーを生成
     * @return
     * @throws Exception
     */
    public Spot getSpot() throws NoContentsException {

        String spotId = asString("spotId");
        if(StringUtil.isEmpty(spotId)) throw new NoContentsException();

        String lang = asString("lang");
        try {
            Spot spot = null;
            
            if(StringUtil.isEmpty(lang)) {
                spot = SpotService.getSpotBaseLang(spotId);
            }else {
                spot = SpotService.getSpot(spotId, Lang.valueOf(lang));
            }

            return spot;

        } catch (ObjectNotExistException e) {
            throw new NoContentsException();
        }
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Client client, Spot spot) throws Exception;

}
