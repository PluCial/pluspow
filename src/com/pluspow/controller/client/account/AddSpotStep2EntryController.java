package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Country;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;
import com.pluspow.validator.NGValidator;

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
        String phoneNumber = asString("phoneNumber");
        
        // ------------------------------------------
        // 国際電話番号の入力チェック
        // ------------------------------------------
        String phoneCountryString = asString("phoneCountryString");
        Country phoneCountry = null;
        try {
            phoneCountry = Country.valueOf(phoneCountryString);

        }catch(Exception e) {
            Validators v = new Validators(request);
            v.add("phoneCountryString",
                new NGValidator("国際電話番号コードが正しくありません。"));

            v.validate();

            return forward("/client/account/addSpotStep2.jsp");
        }
        
        
        // ------------------------------------------
        // スポットエントリーの設定
        // ------------------------------------------
        Spot spot = sessionScope("spotEntryInfo");
        SpotService.setStep2(spot, phoneCountry, phoneNumber, name, catchCopy, content);
        
        SpotService.startFreePlan(spot);
        
        // セッション変更
        removeSessionScope("spotEntryInfo");

        return redirect(PathUtils.selectSpotPage());
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
        
        // 国際電話の国
        v.add("phoneCountryString", 
            v.required("国際電話番号コードを選択してください。")
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
