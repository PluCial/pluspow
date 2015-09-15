package com.pluspow.utils;

import com.pluspow.enums.Lang;
import com.pluspow.model.Spot;

public class PathUtils {
    
    /**
     * スポットの相対パス
     * @param spot
     * @param lang
     * @return
     */
    public static String spotRelativePath(Spot spot, Lang lang) {
        return "/+" + spot.getSpotId() + "/l-" + lang.toString() + "/";
    }

}
