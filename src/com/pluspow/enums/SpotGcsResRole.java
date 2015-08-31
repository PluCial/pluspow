package com.pluspow.enums;

public enum SpotGcsResRole {
    SPOT_ICON_IMAGE("アイコン画像"),
    SPOT_BACKGROUND_IMAGE("背景画像");
    
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
    private SpotGcsResRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
