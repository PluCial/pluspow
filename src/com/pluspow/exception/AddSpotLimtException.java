package com.pluspow.exception;

public class AddSpotLimtException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "作成できるスポットの上限を超えました。";
    
    /**
     * コンストラクタ
     */
    public AddSpotLimtException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public AddSpotLimtException(String message) {
        super(message);
    }

}
