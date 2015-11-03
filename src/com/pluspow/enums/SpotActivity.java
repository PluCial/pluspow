package com.pluspow.enums;

public enum SpotActivity {
    FOOD_AND_DRINK("飲食", "飲食 (料理、フード、ドリンク...)", "fa fa-cutlery"),
    LODGING("宿泊", "宿泊施設", "fa fa-bed"),
    RECREATION("娯楽", "娯楽 (美容、マッサージ、娯楽サービス...)", "fa fa-heart-o"),
    COMMUNICATION("交流", "交流 (定期イベント、交流会、体験プログラム...)", "fa fa-users"), // Experience に変更
    SHOPPING("ショッピング", "店頭販売品 (お土産、免税品...)", "fa fa-shopping-cart"),
    ETC("その他", "その他", "fa fa-ellipsis-h");
    
    /** 名前 */
    private String name;
    
    /**
     * アイテムタイプ選択での表示名
     */
    private String itemTypeName;
    
    private String iconClass;
    
    /**
     * コンストラクタ
     * @param name
     */
    private SpotActivity(String name, String itemTypeName, String iconClass) {
        this.name = name;
        this.itemTypeName = itemTypeName;
        this.iconClass = iconClass;
    }

    public String getName() {
        return name;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public String getIconClass() {
        return iconClass;
    }
}
