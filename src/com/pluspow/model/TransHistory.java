package com.pluspow.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.pluspow.enums.Lang;
import com.pluspow.enums.ObjectType;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;

@Model(schemaVersion = 1)
public class TransHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * オブジェクトタイプ
     */
    private ObjectType objectType;
    
    /**
     * 翻訳元の言語
     */
    @Attribute(unindexed = true)
    private Lang baseLang;
    
    /**
     * 翻訳言語
     */
    @Attribute(unindexed = true)
    private Lang transLang;
    
    /**
     * 翻訳タイプ
     */
    @Attribute(unindexed = true)
    private TransType transType;
    
    /**
     * 翻訳ステータス
     */
    @Attribute(unindexed = true)
    private TransStatus transStatus;
    
    /**
     * 翻訳文字数
     */
    @Attribute(unindexed = true)
    private int transCharCount = 0;
    
    /**
     * 文字単価
     */
    @Attribute(unindexed = true)
    private double charUnitPrice = 0.0;
    
    /**
     * 翻訳コスト
     */
    @Attribute(unindexed = true)
    private double transCost = 0;
    
    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    
    /** Spotとの関連 */
    private ModelRef<Spot> spotRef = new ModelRef<Spot>(Spot.class);
    
    /** Itemとの関連 */
    private ModelRef<Item> itemRef = new ModelRef<Item>(Item.class);
    
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
        TransHistory other = (TransHistory) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
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

    public ModelRef<Spot> getSpotRef() {
        return spotRef;
    }

    public double getTransCost() {
        return transCost;
    }

    public void setTransCost(double transCost) {
        this.transCost = transCost;
    }

    public int getTransCharCount() {
        return transCharCount;
    }

    public void setTransCharCount(int transCharCount) {
        this.transCharCount = transCharCount;
    }

    public double getCharUnitPrice() {
        return charUnitPrice;
    }

    public void setCharUnitPrice(double charUnitPrice) {
        this.charUnitPrice = charUnitPrice;
    }

    public Lang getBaseLang() {
        return baseLang;
    }

    public void setBaseLang(Lang baseLang) {
        this.baseLang = baseLang;
    }

    public Lang getTransLang() {
        return transLang;
    }

    public void setTransLang(Lang transLang) {
        this.transLang = transLang;
    }

    public TransType getTransType() {
        return transType;
    }

    public void setTransType(TransType transType) {
        this.transType = transType;
    }

    public TransStatus getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(TransStatus transStatus) {
        this.transStatus = transStatus;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public ModelRef<Item> getItemRef() {
        return itemRef;
    }
}
