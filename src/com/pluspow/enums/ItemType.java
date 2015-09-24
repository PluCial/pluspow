package com.pluspow.enums;

/**
 * サービスプラン
 * @author takahara
 */
public enum ItemType {
    FOOD("料理", SpotActivity.FOOD_AND_DRINK),
    DRINK("ドリンク(お酒)", SpotActivity.FOOD_AND_DRINK),
    LODGING_FACILITY("宿泊施設", SpotActivity.LODGING),
    GOODS("店頭販売品", SpotActivity.SHOPPING),
    RECREATION_SERVICE("娯楽サービス", SpotActivity.RECREATION),
    ETC("その他", SpotActivity.ETC);
    
    /** 名前 */
    private String name;
    
    private SpotActivity activity;
    
    /**
     * コンストラクタ
     * @param name
     * @param activity
     */
    private ItemType(String name, SpotActivity activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public SpotActivity getActivity() {
        return activity;
    }
}
