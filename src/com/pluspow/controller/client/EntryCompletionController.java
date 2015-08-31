package com.pluspow.controller.client;

import org.slim3.controller.Navigation;

import com.pluspow.enums.EntryType;

public class EntryCompletionController extends BaseController {

    @Override
    protected Navigation execute() throws Exception {
        
        String type = asString("type");
        
        requestScope("entry", EntryType.valueOf(type));
        
        
        return forward("entryCompletion.jsp");
    }
}
