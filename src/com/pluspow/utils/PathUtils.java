package com.pluspow.utils;

import com.pluspow.App;
import com.pluspow.enums.Country;
import com.pluspow.enums.Lang;
import com.pluspow.enums.PlanLimitType;
import com.pluspow.model.Spot;

public class PathUtils {
    
    /**
     * スポットの相対パス
     * @param spot
     * @param lang
     * @return
     */
    private static String spotPage(String spotId, Lang lang) {
        return "/+" + spotId + "/l-" + lang.toString() + "/";
    }
    
    /**
     * スポットのパス
     * @param spotId
     * @param lang
     * @param isLocal
     * @param isClientLogged
     * @return
     */
    public static String spotPage(String spotId, Lang lang, boolean isLocal, boolean isClientLogged) {
        
        if(isLocal || isClientLogged) {
            return spotPage(spotId, lang);
        }
        
        if(lang.equals(App.APP_BASE_LANG)) {
            return  App.APP_PRODUCTION_SCHEME + App.APP_PRODUCTION_DOMAIN + "/+" + spotId  + "/";
        }
        
        return App.APP_PRODUCTION_SCHEME + lang.toString() + "." + App.APP_PRODUCTION_DOMAIN + "/+" + spotId  + "/";
    }
    
    /**
     * アイテムページの相対パス
     * @param spot
     * @param item
     * @param lang
     * @return
     */
    public static String itemPage(Spot spot, String itemId, Lang lang, boolean isLocal, boolean isClientLogged) {
        if(isLocal || isClientLogged) {
            return "/+" + spot.getSpotId() + "/l-" + lang.toString() + "/item/" + itemId;
        }
        
        if(lang.equals(App.APP_BASE_LANG)) {
            return  App.APP_PRODUCTION_SCHEME + App.APP_PRODUCTION_DOMAIN + "/+" + spot.getSpotId() + "/item/" + itemId;
        }
        
        return App.APP_PRODUCTION_SCHEME + lang.toString() + "." + App.APP_PRODUCTION_DOMAIN + "/+" + spot.getSpotId() + "/item/" + itemId;
        
        
    }
    
    /**
     * スポット選択画面パス
     * @return
     */
    public static String selectSpotPage() {
        return "/client/account/selectSpot";
    }
    
    public static String changePlanPage(Spot spot) {
        return "/spot/secure/changePlan?spotId=" + spot.getSpotId() + "&isAlert=" + false;
    }
    
    public static String changePlanPage(Spot spot, PlanLimitType limitType) {
        return "/spot/secure/changePlan?spotId=" + spot.getSpotId() + "&isAlert=" + true + "&limitType=" + limitType.toString();
    }
    
    /**
     *国旗アイコンパスを取得
     * @return
     */
    public static String getCountryFlagUrl(Country country) {
        return "/images/flag/flat/" + country.toString() + ".png";
    }
    
    /**
     * 言語から国旗アイコンパスを取得
     * @return
     */
    public static String getCountryFlagUrl(Lang lang) {
        return getCountryFlagUrl(lang.getCountry());
    }
    
    /**
     * スポットの背景画像
     * @param spot
     * @return
     */
    public static String getSpotBackgroundImageUrl(Spot spot) {
        return spot.getBackgroundImageUrl() != null ? spot.getBackgroundImageUrl() + "=s" + spot.getBackgroundImageWidth() : "/images/spot/spot_default_background_image.jpg";
    }

}
