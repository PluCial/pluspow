package com.pluspow.enums;

public enum SpotActivity {
    FOOD_AND_DRINK("飲食", "飲食 (料理、フード、ドリンク...)"),
    LODGING("宿泊", "宿泊施設"),
    SHOPPING("ショッピング", "店頭販売品 (お土産、免税品...)"),
    RECREATION("娯楽", "娯楽 (美容、マッサージ、娯楽サービス...)"),
    COMMUNICATION("交流", "交流 (定期イベント、交流会、体験プログラム...)"),
    ETC("その他", "その他");
    
    /** 名前 */
    private String name;
    
    /**
     * アイテムタイプ選択での表示名
     */
    private String itemTypeName;
    
    /**
     * コンストラクタ
     * @param name
     */
    private SpotActivity(String name, String itemTypeName) {
        this.name = name;
        this.itemTypeName = itemTypeName;
    }

    public String getName() {
        return name;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }
}
