package com.pluspow.controller.spot.secure;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TransException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.ItemLangUnit;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TextRes;
import com.pluspow.service.ItemLangUnitService;
import com.pluspow.service.ItemService;
import com.pluspow.service.ItemTextResService;
import com.pluspow.service.SpotLangUnitService;
import com.pluspow.service.SpotService;
import com.pluspow.service.SpotTextResService;
import com.pluspow.utils.PathUtils;

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
                SpotLangUnit unit = SpotLangUnitService.get(spot, transLang);

                if(unit.isInvalid()) {
                    SpotService.setInvalid(spot, transLang, false);
                    return redirect(PathUtils.spotPage(spot, transLang));
                }
            
            } catch (ObjectNotExistException e) {}
            
            // ------------------------------------------
            // 翻訳対象のテキストリソースを取得
            // ------------------------------------------
            transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());

            break;


        case ITEM:
            // ------------------------------------------
            // アイテムの取得
            // ------------------------------------------
            String itemId = asString("itemId");
            if(StringUtil.isEmpty(itemId)) throw new NoContentsException();

            Item item = null;
            try {
                item = ItemService.getByKey(itemId, spot.getBaseLang());
                requestScope("item", item);
                
            } catch (ObjectNotExistException e) {
                throw new NoContentsException();
            }
            
            try {
                // ------------------------------------------
                // 無効になっている言語を復活させる
                // ------------------------------------------
                ItemLangUnit unit = ItemLangUnitService.get(item, transLang);
                if(unit.isInvalid()) {
                    ItemService.setInvalid(item, transLang, false);
                    return redirect(PathUtils.spotPage(spot, transLang));
                }

            } catch (ObjectNotExistException e) {}
            
            // ------------------------------------------
            // 翻訳対象のテキストリソースを取得
            // ------------------------------------------
            transContentsList = ItemTextResService.getResourcesList(item, item.getBaseLang());

            break;

        default:
            // その他の場合
            throw new ArgumentException();
        }
        
        
        if(transContentsList == null) throw new TransException("翻訳するコンテンツがありません。");
        
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
