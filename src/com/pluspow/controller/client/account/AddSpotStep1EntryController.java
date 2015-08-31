package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import com.pluspow.model.Client;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.service.GeoService;
import com.pluspow.service.SpotService;
import com.pluspow.validator.GeoAddressValidator;
import com.pluspow.validator.GeoStatusValidator;
import com.pluspow.validator.TooManyValidator;

public class AddSpotStep1EntryController extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            return forward("/client/account/addSpotStep1.jsp");
        }
        
        // ------------------------------------------
        // リクエストパラメーターの取得
        // ------------------------------------------
        String spotId = asString("spotId");
        String address = asString("address");
        String phoneNumber = asString("phoneNumber");
        String email = asString("email");
        
        // ------------------------------------------
        // spotId重複チェック
        // ------------------------------------------
        Spot spot = SpotService.getSpotModelOnly(spotId);
        if (!validate(spot)) {
            return forward("/client/account/addSpotStep1.jsp");
        }
        
        // ------------------------------------------
        // 住所の整合性チェック
        // ------------------------------------------
        GeoModel geoModel = GeoService.getGeoModel(address, client.getLang());
        if (!validate(geoModel)) {
            return forward("/client/account/addSpotStep1.jsp");
        }
        
        // ------------------------------------------
        // スポットエントリーの設定
        // ------------------------------------------
        spot = SpotService.setStep1(client, spotId, client.getLang(), address, phoneNumber, email, geoModel);
        // セッションへ保存
        sessionScope("spotEntryInfo", spot);

        return redirect("/client/account/addSpotStep2");
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // spotId
        v.add("spotId", 
            v.required("スポットIDを入力してください。"), 
            v.minlength(5, "IDは5文字以上必要です。"),
            v.regexp("^[a-z0-9_-]+$", "スポットIDで使用できる文字は半角英語(小文字)、0〜9の数字、[-]、[_]のみです。"));
        
        // 住所
        v.add("address", 
            v.required("住所を入力してください。")
                );
        
        // 電話番号
        v.add("phoneNumber", 
            v.required("電話番号を入力してください。"),
            v.minlength(10, "電話番号は正しくありません。"),
            v.regexp("^[0-9-]+$", "電話番号は正しくありません。")
                );
        
        // メール
        v.add("email", 
            v.required("メールアドレスを入力してください。"),
            v.maxlength(256, "メールアドレスが長すぎます。"), 
            v.minlength(6, "メールアドレスが短すぎます。"),
            v.regexp("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*([,;]\\s*\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", "メールアドレスが正しくありません。"));
        
        // オーナー
        v.add("owner", 
            v.required("スポットの所有者以外は登録できません。")
             );
        
        return v.validate();
    }
    
    /**
     * スポット重複バリデーション
     * @return
     */
   private boolean validate(Spot spot) {
       Validators v = new Validators(request);
       
       // spotId
       v.add("spotId", v.required(), new TooManyValidator(spot, "このスポットIDは既に利用されています。"));
       
       return v.validate();
   }
   
   /**
    * 住所バリデーション
    * @return
    */
   private boolean validate(GeoModel geoModel) {
       Validators v = new Validators(request);

       // 住所
       v.add("address",
           new GeoStatusValidator(geoModel, "住所の確認に失敗しました。しばらく立ってから再度実行してください。"),
           new GeoAddressValidator(geoModel, "入力した住所が正しくないか、もしくは完全な住所ではありません。")
       );

       return v.validate();
   }
}
