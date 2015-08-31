package com.pluspow.model;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

@Model(schemaVersion = 1)
public class GcsResources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /** 幅 */
    @Attribute(unindexed = true)
    private int width = 0;
    
    /** 高さ */
    @Attribute(unindexed = true)
    private int height = 0;
    
    /** コンテントタイプ */
    @Attribute(unindexed = true)
    private String contentType;
    
    /** サイズ */
    private int size;
    
    /** servingUrl */
    @Attribute(unindexed = true)
    private String servingUrl;
    
    /** 無効フラグ(アイコン画像の変更時に使用) */
    private boolean invalid = false;
    
    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    /** Userとの関連 */
    private ModelRef<Spot> spotRef = new ModelRef<Spot>(Spot.class);
    
    // ----------------------------------------------------------------------
    // その他
    // ----------------------------------------------------------------------
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
        GcsResources other = (GcsResources) obj;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getServingUrl() {
        return servingUrl;
    }

    public void setServingUrl(String servingUrl) {
        this.servingUrl = servingUrl;
    }

    public ModelRef<Spot> getSpotRef() {
        return spotRef;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}
