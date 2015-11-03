package com.pluspow.controller.spot;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.service.ItemService;
import com.pluspow.service.SpotLangUnitService;

public class ItemController extends BaseController {

    @Override
    protected Navigation execute(
            Spot spot, 
            Lang lang,
            Client client,
            boolean isClientLogged,
            boolean isOwner) throws Exception {
        
        String itemId = asString("itemId");
        if(StringUtil.isEmpty(itemId)) throw new NoContentsException();
        
        // アイテムの取得
        try {
            Item item = null;
            try {
                item = ItemService.getItem(itemId, lang);
            }catch(ObjectNotExistException e) {
                throw new NoContentsException();
            }
            requestScope("item", item);
            
            List<Lang> itemLangs = item.getLangs();
            List<Lang> suppertLangList = new ArrayList<Lang>();
            
            // スポットとItem 両方でサポートしている言語のみを抽出
            // ※スポットのプラン制限でサポート言語が増減するため。
            List<SpotLangUnit> spotLangUnitList = SpotLangUnitService.getList(spot);
            for(SpotLangUnit spotLangUnit: spotLangUnitList) {
                if(itemLangs.indexOf(spotLangUnit.getLang()) >= 0) {
                    suppertLangList.add(spotLangUnit.getLang());
                }
            }
            
            requestScope("suppertLangList", suppertLangList != null ? suppertLangList : new ArrayList<Lang>());
            
        }catch (Exception e){
            throw new NoContentsException();
        }
        
        
        
        // アイテムリストの取得
        List<Item> itemList = ItemService.getItemList(spot, lang, null);
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        
        return forward("/spot/item.jsp");
    }
}
