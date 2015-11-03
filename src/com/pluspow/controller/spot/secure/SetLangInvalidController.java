package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.MemcacheService;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;

public class SetLangInvalidController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        boolean invalid = asBoolean("invalid");
        Lang lang = Lang.valueOf(asString("lang"));
        ObjectType objectType = ObjectType.valueOf(asString("objectType"));
        
        switch(objectType) {
        case SPOT:
            SpotService.setInvalid(spot, lang, invalid);
            
            // キャッシュクリア(すべての言語)
            MemcacheService.deleteSpotAll(spot);
            
            // ベース言語ページにリダイレクト
            return redirect(PathUtils.spotPage(spot.getSpotId(), spot.getBaseLang(), isLocal(), true));

        case ITEM:
            // ------------------------------------------
            // アイテムの取得
            // ------------------------------------------
            String itemId = asString("itemId");
            if(StringUtil.isEmpty(itemId)) throw new NoContentsException();

            Item item = null;
            try {
                item = ItemService.getItem(itemId, spot.getBaseLang());
                requestScope("item", item);
                
            } catch (ObjectNotExistException e) {
                throw new NoContentsException();
            }
            
            ItemService.setInvalid(spot, item, lang, invalid);
            
            // キャッシュクリア(spot のアクティビティの更新があるため、spotのキャッシュもクリア)
            MemcacheService.deleteSpotAll(spot);
            MemcacheService.deleteItemAll(item);
            
            // 現在の言語ページにリダイレクト
            return redirect(PathUtils.spotPage(spot.getSpotId(), lang, isLocal(), true));
            
        default:
            // その他の場合
            throw new ArgumentException();
        }
    }
}
