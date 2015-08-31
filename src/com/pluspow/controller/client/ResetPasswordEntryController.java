package com.pluspow.controller.client;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.EntryType;
import com.pluspow.model.Client;
import com.pluspow.model.ResetPasswordEntry;
import com.pluspow.service.ClientService;
import com.pluspow.service.EMailService;
import com.pluspow.service.ResetPasswordEntryService;
import com.pluspow.validator.NoContentsValidator;

public class ResetPasswordEntryController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/client/resetPassword");
        }
        
        String email = asString("email");
        
        Client client = ClientService.get(email);
        
        if (!validate(client)) {
            return forward("/client/resetPassword");
        }
        
        // エントリーを保存
        ResetPasswordEntry entry = ResetPasswordEntryService.put(client);
        
        String entryUrl = super.getAccessDomeinUrl() + "/client/confirmationEmailEntry?type=" + EntryType.RESET_PASSWORD.toString() + "&entryId=" + entry.getKey().getName();
        
        // メール確認フロー
        EMailService.resetPassword(email, client.getName(), entryUrl, isLocal());
        
        return redirect("/client/entryCompletion?type=" + EntryType.RESET_PASSWORD.toString());
    }
    
    /**
     * バリデーション
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

        return v.validate();
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate(Client client) {
        Validators v = new Validators(request);

        // メール
        v.add("email", 
            new NoContentsValidator(client, "このメールアドレスは登録されていません。"));

        return v.validate();
    }
    
    // NoContentsValidator
}
