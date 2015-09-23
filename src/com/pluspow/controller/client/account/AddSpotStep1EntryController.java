package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Country;
import com.pluspow.exception.GeocoderLocationTypeException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.service.GeoService;
import com.pluspow.service.SpotService;
import com.pluspow.validator.NGValidator;

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
        Spot spot = null;
        try {
            spot = SpotService.getSpotModelOnly(spotId);
            
            // Spot Id 重複エラー
            Validators v = new Validators(request);
            v.add("spotId", new NGValidator("このスポットIDは既に利用されています。"));
            v.validate();
            return forward("/client/account/addSpotStep1.jsp");
            
        } catch (ObjectNotExistException e) {}
        
        
        // ------------------------------------------
        // 住所の整合性チェック
        // ------------------------------------------
        GeoModel geoModel = null;
        try {
            geoModel = GeoService.getGeoModel(address, client.getLang());
            
        } catch (Exception e) {
            Validators v = new Validators(request);
            
            if(e instanceof GeocoderLocationTypeException) {
                v.add("address",
                    new NGValidator("入力した住所が正しくないか、もしくは完全な住所ではありません。"));
                
            }else {
                v.add("address",
                    new NGValidator("住所の確認に失敗しました。しばらく立ってから再度実行してください。"));
            }
            
            v.validate();
            
            return forward("/client/account/addSpotStep1.jsp");
        }
        
        // ------------------------------------------
        // 国のサポートチェック
        // ------------------------------------------
        Country country = null;
        try {
            country = Country.valueOf(geoModel.getCountryShortName());
            
            if(!country.isSupport()) {
                throw new IllegalArgumentException();
            }
            
        }catch(IllegalArgumentException e) {
            Validators v = new Validators(request);
            v.add("address",
                new NGValidator(geoModel.getCountryLongName() + "のスポットは現在サポートしていません。"));
            v.validate();

            return forward("/client/account/addSpotStep1.jsp");

        }
        
        // ------------------------------------------
        // スポットエントリーの設定
        // ------------------------------------------
        spot = SpotService.setStep1(client, spotId, client.getLang(), country, address, phoneNumber, email, geoModel);
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
}
