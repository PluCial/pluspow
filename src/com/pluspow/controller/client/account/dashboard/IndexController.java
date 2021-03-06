package com.pluspow.controller.client.account.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.TransHistory;
import com.pluspow.service.SpotLangUnitService;
import com.pluspow.service.TransCreditService;
import com.pluspow.service.TransHistoryService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 貯蓄の設定
        spot.setTransAcc(TransCreditService.get(spot));
        
        // サポートしている言語リスト
        List<SpotLangUnit> spotLangUnitList = SpotLangUnitService.getAllList(spot);
        requestScope("spotLangUnitList", spotLangUnitList == null ? new ArrayList<SpotLangUnit>() : spotLangUnitList);
        
        // 翻訳履歴
        List<TransHistory> transHistoryList = TransHistoryService.getHistoryList(spot);
        requestScope("transHistoryList", transHistoryList == null ? new ArrayList<TransHistory>() : transHistoryList);
        
        return forward("dashboard.jsp");
    }
}
