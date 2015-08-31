package com.pluspow.controller.spot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;

import com.pluspow.enums.SupportLang;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(
            Spot spot, 
            SupportLang lang,
            Client client,
            boolean isClientLogged,
            boolean isOwner) throws Exception {
        
        List<SupportLang> suppertLangList = spot.getSupportLangs();
        requestScope("suppertLangList", suppertLangList != null ? suppertLangList : new ArrayList<SupportLang>());
        
        // 編集可能で、ベース言語ではない場合は翻訳用のベースアイテムを取得
        if(isOwner && !spot.getBaseLang().equals(lang)) {
            List<Item> itemList = ItemService.getItemList(spot, spot.getBaseLang());
            requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
            
            // 対象言語のアイテムリストのMAPを作成
            List<Item> langItemList = ItemService.getItemList(spot, lang);
            Map<String,Item> langItemMap = new HashMap<String,Item>();
            
            if(langItemList != null) {
                for (Item i : langItemList) langItemMap.put(i.getKey().getName(),i);
            }
            requestScope("langItemMap", langItemMap);
            
            
        }else {
            // 編集不可能、もしくはベース言語の場合は対象言語のアイテムリストを取得
            List<Item> itemList = ItemService.getItemList(spot, lang);
            requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        }
        
        return forward("/spot/index.jsp");
    }
}
