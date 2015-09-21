package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;

public class EditItemPriceController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String itemId = asString("itemId");
        if(StringUtil.isEmpty(itemId)) {
            return null;
        }
        
        Item item = null;
        try {
            item = ItemService.getModelOnly(itemId);
            requestScope("item", item);
            
        }catch(ObjectNotExistException e) {
            return null;
        }
        
        
        return forward("editItemPrice.jsp");
    }
}
