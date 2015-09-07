package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pluspow.enums.SupportLang;
import com.pluspow.enums.ResGroups;
import com.pluspow.exception.TransException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.SpotService;

public class TransEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!validate()) {
            throw new TransException();
        }
        
        ResGroups targetGroup = ResGroups.valueOf(asString("transGroup"));
        SupportLang transLang = SupportLang.valueOf(asString("transLang"));
        
        // ---------------------------------------------------
        // スポットの翻訳
        // ---------------------------------------------------
        if(targetGroup == ResGroups.SPOT) {
            SpotService.machineRealTrans(spot, transLang);
            
            return redirect("/+" + spot.getSpotId() + "/l-" + transLang.toString() + "/");
            
            
        // ---------------------------------------------------
        // アイテムの翻訳
        // ---------------------------------------------------
        }else if(targetGroup == ResGroups.ITEM) {
            
            String itemId = asString("itemId");
            if(StringUtil.isEmpty(itemId)) throw new TransException();
            
            Item item = ItemService.getByKey(spot, itemId, spot.getBaseLang());
            
            ItemService.machineRealTrans(spot, item, transLang);
            
            return redirect("/+" + spot.getSpotId() + "/l-" + transLang.toString() + "/");
            
            
        // ---------------------------------------------------
        // その他
        // ---------------------------------------------------   
        }else {
            throw new TransException();
        }
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