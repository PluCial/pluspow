package com.pluspow.search;

import java.util.ArrayList;
import java.util.List;

import org.slim3.util.StringUtil;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;
import com.pluspow.enums.Country;
import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;

public class SearchApi {
    
    private static final String SPOT_DOCUMENT_INDEX = "spot_index";
    
    /**
     * ドキュメントインデックスの取得
     * @param lang
     * @return
     */
    private static Index getDocumentIndex(Country country, Lang lang) {
        return SearchServiceFactory.getSearchService()
                .getIndex(IndexSpec.newBuilder()
                    .setName(SPOT_DOCUMENT_INDEX  + "_" + country.toString() + "_" + lang.toString()));
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
        
        Index index = getDocumentIndex(spot.getCountry(), lang);
        
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
        Index index = getDocumentIndex(spot.getCountry(), lang);
        
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
//        SpotLangUnit langUnit = SpotLangUnitService.get(spot, lang);
//        spot.setTextResources(SpotTextResService.getResourcesMap(spot, lang));
//        Double maxPrice = ItemService.getItemMaxPrice(spot, lang);
        
        SpotLangUnit langUnit = spot.getLangUnit();
        Double maxPrice = spot.getMaxPrice();
        
        // ドキュメントの作成
        Builder builder = Document.newBuilder()
                .setId(getDocumentId(spot, langUnit.getLang()))
                .addField(Field.newBuilder()
                    .setName(SearchField.CLIENT_KEY_ID)
                    .setAtom(String.valueOf(client.getKey().getId())))
                .addField(Field.newBuilder()
                    .setName(SearchField.SPOT_KEY_ID)
                    .setAtom(String.valueOf(spot.getKey().getId())))
                .addField(Field.newBuilder()
                    .setName(SearchField.SPOT_ID)
                    .setAtom(spot.getSpotId()))
                .addField(Field.newBuilder()
                    .setName(SearchField.ADDRESS)
                    .setText(langUnit.getDisplayAddress()))
                .addField(Field.newBuilder()
                    .setName(SearchField.NAME)
                    .setText(spot.getName()))
                .addField(Field.newBuilder()
                    .setName(SearchField.CATCH_COPY)
                    .setText(spot.getCatchCopy()))
                .addField(Field.newBuilder()
                     .setName(SearchField.DETAIL)
                     .setText(spot.getDetail()))
                .addField(Field.newBuilder()
                    .setName(SearchField.ADMISSION_FRR)
                    .setNumber(spot.getAdmissionFee()))
                 .addField(Field.newBuilder()
                    .setName(SearchField.MAX_PRICE)
                    .setNumber(maxPrice));
        
                // ---------------------------------------------------
                // 位置情報
                // ---------------------------------------------------
                builder.addField(Field.newBuilder()
                     .setName(SearchField.GEO_POINT)
                     .setGeoPoint(new GeoPoint(spot.getLat(), spot.getLng())))
                .addField(Field.newBuilder()
                     .setName(SearchField.COUNTRY)
                     .setAtom(spot.getCountry().toString()))
                .addField(Field.newBuilder()
                     .setName(SearchField.COUNTRY_NAME)
                     .setText(langUnit.getGeoCountry()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_AREA_LEVEL1)
                     .setText(langUnit.getGeoAreaLevel1()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_AREA_LEVEL2)
                     .setText(langUnit.getGeoAreaLevel2()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_AREA_LEVEL3)
                     .setText(langUnit.getGeoAreaLevel3()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_LOCALITY)
                     .setText(langUnit.getGeoLocality()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_WARD_LOCALITY)
                     .setText(langUnit.getGeoWardLocality()))
                .addField(Field.newBuilder()
                     .setName(SearchField.GEO_SUB_LOCALITY)
                     .setText(langUnit.getGeoSublocality()));
                
                // ---------------------------------------------------
                // アクティビティ
                // ---------------------------------------------------
                builder.addField(Field.newBuilder()
                    .setName(SearchField.ACTIVITYS)
                    .setText(activitysToString(langUnit.getActivitys())));
                    
                // ---------------------------------------------------
                // その他
                // ---------------------------------------------------
                // 評価(今は対応しないが、今後ユーザーによる評価機能を提供する時に利用)
                builder.addField(Field.newBuilder()
                    .setName(SearchField.RATING)
                    .setNumber(0));
                
                builder.addField(Field.newBuilder()
                    .setName(SearchField.CREATE_DATE)
                    .setAtom(String.valueOf(langUnit.getCreateDate().getTime())));
        
        Document document = builder.build();
        
        // ドキュメントの保存
        Index index = getDocumentIndex(spot.getCountry(), langUnit.getLang());
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

        String activitys = document.getOnlyField(SearchField.ACTIVITYS).getText().replaceAll("^\\[", "").replaceAll("\\]$", "");

        return getSpotActivity(activitys);
    }
    
    /**
     * Search Builder を取得
     * @param lang
     * @return
     * @throws ArgumentException
     */
    public static SearchSpot newSearchBuilder(Country country, Lang lang) throws ArgumentException {
        Index index = getDocumentIndex(country, lang);
        
        return new SearchSpot(index, lang);
    }

}
