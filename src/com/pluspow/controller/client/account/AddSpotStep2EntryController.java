package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

public class AddSpotStep2EntryController extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/client/account/addSpotStep2.jsp");
        }
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String name = asString("name");
        String catchCopy = asString("catchCopy");
        String content = asString("content");
        
        
        // ------------------------------------------
        // スポットエントリーの設定
        // ------------------------------------------
        Spot spot = sessionScope("spotEntryInfo");
        SpotService.setStep2(spot, name, catchCopy, content);
        
        SpotService.startFreePlan(spot, client);
        
        // セッション変更
        removeSessionScope("spotEntryInfo");

        return redirect("/client/account/dashboard/?spotId=" + spot.getSpotId());
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);

        // 名前
        v.add("name", 
            v.required("スポット名を入力してください。"),
            v.maxlength(30, "スポット名は最大30文字です。")
                );
        
        // キャッチフレーズ
        v.add("catchCopy", 
            v.required("スポットのキャッチフレーズを入力してください。"),
            v.maxlength(50, "スポットのキャッチフレーズは最大50文字です。")
                );
        
        // コンテンツ
        v.add("content", 
            v.required("スポットの説明を入力してください。")
        );
        
        return v.validate();
    }

}
