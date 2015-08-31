package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.SupportLang;
import com.pluspow.model.Client;
import com.pluspow.model.SignupEntry;
import com.pluspow.service.ClientService;
import com.pluspow.service.SignupEntryService;

public class AddAccountController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
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
        
        // エントリー情報を更新
        SignupEntryService.entryBeEnd(signupEntry);
        
        Client client = ClientService.add(
            signupEntry.getName(), 
            signupEntry.getEmail().getEmail(), 
            signupEntry.getPassword(), 
            SupportLang.ja);  //TODO: 国際化まで暫定
        
        // セッションの設定
        sessionScope("client", client);
        
        // ログイン後画面に遷移
        return redirect("/client/account/selectSpot");
    }
}
