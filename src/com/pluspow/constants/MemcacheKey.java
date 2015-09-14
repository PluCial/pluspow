package com.pluspow.constants;

import com.pluspow.enums.Lang;


public class MemcacheKey {
    
    /** スポットキー */
    private static final String SPOT_INFO_MEMCACHE_KEY = "spot_info";
    
    // ------------------------------------------------------
    // スポット
    // ------------------------------------------------------
    
    /**
     * スポットキーの取得
     * @param spotId
     * @param lang
     * @return
     */
    public static String getSpotKey(String spotId, Lang lang) {
        return SPOT_INFO_MEMCACHE_KEY + "_" + spotId + "_" + lang.getLangKey();
    }

}
