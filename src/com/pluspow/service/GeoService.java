package com.pluspow.service;

import java.io.IOException;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.pluspow.enums.SupportLang;
import com.pluspow.exception.GeocodeStatusException;
import com.pluspow.exception.GeocoderLocationTypeException;
import com.pluspow.model.GeoModel;

public class GeoService {
    
    /** geocoder */
    private static final Geocoder geocoder = new Geocoder();
    
    /**
     * 位置情報の取得
     * @param prefecture
     * @param city
     * @param address
     * @return
     * @throws IOException
     * @throws GeocodeStatusException
     * @throws GeocoderLocationTypeException
     */
    public static GeoModel getGeoModel(
            String address, SupportLang lang) throws IOException, GeocodeStatusException, GeocoderLocationTypeException {
        
        // Geocodeリクエスト
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
            .setAddress(address)
            .setLanguage(lang.getLangKey())
            .getGeocoderRequest();

        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        
        return new GeoModel(geocoderResponse);
    }

}
