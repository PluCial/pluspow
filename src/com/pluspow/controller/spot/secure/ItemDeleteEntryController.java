package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.utils.PathUtils;

public class ItemDeleteEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        String itemId = asString("itemId");
        if(StringUtil.isEmpty(itemId)) throw new NoContentsException();

        try {
            Item item = ItemService.getModelOnly(itemId);
            ItemService.delete(spot, item);
            
        }catch (ObjectNotExistException e){
            throw new NoContentsException();
        }
        
        return redirect(PathUtils.spotPage(spot.getSpotId(), spot.getLangUnit().getLang(), isLocal(), true));
    }
}
