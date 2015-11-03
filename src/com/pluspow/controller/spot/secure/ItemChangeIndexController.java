package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.MemcacheService;

public class ItemChangeIndexController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String itemId = asString("itemId");
        String prevId = asString("prevId");
        String nextId = asString("nextId");
        
        // itemId もしくは 前後のアイテムIDが両方ともNull場合はエラー
        if(StringUtil.isEmpty(itemId)
                || (StringUtil.isEmpty(prevId) && StringUtil.isEmpty(nextId))
                ) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        
        // 前後のアイテムIDが同じの場合エラー
        if(!StringUtil.isEmpty(prevId) && !StringUtil.isEmpty(nextId) && prevId.equals(nextId)) {
            requestScope("status", "NG");
            return forward("/client/ajax_response.jsp");
        }
        
        
        Item targetItem = ItemService.getItem(itemId, spot.getBaseLang());
        double order = targetItem.getSortOrder();
        
        // 前後にアイテムが存在する場合 平均値を設定
        if(!StringUtil.isEmpty(prevId) && !StringUtil.isEmpty(nextId)) {
            Item prevItem = ItemService.getItem(prevId, spot.getBaseLang());
            Item nextItem = ItemService.getItem(nextId, spot.getBaseLang());
            
            order = (prevItem.getSortOrder() + nextItem.getSortOrder()) / 2;
            
        }else if(!StringUtil.isEmpty(prevId)) {
            // 前のアイテムのみ存在する場合(一番最後に移動)
            Item prevItem = ItemService.getItem(prevId, spot.getBaseLang());
            order = prevItem.getSortOrder() + 0.00000001;
            
            
        }else if(!StringUtil.isEmpty(nextId)) {
            // 後のアイテムのみ存在する(一番最初に移動)
            Item nextItem = ItemService.getItem(nextId, spot.getBaseLang());
            order = nextItem.getSortOrder() - 0.00000001;
        }
        
        // 保存処理
        ItemService.changeSortOrder(targetItem, order);
        
        // キャッシュクリア
        MemcacheService.deleteItem(targetItem, spot.getBaseLang());
        
        
        requestScope("status", "OK");
        return forward("/client/ajax_response.jsp");
    }
}
