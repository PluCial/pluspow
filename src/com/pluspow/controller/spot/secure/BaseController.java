package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.NoLoginException;
import com.pluspow.exception.PermissionException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public abstract class BaseController extends com.pluspow.controller.BaseController {

    @Override
    protected Navigation run() throws Exception {
        
        try {
            Client client = getLoginClient();
            Spot spot = getSpot();
            
            // オーナーチェック
            boolean isOwner = spot.getClientRef().getKey().equals(client.getKey());
            if(!isOwner) {
                throw new PermissionException();
            }

            requestScope("isSmartPhone", String.valueOf(isSmartPhone()));
            requestScope("isLocal", String.valueOf(isLocal()));
            requestScope("isClientLogged", String.valueOf(client != null));
            requestScope("client", client);
            
            requestScope("spot", spot);
            
            // ログインしている場合
            return execute(client, spot);

        }catch(NoLoginException e) {
            return redirect("/client/login");
            
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
        Spot spot = null;
        if(StringUtil.isEmpty(lang)) {
        spot = SpotService.getSpot(spotId);
        }else {
            spot = SpotService.getSpot(spotId, Lang.valueOf(lang));
        }

        if(spot == null) throw new NoContentsException();

        return spot;
    }

    /**
     * リクエスト処理
     * @return
     * @throws Exception
     */
    protected abstract Navigation execute(Client client, Spot spot) throws Exception;

}
