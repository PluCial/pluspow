package com.pluspow.controller.spot;

import java.util.ArrayList;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;
import org.slim3.util.StringUtil;

import com.pluspow.enums.Lang;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;

public class ItemListNextController extends BaseController {

    @Override
    protected Navigation execute(
            Spot spot, 
            Lang lang,
            Client client,
            boolean isClientLogged,
            boolean isOwner)
            throws Exception {
        
        // 引数チェック
        String cursor = asString("cursor");
        if(StringUtil.isEmpty(cursor)) {
            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        // アイテムリストの取得
        S3QueryResultList<Item> itemList = null;
        try {
            if(isOwner && !spot.getBaseLang().equals(lang)) {
                // 編集可能で、ベース言語ではない場合は翻訳できるアイテムリストを取得
                itemList = ItemService.getEditModeItemList(spot, lang, cursor);

            }else {
                // 編集不可能、もしくはベース言語の場合は対象言語のアイテムリストを取得
                itemList = ItemService.getItemList(spot, lang, cursor);

            }
            
        }catch(ObjectNotExistException e) {

            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        requestScope("itemList", itemList);
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));
        
        return forward("itemListNext.jsp");
    }
}
