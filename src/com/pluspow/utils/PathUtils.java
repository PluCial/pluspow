package com.pluspow.utils;

import com.pluspow.App;
import com.pluspow.enums.Country;
import com.pluspow.enums.Lang;
import com.pluspow.enums.PlanLimitType;
import com.pluspow.model.Item;
import com.pluspow.model.Spot;

public class PathUtils {
    
    /**
     * スポットの相対パス
     * @param spot
     * @param lang
     * @return
     */
    public static String spotPage(Spot spot, Lang lang) {
        return "/+" + spot.getSpotId() + "/l-" + lang.toString() + "/";
    }
    
    /**
     * スポットの相対パス
     * @param spot
     * @param lang
     * @return
     */
    public static String spotPage(String spotId, Lang lang) {
        return "/+" + spotId + "/l-" + lang.toString() + "/";
    }
    
    /**
     * 言語込みサブドメインのURL
     * @param scheme
     * @param domein
     * @param spotId
     * @param lang
     * @return
     */
    public static String spotPageLangSubDomainUrl(String scheme, String spotId, Lang lang) {
        return scheme + "://" + lang.toString() + "." + App.APP_PRODUCTION_DOMAIN + "/+" + spotId  + "/";
    }
    
    /**
     * アイテムページの相対パス
     * @param spot
     * @param item
     * @param lang
     * @return
     */
    public static String itemPage(Spot spot, Item item, Lang lang) {
        return "/+" + spot.getSpotId() + "/l-" + lang.toString() + "/item/" + item.getKey().getName();
    }
    
    /**
     * アイテムページの相対パス
     * @param spot
     * @param item
     * @param lang
     * @return
     */
    public static String itemPage(Spot spot, String itemId, Lang lang) {
        return "/+" + spot.getSpotId() + "/l-" + lang.toString() + "/item/" + itemId;
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
