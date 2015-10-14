package com.pluspow.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.pluspow.enums.DayOfWeek;

@Model(schemaVersion = 1)
public class OfficeHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * 曜日
     */
    @Attribute(unindexed = true)
    private DayOfWeek dayOfWeek;
    
    /**
     * 営業フラグ
     */
    @Attribute(unindexed = true)
    private boolean open = false;
    
    /**
     * 開店時間(時)
     */
    @Attribute(unindexed = true)
    private int openHour = 10;
    
    /**
     * 開店時間(分)
     */
    @Attribute(unindexed = true)
    private int openMinute = 0;
    
    /**
     * 閉店時間(時)
     */
    @Attribute(unindexed = true)
    private int closeHour = 18;
    
    /**
     * 閉店時間(分)
     */
    @Attribute(unindexed = true)
    private int closeMinute = 0;
    
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
        OfficeHours other = (OfficeHours) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getOpenMinute() {
        return openMinute;
    }

    public void setOpenMinute(int openMinute) {
        this.openMinute = openMinute;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public int getCloseMinute() {
        return closeMinute;
    }

    public void setCloseMinute(int closeMinute) {
        this.closeMinute = closeMinute;
    }

    public ModelRef<Spot> getSpotRef() {
        return spotRef;
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
}
