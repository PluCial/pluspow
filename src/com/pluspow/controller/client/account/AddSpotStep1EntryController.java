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
        int floor = asInteger("floor");
        
        
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
        // 国の取得
        // ------------------------------------------
        Country country = Country.valueOf(geoModel.getCountryShortName());
        
        // ------------------------------------------
        // スポットエントリーの設定
        // ------------------------------------------
        spot = SpotService.setStep1(
            client, 
            spotId, 
            country, 
            address, 
            floor, 
            geoModel);
        
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
        
        // フロアー
        v.add("floor", 
            v.required("フロアーを入力してください。"),
            v.integerType("半角数字を入力してください。")
                );
        
        // オーナー
        v.add("owner", 
            v.required("スポットの所有者以外は登録できません。")
             );
        
        return v.validate();
    }
}
