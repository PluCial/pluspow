package com.pluspow.exception;

public class ArgumentException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "引数が正しくありません";
    
    /**
     * コンストラクタ
     */
    public ArgumentException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public ArgumentException(String message) {
        super(message);
    }

}
