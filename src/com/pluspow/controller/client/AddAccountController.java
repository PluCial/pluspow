package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.Lang;
import com.pluspow.model.Client;
import com.pluspow.model.SignupEntry;
import com.pluspow.service.ClientService;
import com.pluspow.service.SignupEntryService;

public class AddAccountController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        String entryId = asString("entryId");
        
        // DBからエントリー情報を取得
        SignupEntry signupEntry = SignupEntryService.getByKey(entryId);
        
        // 項目チェック
        if(signupEntry == null 
                || signupEntry.isInvalid()
                || signupEntry.getName().isEmpty()
                || signupEntry.getEmail().getEmail().isEmpty() 
                || signupEntry.getPassword().isEmpty()) {
            return redirect("/client/account/register");
        }
        
        // クライアントの追加
        Client client = ClientService.add(
            signupEntry.getName(), 
            signupEntry.getEmail().getEmail(), 
            signupEntry.getPassword(), 
            signupEntry.getLang());
        
        // セッションの設定
        sessionScope("client", client);
        
        // エントリー情報の削除
        SignupEntryService.delete(signupEntry);
        
        // ログイン後画面に遷移
        return redirect("/client/account/selectSpot");
    }
}
