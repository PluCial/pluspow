package com.pluspow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PostalAddress;
import com.pluspow.enums.SpotActivity;
import com.pluspow.enums.SupportLang;

@Model(schemaVersion = 1)
public class SupportLangInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * 言語
     */
    private SupportLang lang;
    
    /**
     * アクティビティ
     */
    private List<SpotActivity> activitys = new ArrayList<SpotActivity>();
    
    /**
     * 電話番号表示フラグ
     */
    @Attribute(unindexed = true)
    private boolean phoneDisplayFlg = false;
    
    /**
     * コンタクトページ表示フラグ
     */
    @Attribute(unindexed = true)
    private boolean contactDisplayFlg = false;
    
    /** Spotとの関連 */
    private ModelRef<Spot> spotRef = new ModelRef<Spot>(Spot.class);
    
    /**
     * 作成日時
     */
    @Attribute(listener = CreationDate.class)
    private Date createDate;
    
    /**
     * 更新日時
     */
    @Attribute(listener = ModificationDate.class)
    private Date updateDate;
    
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
    private String geoFormattedAddress;
    
    /**
     * GEO lat
     */
    private float geoLat;
    
    /**
     * GEO lng
     */
    private float geoLng;
    
    /**
     * country
     */
    private String geoCountry;
    
    /**
     * administrative_area_level_1
     */
    private String geoAreaLevel1;
    
    /**
     * administrative_area_level_2
     */
    private String geoAreaLevel2;
    
    /**
     * administrative_area_level_3
     */
    private String geoAreaLevel3;
    
    /**
     * locality
     */
    private String geoLocality;
    
    /**
     * ward locality
     */
    private String geoWardLocality;
    
    /**
     * sub locality
     */
    private String geoSublocality;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SupportLangInfo other = (SupportLangInfo) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public boolean isPhoneDisplayFlg() {
        return phoneDisplayFlg;
    }

    public void setPhoneDisplayFlg(boolean phoneDisplayFlg) {
        this.phoneDisplayFlg = phoneDisplayFlg;
    }

    public boolean isContactDisplayFlg() {
        return contactDisplayFlg;
    }

    public void setContactDisplayFlg(boolean contactDisplayFlg) {
        this.contactDisplayFlg = contactDisplayFlg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getGeoFormattedAddress() {
        return geoFormattedAddress;
    }

    public void setGeoFormattedAddress(String geoFormattedAddress) {
        this.geoFormattedAddress = geoFormattedAddress;
    }

    public float getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(float geoLat) {
        this.geoLat = geoLat;
    }

    public float getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(float geoLng) {
        this.geoLng = geoLng;
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

    public ModelRef<Spot> getSpotRef() {
        return spotRef;
    }

    public SupportLang getLang() {
        return lang;
    }

    public void setLang(SupportLang lang) {
        this.lang = lang;
    }

    public List<SpotActivity> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<SpotActivity> activitys) {
        this.activitys = activitys;
    }
}
