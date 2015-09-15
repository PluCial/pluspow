package com.pluspow.controller.client.account.dashboard;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.NoLoginException;
import com.pluspow.exception.ObjectNotExistException;
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
            requestScope("lang", spot.getBaseLang()); // アイテム追加ページで必要
            
            // アクティブメニューの設定
            if(!StringUtil.isEmpty(request.getServletPath())) {
                String[] servletPathParts =  request.getServletPath().split("/");
                requestScope("activeMenu", servletPathParts[servletPathParts.length - 1]);
                
                if(isLocal()) {
                    System.out.println(servletPathParts[servletPathParts.length - 1]);
                }
            }
            
            // ログインしている場合
            return execute(client, spot);

        }catch(NoLoginException e) {
            return redirect("/client/login");
            
        }catch(NoContentsException e) {
            return redirect("/client/account/selectSpot");
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

        try {
            Spot spot = SpotService.getSpotBaseLang(spotId);
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
