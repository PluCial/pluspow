package com.pluspow.utils;

import com.pluspow.enums.Lang;
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
    
    public static String changePlanPage(Spot spot, boolean isAlert) {
        return "/spot/secure/changePlan?spotId=" + spot.getSpotId() + "&isAlert=" + isAlert;
    }

}
