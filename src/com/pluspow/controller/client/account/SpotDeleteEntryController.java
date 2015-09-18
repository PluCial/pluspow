package com.pluspow.controller.client.account;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.pluspow.exception.NoContentsException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;
import com.pluspow.utils.PathUtils;

public class SpotDeleteEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client) throws Exception {
        
        String spotId = asString("spotId");
        if(StringUtil.isEmpty(spotId)) throw new NoContentsException();

        try {
            Spot spot = SpotService.getSpotModelOnly(spotId);
            SpotService.delete(spot);
            
        }catch (ObjectNotExistException e){
            throw new NoContentsException();
        }
        
        
        return redirect(PathUtils.selectSpotPage());
    }
}
