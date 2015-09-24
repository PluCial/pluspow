package com.pluspow.enums;

public enum ObjectType {
    SPOT("スポット情報"),
    ITEM("アイテム");
    
    private String name;
    
    /**
     * コンストラクタ
     * @param name
     */
    private ObjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
}
