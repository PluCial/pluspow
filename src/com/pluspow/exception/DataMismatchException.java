package com.pluspow.exception;

public class DataMismatchException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "データー不整合エラー";
    
    /**
     * コンストラクタ
     */
    public DataMismatchException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public DataMismatchException(String message) {
        super(message);
    }

}
