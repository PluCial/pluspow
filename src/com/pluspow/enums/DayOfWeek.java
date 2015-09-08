package com.pluspow.enums;

public enum DayOfWeek {
    MON("月曜日"),
    TUE("火曜日"),
    WED("水曜日"),
    THU("木曜日"),
    FRI("金曜日"),
    SAT("土曜日"),
    SUN("日曜日");
    
    /** 名前 */
    private String name;
    
    /**
     * コンストラクタ
     * @param name
     */
    private DayOfWeek(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
