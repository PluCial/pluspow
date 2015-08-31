package com.pluspow.controller.client.account.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Navigation;

import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SupportLangInfo;
import com.pluspow.model.TransHistory;
import com.pluspow.service.SupportLangInfoService;
import com.pluspow.service.TransHistoryService;

public class IndexController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot baseSpot) throws Exception {
        
        // サポートしている言語リスト
        List<SupportLangInfo> supportLangInfoList = SupportLangInfoService.getList(baseSpot);
        requestScope("supportLangInfoList", supportLangInfoList == null ? new ArrayList<SupportLangInfo>() : supportLangInfoList);
        
        // 翻訳履歴
        List<TransHistory> transHistoryList = TransHistoryService.getHistoryList(baseSpot);
        requestScope("transHistoryList", transHistoryList == null ? new ArrayList<TransHistory>() : transHistoryList);
        
        return forward("dashboard.jsp");
    }
}
