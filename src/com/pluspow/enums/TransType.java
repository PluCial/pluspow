package com.pluspow.enums;

public enum TransType {
    MACHINE("機械翻訳", 0.0),
    HUMAN_STANDARD("スタンダード", 5.0),
    HUMAN_BUSINESS("ビジネス", 9.0),
    HUMAN_ULTRA("ウルトラ", 12.0);
    
    private String name;
    private double price;
    
    private TransType(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
