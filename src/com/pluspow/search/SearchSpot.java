package com.pluspow.search;

import java.util.ArrayList;
import java.util.List;

import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;
import com.pluspow.enums.Country;
import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Spot;
import com.pluspow.service.SpotService;

/**
 * スポットの検索
 * @author takahara
 *
 */
public class SearchSpot {
    
    private StringBuilder qstr = null;
    private Lang lang = null;
    private Index index = null;
    
    /**
     * コンストラクタ
     * @param lang
     * @throws ArgumentException
     */
    public SearchSpot(Index index, Lang lang) throws ArgumentException {
        if(lang == null) throw new ArgumentException();
        this.qstr = new StringBuilder();
        this.lang = lang;
        this.index = index;
    }
    
    /**
     * 検索結果を取得
     * @param limit
     * @param cursorString
     * @return
     */
    public Results<ScoredDocument> build(int limit, String cursorString) {

        if(StringUtil.isEmpty(qstr.toString().trim())) return null;
        
        System.out.println(qstr.toString());
        
        if (StringUtil.isEmpty(cursorString)) {
            return search(limit);
            
        }else {
            return search(limit, cursorString);
        }
    }
    
    /**
     * 検索キーワードの設定
     * @param keyword
     * @return
     */
    public SearchSpot addKeyword(String keyword) {
        
        if(StringUtil.isEmpty(keyword)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.insert(0, keyword + " AND ");
        }
        
        qstr.append(keyword);
        
        return this;
    }
    
    /**
     * アクティビティの設定
     * @param isIncluded
     * @param activityList
     * @return
     */
    public SearchSpot addActivity(boolean isIncluded, List<SpotActivity> activityList) {
        
        if(activityList == null || activityList.size() <= 0) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            if(isIncluded) {
                qstr.append(" AND ");
            }else {
                qstr.append("AND NOT");
            }
        }
        
        qstr.append(SearchField.ACTIVITYS);
        qstr.append(":(");
        for(int i=0;i<activityList.size(); i++) {
            if(i > 0) {
                qstr.append(" OR ");
            }
            qstr.append("\"");
            qstr.append(activityList.get(i).toString());
            qstr.append("\"");
        }
        qstr.append(")");
        
        return this;
    }
    
    /**
     * アクティビティの設定
     * @param isIncluded
     * @param activityArray
     * @return
     */
    public SearchSpot addActivity(boolean isIncluded, String[] activityArray) {
        
        if(activityArray == null || activityArray.length <= 0) return this;
        
        List<SpotActivity> activityList = new ArrayList<SpotActivity>();
        for(String activityString: activityArray) {
            try{
                activityList.add(SpotActivity.valueOf(activityString));
            }catch(Exception e) {}
        }
        
        return addActivity(isIncluded, activityList);
    }
    
    /**
     * クライアント キー ID
     * @param clientKeyId
     * @return
     */
    public SearchSpot addClientKeyId(String clientKeyId) {
        if(StringUtil.isEmpty(clientKeyId)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND ");
        }
        
        qstr.append(SearchField.CLIENT_KEY_ID);
        qstr.append("=\"");
        qstr.append(clientKeyId);
        qstr.append("\"");
        
        return this;
    }

    /**
     * スポットキー ID
     * @param spotKeyId
     * @return
     */
    public SearchSpot addSpotKeyId(String spotKeyId) {
        
        if(StringUtil.isEmpty(spotKeyId)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.SPOT_KEY_ID);
        qstr.append("=\"");
        qstr.append(spotKeyId);
        qstr.append("\"");
        
        return this;
    }

    /**
     * スポットID
     * @param spotId
     * @return
     */
    public SearchSpot addSpotId(String spotId) {
        if(StringUtil.isEmpty(spotId)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.SPOT_ID);
        qstr.append("=\"");
        qstr.append(spotId);
        qstr.append("\"");
        
        return this;
    }
    
    /**
     * 住所
     * @param address
     * @return
     */
    public SearchSpot addAddress(String address) {
        if(StringUtil.isEmpty(address)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.ADDRESS);
        qstr.append(":\"");
        qstr.append(address);
        qstr.append("\"");
        
        return this;
    }

    /**
     * スポット名
     * @param name
     * @return
     */
    public SearchSpot addName(String name) {
        if(StringUtil.isEmpty(name)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.NAME);
        qstr.append(":\"");
        qstr.append(name);
        qstr.append("\"");
        
        return this;
    }
    
    /**
     * キャッチコピー
     * @param catchCopy
     * @return
     */
    public SearchSpot addCatchCopy(String catchCopy) {
        if(StringUtil.isEmpty(catchCopy)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.CATCH_COPY);
        qstr.append(":\"");
        qstr.append(catchCopy);
        qstr.append("\"");
        
        return this;
    }

    /**
     * スポット詳細
     * @param detail
     * @return
     */
    public SearchSpot addDetail(String detail) {
        if(StringUtil.isEmpty(detail)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.DETAIL);
        qstr.append(":\"");
        qstr.append(detail);
        qstr.append("\"");
        
        return this;
    }

//    public SearchSpot addGeoPoint(String geoPoint) {
//        if(!StringUtil.isEmpty(qstr.toString().trim())) {
//            qstr.append(" AND "); 
//        }
//        
//        qstr.append(b);
//        qstr.append("=\"");
//        qstr.append(geoPoint);
//        qstr.append("\"");
//        
//        return this;
//    }

    /**
     * 国コード
     * @param country
     * @return
     */
    public SearchSpot addCountry(String countryCode) {
        if(StringUtil.isEmpty(countryCode)) return this;
        
        Country country = null;
        try {
            country = Country.valueOf(countryCode);
        }catch(Exception e){
            return this;
        }
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.COUNTRY);
        qstr.append("=\"");
        qstr.append(country.toString());
        qstr.append("\"");
        
        return this;
    }

    /**
     * 国名
     * @param countryName
     * @return
     */
    public SearchSpot addCountryName(String countryName){
        if(StringUtil.isEmpty(countryName)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.COUNTRY_NAME);
        qstr.append(":\"");
        qstr.append(countryName);
        qstr.append("\"");
        
        return this;
    }

    /**
     * geoAreaLevel1
     * @param geoAreaLevel1
     * @return
     */
    public SearchSpot addGeoAreaLevel1(String geoAreaLevel1) {
        if(StringUtil.isEmpty(geoAreaLevel1)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_AREA_LEVEL1);
        qstr.append(":\"");
        qstr.append(geoAreaLevel1);
        qstr.append("\"");
        
        return this;
    }

    /**
     * geoAreaLevel2
     * @param geoAreaLevel2
     * @return
     */
    public SearchSpot addGeoAreaLevel2(String geoAreaLevel2) {
        if(StringUtil.isEmpty(geoAreaLevel2)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_AREA_LEVEL2);
        qstr.append(":\"");
        qstr.append(geoAreaLevel2);
        qstr.append("\"");
        
        return this;
    }

    /**
     * addGeoAreaLevel3
     * @param geoAreaLevel3
     * @return
     */
    public SearchSpot addGeoAreaLevel3(String geoAreaLevel3) {
        if(StringUtil.isEmpty(geoAreaLevel3)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_AREA_LEVEL3);
        qstr.append(":\"");
        qstr.append(geoAreaLevel3);
        qstr.append("\"");
        
        return this;
    }

    /**
     * geoLocality
     * @param geoLocality
     * @return
     */
    public SearchSpot addGeoLocality(String geoLocality) {
        
        if(StringUtil.isEmpty(geoLocality)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_LOCALITY);
        qstr.append(":\"");
        qstr.append(geoLocality);
        qstr.append("\"");
        
        return this;
    }

    /**
     * geoWardLocality
     * @param geoWardLocality
     * @return
     */
    public SearchSpot addGeoWardLocality(String geoWardLocality) {
        
        if(StringUtil.isEmpty(geoWardLocality)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_WARD_LOCALITY);
        qstr.append(":\"");
        qstr.append(geoWardLocality);
        qstr.append("\"");
        
        return this;
    }

    /**
     * geoSublocality
     * @param geoSublocality
     * @return
     * @throws ArgumentException
     */
    public SearchSpot addGeoSublocality(String geoSublocality) {
        if(StringUtil.isEmpty(geoSublocality)) return this;
        
        if(!StringUtil.isEmpty(qstr.toString().trim())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.GEO_SUB_LOCALITY);
        qstr.append(":\"");
        qstr.append(geoSublocality);
        qstr.append("\"");
        
        return this;
    }

    /**
     * rating
     * @param rating
     * @return
     * @throws ArgumentException
     */
    public SearchSpot addRating(int rating) {
        
        if(rating < 0 || rating > 100) return this;
        
        if(!StringUtil.isEmpty(qstr.toString())) {
            qstr.append(" AND "); 
        }
        
        qstr.append(SearchField.RATING);
        qstr.append("=");
        qstr.append(rating);
        
        return this;
    }
    
    /**
     * 検索結果からスポットリストを生成
     * @param results
     * @param lang
     * @return
     */
    public List<Spot> getSpotListByResults(Results<ScoredDocument> results) {
        
        if(results == null) return new ArrayList<Spot>();

        List<Spot> spotList = new ArrayList<Spot>();
        for (ScoredDocument document : results) {
            
            String spotId = document.getOnlyField(SearchField.SPOT_ID).getAtom();
            
            if(!StringUtil.isEmpty(spotId)) {
                
                try {
                    Spot spot = SpotService.getSpot(spotId, lang);
                    spotList.add(spot);
                }catch(ObjectNotExistException e) {};
            }
        }
        
        return spotList;
    }
    
    /**
     * 検索
     * @return
     */
    private Results<ScoredDocument> search(int limit) {
        // クリエ毎のカーソルを使用
        Cursor cursor = Cursor.newBuilder().setPerResult(false).build();

        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(limit)
                    .setSortOptions(SortOptions.newBuilder()
                        .addSortExpression(SortExpression.newBuilder()
                            .setExpression(SearchField.CREATE_DATE)
                            .setDirection(SortDirection.DESCENDING)))
                            .setCursor(cursor)
                            .build())
                            .build(qstr.toString());
        Results<ScoredDocument> results = index.search(query);

        return results;
    }
    
    /**
     * 検索
     * @return
     */
    private Results<ScoredDocument> search(int limit, String cursorString) {
        Cursor cursor = Cursor.newBuilder().setPerResult(false).build(cursorString);

        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(limit)
                    .setSortOptions(SortOptions.newBuilder()
                        .addSortExpression(SortExpression.newBuilder()
                            .setExpression(SearchField.CREATE_DATE)
                            .setDirection(SortDirection.DESCENDING)))
                        .setCursor(cursor)
                        .build())
                        .build(qstr.toString());
        Results<ScoredDocument> results = index.search(query);

        return results;
    }

}
