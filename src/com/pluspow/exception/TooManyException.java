package com.pluspow.exception;

/**
 * データ重複エラー
 * @author takahara
 *
 */
public class TooManyException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "重複しています。";
    
    /**
     * コンストラクタ
     */
    public TooManyException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public TooManyException(String message) {
        super(message);
    }

}
