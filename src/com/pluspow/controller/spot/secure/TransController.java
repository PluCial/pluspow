package com.pluspow.controller.spot.secure;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TransException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;
import com.pluspow.service.SpotLangUnitService;
import com.pluspow.service.SpotService;
import com.pluspow.service.SpotTextResService;

public class TransController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!validate()) {
            throw new ArgumentException();
        }
        
        Lang transLang = Lang.valueOf(asString("transLang"));
        
        int transCharCount = 0;
        
        List<? extends TextRes> transContentsList = null;
        
        ObjectType objectType = ObjectType.valueOf(asString("objectType"));
        

        switch(objectType) {
        case SPOT:
            try {
                // ------------------------------------------
                // 無効になっている言語を復活させる
                // ------------------------------------------
                SpotLangUnitService.get(spot, transLang);
                SpotService.setInvalid(spot, transLang, false);

                return redirect("/+" + spot.getSpotId() + "/l-" + transLang.toString() + "/");
            
            } catch (ObjectNotExistException e) {
                // ------------------------------------------
                // 翻訳対象のテキストリソースを取得
                // ------------------------------------------
                transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());
            }
            
            break;
            
            
        case ITEM:
            // TODO: 未実装
            break;
            
        default:
            // その他の場合
            throw new ArgumentException();
        }
        
//        if(objectType == ObjectType.SPOT) {
//            
//            
//            SpotLangUnit spotLangUnit = SpotLangUnitService.get(spot, transLang);
//            
//            // 無効になっている言語を復活させる
//            if(spotLangUnit != null && spotLangUnit.isInvalid()) {
//                SpotService.setInvalid(spot, transLang, false);
//                
//                return redirect("/+" + spot.getSpotId() + "/l-" + transLang.toString() + "/");
//            }
//            
//            transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());
//            
//        }else if(objectType == ObjectType.ITEM) {
//            
//            String itemId = asString("itemId");
//            if(StringUtil.isEmpty(itemId)) throw new TransException();
//            
//            Item item = ItemService.getByKey(spot, itemId, spot.getBaseLang());
//            requestScope("item", item);
//            
//            // 翻訳テキストリストの取得
//            transContentsList = ItemTextResService.getResourcesList(item, spot.getBaseLang());
//            
//        }else {
//            throw new TransException();
//        }
        
        
        if(transContentsList == null) throw new TransException();
        
        // 翻訳文字数のカウント
        for(TextRes transcontents: transContentsList) {
            transCharCount = transCharCount + transcontents.getContentString().length();
        }
        
        requestScope("objectType", objectType);
        requestScope("transCharCount", String.valueOf(transCharCount));
        
        return forward("trans.jsp");
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);

        // 翻訳言語
        v.add("transLang", 
            v.required("翻訳言語が選択されていません。")
                );
        
        // 翻訳言語
        v.add("objectType", 
            v.required("翻訳するターゲットが選択されていません。")
                );
        
        return v.validate();
    }
}
