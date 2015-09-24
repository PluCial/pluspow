package com.pluspow.enums;

public enum TextResRole {
    SPOT_NAME("スポット名", false), 
    SPOT_CATCH_COPY("スポットのキャッチコピー", false), 
    SPOT_DETAIL("スポットの詳細", true), 
    ITEM_NAME("アイテム名", false),
    ITEM_DETAIL("アイテム説明", true);

    private String name;
    
    /**
     * 編集時にテキストフィールドを表示するか、テキストエリアを表示するかをこのパラメーターで切り替える
     */
    private boolean longText;

    private TextResRole(String name, boolean longText) {
        this.name = name;
        this.longText = longText;
    }

    public String getName() {
        return name;
    }

    public boolean isLongText() {
        return longText;
    }
    
    /**
     * スポットの役割かをチェック
     * @param role
     * @return
     */
    public boolean isSpotRole() {
        if(this == TextResRole.SPOT_NAME 
                || this == TextResRole.SPOT_CATCH_COPY 
                || this == TextResRole.SPOT_DETAIL) {
            return true;
        }
        
        return false;
    }
    
    /**
     * スポットの役割かをチェック
     * @param role
     * @return
     */
    public boolean isItemRole() {
        if(this == TextResRole.ITEM_NAME 
                || this == TextResRole.ITEM_DETAIL) {
            return true;
        }
        
        return false;
    }
}
