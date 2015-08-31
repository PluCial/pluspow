package com.pluspow.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class TransCredit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /** 翻訳文字数 */
    @Attribute(unindexed = true)
    private int transCharCount = 0;
    
    /** 翻訳コスト */
    @Attribute(unindexed = true)
    private double transCost = 0;
    
    /**
     * 作成日時
     */
    @Attribute(listener = CreationDate.class, unindexed = true)
    private Date createDate;
    
    /**
     * 更新日時
     */
    @Attribute(listener = ModificationDate.class, unindexed = true)
    private Date updateDate;
    
    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    /** Spotとの関連 */
    private ModelRef<Spot> spotRef = new ModelRef<Spot>(Spot.class);

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
        TransCredit other = (TransCredit) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public int getTransCharCount() {
        return transCharCount;
    }

    public void setTransCharCount(int transCharCount) {
        this.transCharCount = transCharCount;
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

    public double getTransCost() {
        return transCost;
    }

    public void setTransCost(double transCost) {
        this.transCost = transCost;
    }

    public ModelRef<Spot> getSpotRef() {
        return spotRef;
    }
}
