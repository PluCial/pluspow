package com.pluspow.exception;

import com.google.code.geocoder.model.GeocoderStatus;

public class GeocodeStatusException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "Geocode API Status エラー";
    private GeocoderStatus status;
    
    /**
     * コンストラクタ
     */
    public GeocodeStatusException(GeocoderStatus status) {
        super(message);
        
        this.setStatus(status);
    }

    public GeocoderStatus getStatus() {
        return status;
    }

    public void setStatus(GeocoderStatus status) {
        this.status = status;
    }

}
