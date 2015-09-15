package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.TransException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;

public class TransEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!validate()) {
            throw new ArgumentException();
        }
        
        ObjectType objectType = ObjectType.valueOf(asString("objectType"));
        Lang transLang = Lang.valueOf(asString("transLang"));
        
        // 翻訳処理
        switch(objectType) {
        case SPOT:
            // ---------------------------------------------------
            // スポットの翻訳
            // ---------------------------------------------------
            SpotService.machineTrans(spot, transLang);
            
            return redirect(PathUtils.spotRelativePath(spot, transLang));



        case ITEM:
            // ---------------------------------------------------
            // アイテムの翻訳
            // --------------------------------------------------- 
            String itemId = asString("itemId");
            if(StringUtil.isEmpty(itemId)) throw new TransException();

            Item item = ItemService.getByKey(spot, itemId, spot.getBaseLang());

            ItemService.machineRealTrans(spot, item, transLang);

            return redirect(PathUtils.spotRelativePath(spot, transLang));


        default:
            // その他の場合
            throw new ArgumentException();
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
        v.add("objectType", 
            v.required("翻訳するターゲットが選択されていません。")
                );
        
        return v.validate();
    }
}
