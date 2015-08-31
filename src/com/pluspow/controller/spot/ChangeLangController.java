package com.pluspow.controller.spot;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ChangeLangController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        String lang = asString("lang");
        String spotId = asString("spotId");
        
        return redirect("/+" + spotId + "/l-" + lang + "/");
    }
}
