package com.pluspow.enums;

public enum TextResRole {
    SPOT_NAME("スポット名", false, true), 
    SPOT_CATCH_COPY("スポットのキャッチコピー", false, true), 
    SPOT_DETAIL("スポットの詳細", true, true), 
    SPOT_ADDRESS("スポットの住所", false, false),
    ITEM_NAME("アイテム名", false, true),
    ITEM_DETAIL("アイテム説明", true, true);

    private String name;
    
    /**
     * 編集時にテキストフィールドを表示するか、テキストエリアを表示するかをこのパラメーターで切り替える
     */
    private boolean longText;
    
    /**
     * 翻訳対象かどうか。
     */
    private boolean transTarget;

    private TextResRole(String name, boolean longText, boolean transTarget) {
        this.name = name;
        this.longText = longText;
        this.transTarget = transTarget;
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
                || this == TextResRole.SPOT_DETAIL 
                || this == TextResRole.SPOT_ADDRESS) {
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

    public boolean isTransTarget() {
        return transTarget;
    }

    public void setTransTarget(boolean transTarget) {
        this.transTarget = transTarget;
    }
}
