package com.pluspow.exception;

public class PlanLimitException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "利用するプランの上限を超えました。";
    
    /**
     * コンストラクタ
     */
    public PlanLimitException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public PlanLimitException(String message) {
        super(message);
    }

}
