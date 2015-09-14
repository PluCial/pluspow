package com.pluspow.controller.client.account.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TransHistory;
import com.pluspow.service.SpotLangUnitService;
import com.pluspow.service.TransHistoryService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot baseSpot) throws Exception {
        
        // サポートしている言語リスト
        List<SpotLangUnit> spotLangUnitList = SpotLangUnitService.getList(baseSpot);
        requestScope("spotLangUnitList", spotLangUnitList == null ? new ArrayList<SpotLangUnit>() : spotLangUnitList);
        
        // 翻訳履歴
        List<TransHistory> transHistoryList = TransHistoryService.getHistoryList(baseSpot);
        requestScope("transHistoryList", transHistoryList == null ? new ArrayList<TransHistory>() : transHistoryList);
        
        return forward("dashboard.jsp");
    }
}
