package com.pluspow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.pluspow.enums.Country;
import com.pluspow.enums.SpotActivity;

@Model(schemaVersion = 1)
public class SpotLangUnit extends LangUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 表示用住所
     * <pre>
     * Geo情報を元に自動登録し、各言語ページからユーザーによる修正を可能とする
     * </pre>
     */
    @Attribute(unindexed = true)
    private String displayAddress;
    
    /**
     * 電話先の国
     */
    @Attribute(unindexed = true)
    private Country phoneCountry;
    
    /**
     * 電話番号
     */
    @Attribute(unindexed = true)
    private PhoneNumber phoneNumber;
    
    /**
     * アクティビティ
     */
    @Attribute(unindexed = true)
    private List<SpotActivity> activitys = new ArrayList<SpotActivity>();
    
    /**
     * 電話番号表示フラグ
     */
    @Attribute(unindexed = true)
    private boolean phoneDisplayFlg = false;
    
    // ----------------------------------------------------------------------
    // GEO情報
    // ----------------------------------------------------------------------
    /**
     * 郵便番号
     */
    @Attribute(unindexed = true)
    private PostalAddress geoPostalAddress;
    
    /**
     * GEO formatted_address
     */
    @Attribute(unindexed = true)
    private String geoFormattedAddress;

    
    /**
     * country
     */
    @Attribute(unindexed = true)
    private String geoCountry;
    
    /**
     * administrative_area_level_1
     */
    @Attribute(unindexed = true)
    private String geoAreaLevel1;
    
    /**
     * administrative_area_level_2
     */
    @Attribute(unindexed = true)
    private String geoAreaLevel2;
    
    /**
     * administrative_area_level_3
     */
    @Attribute(unindexed = true)
    private String geoAreaLevel3;
    
    /**
     * locality
     */
    @Attribute(unindexed = true)
    private String geoLocality;
    
    /**
     * ward locality
     */
    @Attribute(unindexed = true)
    private String geoWardLocality;
    
    /**
     * sub locality
     */
    @Attribute(unindexed = true)
    private String geoSublocality;

    public boolean isPhoneDisplayFlg() {
        return phoneDisplayFlg;
    }

    public void setPhoneDisplayFlg(boolean phoneDisplayFlg) {
        this.phoneDisplayFlg = phoneDisplayFlg;
    }

    public String getGeoFormattedAddress() {
        return geoFormattedAddress;
    }

    public void setGeoFormattedAddress(String geoFormattedAddress) {
        this.geoFormattedAddress = geoFormattedAddress;
    }

    public String getGeoCountry() {
        return geoCountry;
    }

    public void setGeoCountry(String geoCountry) {
        this.geoCountry = geoCountry;
    }

    public String getGeoAreaLevel1() {
        return geoAreaLevel1;
    }

    public void setGeoAreaLevel1(String geoAreaLevel1) {
        this.geoAreaLevel1 = geoAreaLevel1;
    }

    public String getGeoAreaLevel2() {
        return geoAreaLevel2;
    }

    public void setGeoAreaLevel2(String geoAreaLevel2) {
        this.geoAreaLevel2 = geoAreaLevel2;
    }

    public String getGeoAreaLevel3() {
        return geoAreaLevel3;
    }

    public void setGeoAreaLevel3(String geoAreaLevel3) {
        this.geoAreaLevel3 = geoAreaLevel3;
    }

    public String getGeoLocality() {
        return geoLocality;
    }

    public void setGeoLocality(String geoLocality) {
        this.geoLocality = geoLocality;
    }

    public String getGeoWardLocality() {
        return geoWardLocality;
    }

    public void setGeoWardLocality(String geoWardLocality) {
        this.geoWardLocality = geoWardLocality;
    }

    public String getGeoSublocality() {
        return geoSublocality;
    }

    public void setGeoSublocality(String geoSublocality) {
        this.geoSublocality = geoSublocality;
    }

    public PostalAddress getGeoPostalAddress() {
        return geoPostalAddress;
    }

    public void setGeoPostalAddress(PostalAddress geoPostalAddress) {
        this.geoPostalAddress = geoPostalAddress;
    }

    public List<SpotActivity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<SpotActivity> activitys) {
        this.activitys = activitys;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public Country getPhoneCountry() {
        return phoneCountry;
    }

    public void setPhoneCountry(Country phoneCountry) {
        this.phoneCountry = phoneCountry;
    }
}
