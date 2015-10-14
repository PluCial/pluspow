package com.pluspow.controller.client;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Lang;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.service.ClientService;
import com.pluspow.validator.NGValidator;

public class LoginEntryController extends BaseController {

    @Override
    public Navigation execute(Lang lang) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/client/login");
        }
        
        // クライアントの取得
        String email = asString("email");
        String password = asString("password");
        
        Client client = null;
        try {
            client = ClientService.login(email, password);
            
        } catch (ObjectNotExistException e) {
            // ログインエラー
            Validators v = new Validators(request);
            v.add("email", new NGValidator("メールアドレスもしくはパスワードが違います。"));
            v.validate();
            return forward("/client/login");
        }
        
        // ログイン処理
        sessionScope("client", client);
        
        return redirect("/client/account/selectSpot");
    }
    
    /**
     * バリデーション(入力)
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);

        // メール
        v.add("email", 
            v.required("メールアドレスを入力してください。"),
            v.maxlength(256, "メールアドレスが長すぎます。"), 
            v.minlength(6, "メールアドレスが短すぎます。"),
            v.regexp("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", "メールアドレスが正しくありません。"));

        // パスワード
        v.add("password", 
            v.required("パスワードを入力してください。")
             );

        return v.validate();
    }
}
