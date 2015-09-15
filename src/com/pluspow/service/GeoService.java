package com.pluspow.service;

import java.io.IOException;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderLocationType;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderStatus;
import com.pluspow.enums.Lang;
import com.pluspow.exception.GeocodeStatusException;
import com.pluspow.exception.GeocoderLocationTypeException;
import com.pluspow.model.GeoModel;

public class GeoService {
    
    /** geocoder */
    private static final Geocoder geocoder = new Geocoder();
    
    /**
     * 位置情報の取得
     * @param address
     * @param lang
     * @return
     * @throws GeocodeStatusException
     * @throws IOException
     * @throws GeocoderLocationTypeException 
     */
    public static GeoModel getGeoModel(
            String address, Lang lang) throws GeocodeStatusException, IOException, GeocoderLocationTypeException {
        
        // Geocodeリクエスト
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
            .setAddress(address)
            .setLanguage(lang.getLangKey())
            .getGeocoderRequest();

        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        
        // ステータスがOKではない場合エラーを生成
        if(geocoderResponse.getStatus() != GeocoderStatus.OK) {
            throw new GeocodeStatusException(geocoderResponse.getStatus());
        }
        
        // 不完全な住所の場合はエラー
        GeoModel model = new GeoModel(geocoderResponse);
        if(model.getLocationType() != GeocoderLocationType.ROOFTOP) {
            throw new GeocoderLocationTypeException();
        }
        
        return model;
    }

}
