package com.pluspow.exception;

public class UnsuitableException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "不適切なデータです。";
    
    /**
     * コンストラクタ
     */
    public UnsuitableException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public UnsuitableException(String message) {
        super(message);
    }

}
