package com.pluspow.controller.client.account.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.enums.ServicePlan;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;
import com.pluspow.model.SpotPayPlan;
import com.pluspow.model.TransHistory;
import com.pluspow.service.SpotLangUnitService;
import com.pluspow.service.SpotPayPlanService;
import com.pluspow.service.TransCreditService;
import com.pluspow.service.TransHistoryService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // サポートしている言語リスト
        List<SpotLangUnit> spotLangUnitList = SpotLangUnitService.getList(spot);
        requestScope("spotLangUnitList", spotLangUnitList == null ? new ArrayList<SpotLangUnit>() : spotLangUnitList);
        
        // 貯蓄の設定
        spot.setTransAcc(TransCreditService.get(spot));
        
        // プラン
        try {
            SpotPayPlan payPlan = SpotPayPlanService.getPlan(spot);
            spot.setPlan(payPlan.getPlan());
            
        } catch (ObjectNotExistException e) {
            spot.setPlan(ServicePlan.FREE);
        }
        
        // 翻訳履歴
        List<TransHistory> transHistoryList = TransHistoryService.getHistoryList(spot);
        requestScope("transHistoryList", transHistoryList == null ? new ArrayList<TransHistory>() : transHistoryList);
        
        return forward("dashboard.jsp");
    }
}
