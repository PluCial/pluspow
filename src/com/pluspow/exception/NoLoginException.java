package com.pluspow.exception;

public class NoLoginException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "ログインしていません";
    
    /**
     * コンストラクタ
     */
    public NoLoginException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public NoLoginException(String message) {
        super(message);
    }

}
