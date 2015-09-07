package com.pluspow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.CreationDate;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.ModificationDate;

import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PostalAddress;
import com.pluspow.enums.ServicePlan;
import com.pluspow.enums.SpotActivity;
import com.pluspow.enums.SpotGcsResRole;
import com.pluspow.enums.SupportLang;
import com.pluspow.enums.TextResRole;
import com.pluspow.service.SpotGcsResService;
import com.pluspow.service.SpotTextResService;

/**
 * スポットモデル
 * @author takahara
 *
 */
@Model(schemaVersion = 1)
public class Spot implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * String Key(SpotId)
     */
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    /**
     * スポットID
     */
    private String spotId;
    
    /**
     * メールアドレス(ログインとは別に連絡用としてのメールアドレス)
     */
    @Attribute(unindexed = true)
    private Email email;
    
    /**
     * アイテム数
     */
    @Attribute(lob = true)
    private ItemCounts itemCounts = new ItemCounts();
    
    /**
     * ベース言語
     */
    @Attribute(unindexed = true)
    private SupportLang baseLang;
    
    /**
     * 翻訳した言語リスト
     */
    private List<SupportLang> supportLangs = new ArrayList<SupportLang>();
    
    // ----------------------------------------------------------------------
    // 関連
    // ----------------------------------------------------------------------
    
    /** Clientとの関連 */
    private ModelRef<Client> clientRef = new ModelRef<Client>(Client.class);
    
    /** TransCreditとの関連 */
    private ModelRef<TransCredit> transCreditRef = new ModelRef<TransCredit>(TransCredit.class);
    
    // ----------------------------------------------------------------------
    // その他
    // ----------------------------------------------------------------------
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
    // SpotLangInfo(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * 言語情報
     */
    @Attribute(persistent = false)
    private SupportLangInfo supportLangInfo;
    
    public String getPhoneNumber() {
        if(supportLangInfo.getPhoneNumber() == null) return null;
        
        if(baseLang == supportLangInfo.getLang()) {
            return supportLangInfo.getPhoneNumber().getNumber();
        }
        
        StringBuilder sb1 = new StringBuilder(supportLangInfo.getPhoneNumber().getNumber());
        if(sb1.length() > 1 && sb1.charAt(0) == '0') {
            sb1.delete(0,1);
        }
        
        return "+81 " + sb1.toString();
    }
    
    public boolean isPhoneDisplayFlg() {
        return supportLangInfo.isPhoneDisplayFlg();
    }

    public boolean isContactDisplayFlg() {
        return supportLangInfo.isContactDisplayFlg();
    }
    
    public String getGeoFormattedAddress() {
        return supportLangInfo.getGeoFormattedAddress();
    }

    public float getGeoLat() {
        return supportLangInfo.getGeoLat();
    }

    public float getGeoLng() {
        return supportLangInfo.getGeoLng();
    }
    
    public String getGeoCountry() {
        return supportLangInfo.getGeoCountry();
    }

    public String getGeoAreaLevel1() {
        return supportLangInfo.getGeoAreaLevel1();
    }

    public String getGeoAreaLevel2() {
        return supportLangInfo.getGeoAreaLevel2();
    }

    public String getGeoAreaLevel3() {
        return supportLangInfo.getGeoAreaLevel3();
    }

    public String getGeoWardLocality() {
        return supportLangInfo.getGeoWardLocality();
    }

    public String getGeoSublocality() {
        return supportLangInfo.getGeoSublocality();
    }

    public PostalAddress getGeoPostalAddress() {
        return supportLangInfo.getGeoPostalAddress();
    }

    public String getGeoLocality() {
        return supportLangInfo.getGeoLocality();
    }
    
    // ----------------------------------------------------------------------
    // TextResources(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * 名前リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private SpotTextRes nameRes;
    
    /**
     * キャッチコピーリソース
     */
    @Attribute(persistent = false)
    private SpotTextRes catchCopyRes;

    /**
     * 詳細リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private SpotTextRes detailRes;
    
    /**
     * 詳細リソース(永久かしない)
     */
    @Attribute(persistent = false)
    private SpotTextRes addressRes;

    /**
     * Resourcesの設定
     * @param resourcesMap
     */
    public void setTextResources(Map<String, SpotTextRes> resourcesMap) {
        this.setNameRes(
            SpotTextResService.getResourcesByMap(resourcesMap, TextResRole.SPOT_NAME)
                );
        
        this.setCatchCopyRes(
            SpotTextResService.getResourcesByMap(resourcesMap, TextResRole.SPOT_CATCH_COPY)
                );
        
        this.setDetailRes(
            SpotTextResService.getResourcesByMap(resourcesMap, TextResRole.SPOT_DETAIL)
                );
        
        this.setAddressRes(
            SpotTextResService.getResourcesByMap(resourcesMap, TextResRole.SPOT_ADDRESS)
                );
    }
    
    public String getName() {
        return nameRes == null ? null : nameRes.getContentString();
    }
    
    public String getNameResKey() {
        return nameRes == null ? null : nameRes.getKey().getName();
    }
    
    public String getCatchCopy() {
        return catchCopyRes == null ? null : catchCopyRes.getContentString();
    }
    
    public String getCatchCopyResKey() {
        return catchCopyRes == null ? null : catchCopyRes.getKey().getName();
    }
    
    public String getDetail() {
        return detailRes == null ? null : detailRes.getContentString();
    }
    
    public String getDetailResKey() {
        return detailRes == null ? null : detailRes.getKey().getName();
    }
    
    public String getAddress() {
        return addressRes == null ? null : addressRes.getContentString();
    }
    
    public String getAddressResKey() {
        return addressRes == null ? null : addressRes.getKey().getName();
    }
    
    // ----------------------------------------------------------------------
    // GCSリソース
    // ----------------------------------------------------------------------
    /**
     * アイコンイメージリソース(永久化しない)
     */
    @Attribute(persistent = false)
    private SpotGcsRes iconImageRes;
    @Attribute(persistent = false)
    private SpotGcsRes backgroundImageRes;

    /**
     * 背景画像のURL
     * @return
     */
    public String getBackgroundImageUrl() {
        return backgroundImageRes == null ? null : backgroundImageRes.getServingUrl();
    }
    
    public String getBackgroundImageResKey() {
        return backgroundImageRes == null ? null : backgroundImageRes.getKey().getName();
    }

    /**
     * アイコンイメージのUrlを取得
     * @return
     */
    public String getIconImageUrl() {
        return iconImageRes == null ? null : iconImageRes.getServingUrl();
    }
    
    public String getIconImageResKey() {
        return iconImageRes == null ? null : iconImageRes.getKey().getName();
    }
    
    /**
     * UserのGcs Resourcesの設定
     * @param resourcesMap
     */
    public void setGcsResources(Map<String, SpotGcsRes> resourcesMap) {
        this.setIconImageRes(
            SpotGcsResService.getResourcesByMap(resourcesMap, SpotGcsResRole.SPOT_ICON_IMAGE)
                );
        
        this.setBackgroundImageRes(
            SpotGcsResService.getResourcesByMap(resourcesMap, SpotGcsResRole.SPOT_BACKGROUND_IMAGE)
                );
    }
    
    // ----------------------------------------------------------------------
    // TransCredit(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * 翻訳クレジット
     */
    @Attribute(persistent = false)
    private TransCredit transAcc;
    
    // ----------------------------------------------------------------------
    // プラン(永久かしない)
    // ----------------------------------------------------------------------
    /**
     * プラン
     */
    @Attribute(persistent = false)
    private ServicePlan plan;
    
    public ServicePlan getPlan() {
        return plan;
    }

    public void setPlan(ServicePlan plan) {
        this.plan = plan;
    }
    
    
    // ----------------------------------------------------------------------
    // ゲッター＆セッター
    // ----------------------------------------------------------------------

    public String getSpotId() {
        return spotId;
    }

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
        Spot other = (Spot) obj;
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

    public Email getEmail() {
        return email;
    }
    
    public String getEmailString() {
        return email == null ? null : email.getEmail();
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public List<SpotActivity> getActivitys() {
        return supportLangInfo.getActivitys();
    }

    public ModelRef<Client> getClientRef() {
        return clientRef;
    }

    public TransCredit getTransAcc() {
        return transAcc;
    }

    public void setTransAcc(TransCredit transAcc) {
        this.transAcc = transAcc;
    }


    public SupportLangInfo getSpotLangInfo() {
        return supportLangInfo;
    }

    public void setSpotLangInfo(SupportLangInfo supportLangInfo) {
        this.supportLangInfo = supportLangInfo;
    }

    public SupportLang getBaseLang() {
        return baseLang;
    }

    public void setBaseLang(SupportLang baseLang) {
        this.baseLang = baseLang;
    }

    public List<SupportLang> getSupportLangs() {
        return supportLangs;
    }

    public void setSupportLangs(List<SupportLang> supportLangs) {
        this.supportLangs = supportLangs;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public ItemCounts getItemCounts() {
        return itemCounts;
    }

    public void setItemCounts(ItemCounts itemCounts) {
        this.itemCounts = itemCounts;
    }

    public ModelRef<TransCredit> getTransCreditRef() {
        return transCreditRef;
    }
    
    public SpotTextRes getNameRes() {
        return nameRes;
    }

    public void setNameRes(SpotTextRes nameRes) {
        this.nameRes = nameRes;
    }

    public SpotTextRes getCatchCopyRes() {
        return catchCopyRes;
    }

    public void setCatchCopyRes(SpotTextRes catchCopyRes) {
        this.catchCopyRes = catchCopyRes;
    }

    public SpotTextRes getDetailRes() {
        return detailRes;
    }

    public void setDetailRes(SpotTextRes detailRes) {
        this.detailRes = detailRes;
    }

    public SpotTextRes getAddressRes() {
        return addressRes;
    }

    public void setAddressRes(SpotTextRes addressRes) {
        this.addressRes = addressRes;
    }
    
    public SpotGcsRes getIconImageRes() {
        return iconImageRes;
    }

    public void setIconImageRes(SpotGcsRes iconImageRes) {
        this.iconImageRes = iconImageRes;
    }
    
    public void setBackgroundImageRes(SpotGcsRes backgroundImageRes) {
        this.backgroundImageRes = backgroundImageRes;
    }
}