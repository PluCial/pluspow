package com.pluspow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;
import com.pluspow.App;
import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;

public class SearchApiService {
    
    private static final String SPOT_DOCUMENT_INDEX = "spot_document_index";
    
    /**
     * ドキュメントインデックスの取得
     * @param lang
     * @return
     */
    private static Index getDocumentIndex(Lang lang) {
        return SearchServiceFactory.getSearchService()
                .getIndex(IndexSpec.newBuilder()
                    .setName(SPOT_DOCUMENT_INDEX + "_" + lang.toString()));
    }
    
    /**
     * ドキュメントのキーを取得
     * @param spot
     * @return
     */
    private static String getDocumentId(Spot spot, Lang lang) {
        return String.valueOf(spot.getKey().getId() + "_" + lang.toString());
    }
    
    /**
     * documentId からドキュメントを取得
     * @param spot
     * @param lang
     * @return
     */
    private static Document getDocumentById(Spot spot, Lang lang) {
        String documentId = getDocumentId(spot, lang);
        
        Index index = getDocumentIndex(lang);
        
        return index.get(documentId);
    }
    
    /**
     * アイテムのドキュメントを削除
     * @param spot
     * @param lang
     */
    public static void deleteSpot(Spot spot, Lang lang) {
        Document document = getDocumentById(spot, lang);
        if(document == null) return;
        Index index = getDocumentIndex(lang);
        
        index.delete(document.getId());
    }
    
    /**
     * ドキュメントの追加・更新
     * @param client
     * @param spot
     * @param langUnit
     * @throws ObjectNotExistException 
     */
    public static void putDocument(Spot spot, Lang lang) throws ObjectNotExistException {
        
        Client client = spot.getClientRef().getModel();
        SpotLangUnit langUnit = SpotLangUnitService.get(spot, lang);
        spot.setTextResources(SpotTextResService.getResourcesMap(spot, lang));
        
        // ドキュメントの作成
        Builder builder = Document.newBuilder()
                .setId(getDocumentId(spot, langUnit.getLang()))
                .addField(Field.newBuilder()
                    .setName("clientKeyId")
                    .setAtom(String.valueOf(client.getKey().getId())))
                .addField(Field.newBuilder()
                    .setName("spotKeyId")
                    .setAtom(String.valueOf(spot.getKey().getId())))
                .addField(Field.newBuilder()
                    .setName("spotId")
                    .setAtom(spot.getSpotId()))
                .addField(Field.newBuilder()
                    .setName("address")
                    .setText(langUnit.getDisplayAddress()))
                .addField(Field.newBuilder()
                    .setName("name")
                    .setText(spot.getName()))
                .addField(Field.newBuilder()
                    .setName("catchCopy")
                    .setText(spot.getCatchCopy()))
                .addField(Field.newBuilder()
                     .setName("detail")
                     .setText(spot.getDetail()));
        
                // ---------------------------------------------------
                // 位置情報
                // ---------------------------------------------------
                builder.addField(Field.newBuilder()
                     .setName("geoPoint")
                     .setGeoPoint(new GeoPoint(spot.getLat(), spot.getLng())))
                .addField(Field.newBuilder()
                     .setName("country")
                     .setAtom(spot.getCountry().toString()))
                .addField(Field.newBuilder()
                     .setName("countryName")
                     .setText(langUnit.getGeoCountry()))
                .addField(Field.newBuilder()
                     .setName("geoAreaLevel1")
                     .setText(langUnit.getGeoAreaLevel1()))
                .addField(Field.newBuilder()
                     .setName("geoAreaLevel2")
                     .setText(langUnit.getGeoAreaLevel2()))
                .addField(Field.newBuilder()
                     .setName("geoAreaLevel3")
                     .setText(langUnit.getGeoAreaLevel3()))
                .addField(Field.newBuilder()
                     .setName("geoLocality")
                     .setText(langUnit.getGeoLocality()))
                .addField(Field.newBuilder()
                     .setName("geoWardLocality")
                     .setText(langUnit.getGeoWardLocality()))
                .addField(Field.newBuilder()
                     .setName("geoSublocality")
                     .setText(langUnit.getGeoSublocality()));
                
                // ---------------------------------------------------
                // アクティビティ
                // ---------------------------------------------------
                builder.addField(Field.newBuilder()
                    .setName("activitys")
                    .setText(activitysToString(langUnit.getActivitys())));
                    
                // ---------------------------------------------------
                // その他
                // ---------------------------------------------------
                // 評価(今は対応しないが、今後ユーザーによる評価機能を提供する時に利用)
                builder.addField(Field.newBuilder()
                    .setName("rating")
                    .setNumber(0));
                
                builder.addField(Field.newBuilder()
                    .setName("createDate")
                    .setAtom(String.valueOf(new Date().getTime())));
        
        Document document = builder.build();
        
        // ドキュメントの保存
        Index index = getDocumentIndex(langUnit.getLang());
        index.put(document);
    }
    
    /**
     * アクティビティリストをドキュメント用Stringに変換
     * @param activityList
     * @return
     */
    private static String activitysToString(List<SpotActivity> activityList) {
        if(activityList == null || activityList.size() <= 0) {
            return "[]";
        }
        
        String activitysString = "";
        for(SpotActivity activity: activityList) {
            activitysString = activitysString + activity.toString() + ",";
        }
        
        return "[" + activitysString.substring(0, activitysString.length() - 1) + "]";
    }
    
    /**
     * アクティビティの取得
     * @param activitys
     * @return
     */
    public static List<SpotActivity> getSpotActivity(String activitys) {

        if(StringUtil.isEmpty(activitys)) return new ArrayList<SpotActivity>();

        List<SpotActivity> activityList = new ArrayList<SpotActivity>();
        String[] activityArray = activitys.split(",");
        for(String activityString: activityArray) {
            if(!StringUtil.isEmpty(activityString)) {

                try {
                    SpotActivity activity = SpotActivity.valueOf(activityString.trim());
                    activityList.add(activity);
                }catch(Exception e) {
                    // エラーは無視
                }
            }
        }

        return activityList;
    }
    
    /**
     * アクティビティの取得
     * @param spot
     * @param lang
     * @return
     */
    public static List<SpotActivity> getSpotActivity(Spot spot, Lang lang) {
        Document document = getDocumentById(spot, lang);

        String activitys = document.getOnlyField("activitys").getText().replaceAll("^\\[", "").replaceAll("\\]$", "");

        return getSpotActivity(activitys);
    }
    
    /**
     * 検索結果からスポットリストを生成
     * @param results
     * @param lang
     * @return
     */
    public static List<Spot> getItemListByResults(Results<ScoredDocument> results, Lang lang) {

        List<Spot> spotList = new ArrayList<Spot>();
        for (ScoredDocument document : results) {
            
            String spotId = document.getOnlyField("spotId").getAtom();
            
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
     * 全件検索検索
     * @param userModel
     * @param content
     */
    private static Results<ScoredDocument> searchByKeyword(Lang lang, String qstrString) throws Exception {
        
        if(StringUtil.isEmpty(qstrString)) throw new NullPointerException();
        
        String qstr = qstrString;
        
        // クリエ毎のカーソルを使用
        Cursor cursor = Cursor.newBuilder().setPerResult(false).build();
        
        Index index = getDocumentIndex(lang);
        
        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(App.KEYWORD_SEARCH_ITEM_LIST_LIMIT)
                    .setSortOptions(SortOptions.newBuilder()
                    .addSortExpression(SortExpression.newBuilder()
                        .setExpression("createDate")
                        .setDirection(SortDirection.DESCENDING)))
                    .setCursor(cursor)
                    .build())
                    .build(qstr);
        Results<ScoredDocument> results = index.search(query);
        
        return results;
        
    }
    
    /**
     * キーワード検索
     * @param qstrString
     * @param cursorString
     * @return
     * @throws Exception
     */
    public static Results<ScoredDocument> searchByKeyword(Lang lang, String qstrString, String cursorString) throws Exception {
        if (StringUtil.isEmpty(cursorString)) return searchByKeyword(lang, qstrString);

        Cursor cursor = Cursor.newBuilder().setPerResult(false).build(cursorString);
        
        String qstr = qstrString;

        Index index = getDocumentIndex(lang);

        Query query = Query.newBuilder()
                .setOptions(QueryOptions
                    .newBuilder()
                    .setLimit(App.KEYWORD_SEARCH_ITEM_LIST_LIMIT)
                    .setSortOptions(SortOptions.newBuilder()
                        .addSortExpression(SortExpression.newBuilder()
                            .setExpression("createDate")
                            .setDirection(SortDirection.DESCENDING)))
                        .setCursor(cursor)
                        .build())
                        .build(qstr);
        Results<ScoredDocument> results = index.search(query);

        return results;
    }

}
