package com.pluspow.controller;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.pluspow.App;
import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.search.SearchSpot;
import com.pluspow.service.SearchApiService;

public class SearchController extends BaseController {

    @Override
    protected Navigation execute(Lang lang, Client client,
            boolean isClientLogged) throws Exception {
        
        // 検索ビルダーの初期化
        SearchSpot builder = SearchApiService.newSearchBuilder(lang);

        // ---------------------------------------------------
        // 検索条件の設定
        // ---------------------------------------------------
        builder.addKeyword(asString("keyword"));
        builder.addCountry(asString("country"));
        builder.addGeoAreaLevel1(asString("areaLevel1"));
        builder.addGeoAreaLevel2(asString("areaLevel2"));
        builder.addGeoAreaLevel3(asString("areaLevel3"));
        builder.addGeoLocality(asString("locality"));
        builder.addGeoSublocality(asString("sublocality"));
        builder.addGeoWardLocality(asString("wardLocality"));
        
        // アクティビティの取得
        String[] activityArray = super.paramValues("activityArray");
        requestScope("selectedActivitys", activityArray);
        
        // すべての場合はアクティビティを条件に追加しない
        if(activityArray != null && activityArray.length < SpotActivity.values().length) {
            builder.addActivity(true, activityArray);
        }
        
        String cursor = asString("cursor");


        // リストの取得
        Results<ScoredDocument> results = builder.build(App.KEYWORD_SEARCH_SPOT_LIST_LIMIT, cursor);
        List<Spot> spotList = builder.getSpotListByResults(results);
        requestScope("spotList", spotList);
        
        // 「もっと読む」の設定
        requestScope("hasNext", String.valueOf(results.getCursor() != null));
        if(results.getCursor() != null) {
            requestScope("cursor", results.getCursor().toWebSafeString());
        }

        if(!StringUtil.isEmpty(cursor)) return forward("/include-parts/search_results.jsp");
        return forward("search.jsp");
    }
}
