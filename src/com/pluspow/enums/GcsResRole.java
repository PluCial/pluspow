package com.pluspow.enums;

public enum GcsResRole {
    SPOT_ICON_IMAGE("アイコン画像"),
    SPOT_BACKGROUND_IMAGE("背景画像"),
    ITEM_IMAGE("アイテム画像");
    
    /** 名前 */
    private String name;
    
    /**
     * コンストラクタ
     * @param name
     */
    private GcsResRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public boolean isSpotRole() {
        if(this == SPOT_ICON_IMAGE || this == SPOT_BACKGROUND_IMAGE) {
            return true;
        }
        
        return false;
    }
    
    public boolean isItemRole() {
        if(this == ITEM_IMAGE) {
            return true;
        }
        
        return false;
    }
}
