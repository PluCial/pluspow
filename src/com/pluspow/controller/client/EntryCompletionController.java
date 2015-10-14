package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.EntryType;
import com.pluspow.enums.Lang;

public class EntryCompletionController extends BaseController {

    @Override
    protected Navigation execute(Lang lang) throws Exception {
        
        String type = asString("type");
        
        requestScope("entry", EntryType.valueOf(type));
        
        
        return forward("entryCompletion.jsp");
    }
}
