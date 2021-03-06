package com.pluspow.controller.spot;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.datastore.S3QueryResultList;

import com.pluspow.enums.Lang;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.OfficeHoursService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(
            Spot spot, 
            Lang lang,
            Client client,
            boolean isClientLogged,
            boolean isOwner) throws Exception {
        
        // 営業時間マップ
        requestScope("officeHoursMap", OfficeHoursService.getOfficeHoursMap(spot.getOfficeHourList()));
        
        List<Lang> suppertLangList = spot.getLangs();
        requestScope("suppertLangList", suppertLangList != null ? suppertLangList : new ArrayList<Lang>());
        
        
        
        S3QueryResultList<Item> itemList = null;
        try {
            if(isOwner && !spot.getBaseLang().equals(lang)) {
                // 編集可能で、ベース言語ではない場合は翻訳できるアイテムリストを取得
                itemList = ItemService.getEditModeItemList(spot, lang, null);

            }else {
                // 編集不可能、もしくはベース言語の場合は対象言語のアイテムリストを取得
                itemList = ItemService.getItemList(spot, lang, null);

            }
        }catch(ObjectNotExistException e) {

            requestScope("itemList", new ArrayList<Item>());
            return forward("itemListNext.jsp");
        }
        
        requestScope("itemList", itemList != null ? itemList : new ArrayList<Item>());
        requestScope("cursor", itemList.getEncodedCursor());
        requestScope("hasNext", String.valueOf(itemList.hasNext()));
        
        return forward("/spot/index.jsp");
    }
}
