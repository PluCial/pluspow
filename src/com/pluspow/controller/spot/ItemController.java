package com.pluspow.controller.spot;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.enums.SupportLang;
import com.pluspow.exception.NoContentsException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;

public class ItemController extends BaseController {

    @Override
    protected Navigation execute(
            Spot spot, 
            SupportLang lang,
            Client client,
            boolean isClientLogged,
            boolean isOwner) throws Exception {
        
        String itemId = asString("itemId");
        if(StringUtil.isEmpty(itemId)) throw new NoContentsException();
        
        // アイテムの取得
        try {
            Item item = ItemService.getByKey(spot, itemId, lang);
            if(item.getSupportLangs().indexOf(lang) < 0) throw new NoContentsException();
            requestScope("item", item);
            
            List<SupportLang> suppertLangList = item.getSupportLangs();
            requestScope("suppertLangList", suppertLangList != null ? suppertLangList : new ArrayList<SupportLang>());
            
        }catch (Exception e){
            throw new NoContentsException();
        }
        
        
        
        // アイテムリストの取得
        List<Item> itemList = ItemService.getItemList(spot, lang);
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        
        return forward("/spot/item.jsp");
    }
}
