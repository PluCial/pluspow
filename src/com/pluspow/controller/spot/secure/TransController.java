package com.pluspow.controller.spot.secure;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pluspow.enums.ResGroups;
import com.pluspow.exception.TransException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.model.TextRes;
import com.pluspow.service.ItemService;
import com.pluspow.service.ItemTextResService;
import com.pluspow.service.SpotTextResService;

public class TransController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!validate()) {
            throw new TransException();
        }
        
        int transCharCount = 0;
        
        List<? extends TextRes> transContentsList = null;
        
        ResGroups transGroup = ResGroups.valueOf(asString("transGroup"));
        
        if(transGroup == ResGroups.SPOT) {
            // 翻訳テキストリストの取得
            
            transContentsList = SpotTextResService.getResourcesList(spot, spot.getBaseLang());
            
        }else if(transGroup == ResGroups.ITEM) {
            
            String itemId = asString("itemId");
            if(StringUtil.isEmpty(itemId)) throw new TransException();
            
            Item item = ItemService.getByKey(spot, itemId, spot.getBaseLang());
            requestScope("item", item);
            
            // 翻訳テキストリストの取得
            transContentsList = ItemTextResService.getResourcesList(item, spot.getBaseLang());
            
        }else {
            throw new TransException();
        }
        
        
        if(transContentsList == null) throw new TransException();
        
        // 翻訳文字数のカウント
        for(TextRes transcontents: transContentsList) {
            transCharCount = transCharCount + transcontents.getContentString().length();
        }
        
        requestScope("transGroup", transGroup);
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
        v.add("transGroup", 
            v.required("翻訳するターゲットが選択されていません。")
                );
        
        return v.validate();
    }
}
