package com.pluspow.enums;

public enum TransStatus {
    ENTRY("翻訳開始"), TRANSLATED("翻訳完了"), FAILURE("翻訳失敗");
    
    private String name;
    
    private TransStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
