package com.pluspow.controller.info;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;
import com.pluspow.service.EMailService;

public class ContactEntryController extends BaseController {

    @Override
    protected Navigation execute(Lang lang, Client client,
            boolean isClientLogged) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/info/contact");
        }
        
        String name = asString("name");
        String email = asString("email");
        String subject = asString("subject");
        String message = asString("message");
        String phoneNumber = asString("phoneNumber");
        
        // メール確認フロー
        EMailService.contact(email, name, phoneNumber, subject, message, isLocal());
        
        return redirect("/info/contactCompletion");
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

        // 名前
        v.add("name", 
            v.required("名前を入力してください。")
             );
        
        // 件名
        v.add("subject", 
            v.required("件名を入力してください。")
             );

        // 本文
        v.add("message", 
            v.required("本文を入力してください。")
             );
        
        // 電話番号
        v.add("phoneNumber", 
            v.required("電話番号を入力してください。"),
            v.minlength(10, "電話番号は正しくありません。"),
            v.regexp("^[0-9-]+$", "電話番号は正しくありません。")
                );
        
        return v.validate();
    }
}

