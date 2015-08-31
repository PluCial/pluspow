package com.pluspow.enums;

public enum TextResRole {
    SPOT_NAME("スポット名", false), 
    SPOT_CATCH_COPY("スポットのキャッチコピー", false), 
    SPOT_DETAIL("スポットの詳細", true), 
    SPOT_ADDRESS("スポットの住所", false),
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
    public static boolean isSpotRole(TextResRole resRole) {
        if(resRole == TextResRole.SPOT_NAME 
                || resRole == TextResRole.SPOT_CATCH_COPY 
                || resRole == TextResRole.SPOT_DETAIL 
                || resRole == TextResRole.SPOT_ADDRESS) {
            return true;
        }
        
        return false;
    }
    
    /**
     * スポットの役割かをチェック
     * @param role
     * @return
     */
    public static boolean isItemRole(TextResRole resRole) {
        if(resRole == TextResRole.ITEM_NAME 
                || resRole == TextResRole.ITEM_DETAIL) {
            return true;
        }
        
        return false;
    }
}
