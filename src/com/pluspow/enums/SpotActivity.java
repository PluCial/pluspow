package com.pluspow.enums;

public enum SpotActivity {
    FOOD_AND_DRINK("飲食"),
    LODGING("宿泊"),
    SHOPPING("ショッピング"),
    RECREATION("娯楽"),
    ETC("その他");
    
    /** 名前 */
    private String name;
    
    /**
     * コンストラクタ
     * @param name
     * @param monthlyAmount
     * @param transCharMaxCount
     * @param createItemMaxCount
     * @param createHowtoMaxCount
     */
    private SpotActivity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
