package com.pluspow.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.SpotLangUnitDao;
import com.pluspow.enums.SupportLang;
import com.pluspow.exception.TooManyException;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;


public class SpotLangUnitService {
    
    /** DAO */
    private static final SpotLangUnitDao dao = new SpotLangUnitDao();
    
    /**
     * 取得
     * @param spot
     * @param lang
     * @return
     */
    public static SpotLangUnit get(Spot spot, SupportLang lang) {
        return dao.getLangInfo(spot, lang);
    }
    
    /**
     * リストの取得
     * @param spot
     * @return
     */
    public static List<SpotLangUnit> getList(Spot spot) {
        return dao.getList(spot);
    }
    
    /**
     * 新しいモデルの取得(未永久化)
     * @param spot
     * @param lang
     * @param geoModel
     * @return
     */
    public static SpotLangUnit getNewModel(Spot spot, SupportLang lang, String phoneNumber, GeoModel geoModel) {
        
        SpotLangUnit info = new SpotLangUnit();
        
        // 言語の設定
        info.setLang(lang);
        
        // ベース言語の場合
        if(spot.getBaseLang() == lang) {
            info.setPhoneDisplayFlg(true);
            info.setContactDisplayFlg(true);
        }
        
        // Spotキーの設定
        info.getSpotRef().setModel(spot);
        
        // 電話番号の設定
        info.setPhoneNumber(new PhoneNumber(phoneNumber));
        
        // GEO 情報の設定
        setSpotGeo(info, geoModel);
        
        return info;
    }
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param spot
     * @param lang
     * @param geoModel
     * @return
     * @throws TooManyException 
     */
    public static SpotLangUnit addBaseLang(Transaction tx, Spot spot, SpotLangUnit info) throws TooManyException {
        
        if(get(spot, info.getLang()) != null) throw new TooManyException();
        
        // キーの設定
        info.setKey(createKey(spot, spot.getBaseLang()));
        
        // 保存
        Datastore.put(tx, info);
        
        return info;
    }
    
    /**
     * 追加(用コミット)
     * <pre>
     * 言語を一回削除して再追加する場合は以前の情報を上書きする
     * </pre>
     * @param tx
     * @param spot
     * @param lang
     * @param geoModel
     * @return
     * @throws TooManyException 
     */
    public static SpotLangUnit add(Transaction tx, Spot spot, SupportLang lang, GeoModel geoModel) throws TooManyException {
        
        if(get(spot, lang) != null) throw new TooManyException();
        
        SpotLangUnit info =  getNewModel(spot, lang, spot.getPhoneNumber(), geoModel);
        
        // キーの設定
        info.setKey(createKey(spot, lang));
        
        // 保存
        Datastore.put(tx, info);
        
        return info;
    }
    
    /**
     * 削除処理
     * @param tx
     * @param spot
     * @param lang
     */
    public static void delete(Transaction tx, Spot spot, SupportLang lang) {
        
        SpotLangUnit langUnit = get(spot, lang);
        if(langUnit == null) return;
        
        // 削除処理
        Datastore.delete(tx, langUnit.getKey());
    }
    
    /**
     * 電話番号表示フラグの変更
     * @param spot
     * @param lang
     * @param displayFlg
     */
    public static void changePhoneDisplayFlg(Spot spot, SupportLang lang, boolean displayFlg) {
        // 取得
        SpotLangUnit info = get(spot, lang);
        
        info.setPhoneDisplayFlg(displayFlg);
        dao.put(info);
    }
    
    /**
     * コンタクト表示フラグの変更
     * @param spot
     * @param lang
     * @param displayFlg
     */
    public static void changeContactDisplayFlg(Spot spot, SupportLang lang, boolean displayFlg) {
        // 取得
        SpotLangUnit info = get(spot, lang);
        
        info.setContactDisplayFlg(displayFlg);
        dao.put(info);
    }
    
    /**
     * 位置情報の設定
     * @param spot
     * @param geoModel
     */
    private static void setSpotGeo(SpotLangUnit info, GeoModel geoModel) {
        info.setGeoPostalAddress(new PostalAddress(geoModel.getPostalCodeLongName()));
        info.setGeoCountry(geoModel.getCountryLongName());
        info.setGeoFormattedAddress(geoModel.getFormattedAddress());
        info.setGeoLat(geoModel.getLat().floatValue());
        info.setGeoLng(geoModel.getLng().floatValue());
        info.setGeoAreaLevel1(geoModel.getAdministrativeAreaLevel1LongName());
        info.setGeoAreaLevel2(geoModel.getAdministrativeAreaLevel2LongName());
        info.setGeoAreaLevel3(geoModel.getAdministrativeAreaLevel3LongName());
        info.setGeoLocality(geoModel.getLocalityLongName());
        info.setGeoWardLocality(geoModel.getWardLocalityLongName());
        info.setGeoSublocality(geoModel.getSublocalityLongName());
    }
    
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    private static Key createKey(Spot spot, SupportLang lang) {
        return Datastore.createKey(SpotLangUnit.class, spot.getKey().getId() + "_" + lang.toString());
    }

}
