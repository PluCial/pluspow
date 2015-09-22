package com.pluspow.controller.info;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class PriceController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("price.jsp");
    }
}
