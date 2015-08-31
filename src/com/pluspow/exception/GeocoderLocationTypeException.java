package com.pluspow.exception;

public class GeocoderLocationTypeException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "入力された住所が正しくないか、もしくは不完全な住所です。";
    
    /**
     * コンストラクタ
     */
    public GeocoderLocationTypeException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public GeocoderLocationTypeException(String message) {
        super(message);
    }

}
