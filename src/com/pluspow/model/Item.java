package com.pluspow.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Key;
import com.pluspow.enums.Lang;
import com.pluspow.enums.SpotActivity;
import com.pluspow.enums.TextResRole;
import com.pluspow.service.ItemTextResService;

@Model(schemaVersion = 1)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * アイテムタイプ(関連するアクティビティ)
     */
    private SpotActivity activity;
    
    /**
     * 値段
     */
    private double price = 0;
    
    /**
     * ソート順
     */
    private double sortOrder = 0.0;
    
    /**
     * ベース言語
     */
    @Attribute(unindexed = true)
    private Lang baseLang;
    
    /**
     * 翻訳した言語リスト
     */
    private List<Lang> langs = new ArrayList<Lang>();
    
    /**
     * 無効フラグ
     */
    private boolean invalid = false;
    
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
    // 関連
    // ----------------------------------------------------------------------
    /** Spotとの関連 */
    private ModelRef<Spot> spotRef = new ModelRef<Spot>(Spot.class);
    
    /**
     * spotId 検索した結果リストの表示時に利用する
     */
    @Attribute(unindexed = true)
    private String spotId;
    
    // ----------------------------------------------------------------------
    // SpotLangUnit(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * 言語情報
     */
    @Attribute(persistent = false)
    private ItemLangUnit langUnit;
    
    // ----------------------------------------------------------------------
    // TextResources
    // ----------------------------------------------------------------------
    /**
     * 名前リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private ItemTextRes nameRes;

    /**
     * 詳細リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private ItemTextRes detailRes;

    /**
     * Resourcesの設定
     * @param resourcesMap
     */
    public void setTextResources(Map<String, ItemTextRes> resourcesMap) {
        this.setNameRes(
            ItemTextResService.getResourcesByMap(resourcesMap, TextResRole.ITEM_NAME)
                );
        
        this.setDetailRes(
            ItemTextResService.getResourcesByMap(resourcesMap, TextResRole.ITEM_DETAIL)
                );
    }

    public String getName() {
        return nameRes == null ? null : nameRes.getContentString();
    }
    
    public String getNameResKey() {
        return nameRes == null ? null : nameRes.getKey().getName();
    }
    
    public String getDetail() {
        return detailRes == null ? null : detailRes.getContentString();
    }
    
    public String getDetailResKey() {
        return detailRes == null ? null : detailRes.getKey().getName();
    }
    
    // ----------------------------------------------------------------------
    // GCSリソース
    // ----------------------------------------------------------------------
    /**
     * アイコンイメージリソース(永久化しない)
     */
    @Attribute(persistent = false)
    private ItemGcsRes itemImageRes;
    
    /**
     * UserのGcs Resourcesの設定
     * @param resourcesList
     */
    public void setGcsResources(List<ItemGcsRes> resourcesList) {
        
        if(resourcesList == null || resourcesList.size() <= 0) return;
        
        this.setItemImageRes(resourcesList.get(0));
    }
    
    public String getItemImageUrl() {
        return itemImageRes == null ? "" : itemImageRes.getServingUrl();
    }
    
    public String getItemImageResKey() {
        return itemImageRes == null ? null : itemImageRes.getKey().getName();
    }
    
    // ----------------------------------------------------------------------
    // ゲッター＆セッター
    // ----------------------------------------------------------------------
    
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
        Item other = (Item) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public Lang getBaseLang() {
        return baseLang;
    }

    public void setBaseLang(Lang baseLang) {
        this.baseLang = baseLang;
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

    public double getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public double getPrice() {
        return price;
    }
    
    public String getPriceString() {
        NumberFormat fPrice = NumberFormat.getNumberInstance();
        return fPrice.format((int)getPrice());
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }
    
    public ItemTextRes getNameRes() {
        return nameRes;
    }

    public void setNameRes(ItemTextRes nameRes) {
        this.nameRes = nameRes;
    }

    public ItemGcsRes getItemImageRes() {
        return itemImageRes;
    }

    public void setItemImageRes(ItemGcsRes itemImageRes) {
        this.itemImageRes = itemImageRes;
    }
    
    public ItemTextRes getDetailRes() {
        return detailRes;
    }

    public void setDetailRes(ItemTextRes detailRes) {
        this.detailRes = detailRes;
    }

    public List<Lang> getLangs() {
        return langs;
    }

    public void setLangs(List<Lang> langs) {
        this.langs = langs;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public ItemLangUnit getLangUnit() {
        return langUnit;
    }

    public void setLangUnit(ItemLangUnit langUnit) {
        this.langUnit = langUnit;
    }

    public SpotActivity getActivity() {
        return activity;
    }

    public void setActivity(SpotActivity activity) {
        this.activity = activity;
    }
}
