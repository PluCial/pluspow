package com.pluspow.exception;

import com.pluspow.enums.PlanLimitType;

public class PlanLimitException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "利用するプランの上限を超えました。";
    
    private PlanLimitType limittype;
    
    /**
     * コンストラクタ
     */
    public PlanLimitException(PlanLimitType limittype) {
        super(message);
        this.limittype = limittype;
    }
    
    /**
     * コンストラクタ
     */
    public PlanLimitException(PlanLimitType limittype, String message) {
        super(message);
        this.limittype = limittype;
    }

    public PlanLimitType getLimittype() {
        return limittype;
    }

    public void setLimittype(PlanLimitType limittype) {
        this.limittype = limittype;
    }

}
