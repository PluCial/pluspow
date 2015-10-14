package com.pluspow.exception;

public class SearchApiException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "Google Document Search Api error!";
    
    /**
     * コンストラクタ
     */
    public SearchApiException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public SearchApiException(String message) {
        super(message);
    }

}
