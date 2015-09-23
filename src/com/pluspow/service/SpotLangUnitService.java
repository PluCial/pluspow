package com.pluspow.service;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.SpotLangUnitDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TooManyException;
import com.pluspow.model.GeoModel;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotLangUnit;


public class SpotLangUnitService extends LangUnitService {
    
    /** DAO */
    private static final SpotLangUnitDao dao = new SpotLangUnitDao();
    
    /**
     * 取得(無効なものも含まれる)
     * @param spot
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static SpotLangUnit get(Spot spot, Lang lang) throws ObjectNotExistException {
        SpotLangUnit model = dao.getLangInfo(spot, lang);
        
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    /**
     * リストの取得
     * @param spot
     * @return
     * @throws ObjectNotExistException 
     * @throws ArgumentException 
     */
    public static List<SpotLangUnit> getList(Spot spot) throws ObjectNotExistException, ArgumentException {
        if(spot.getPlan() == null) throw new ArgumentException();
        
        List<SpotLangUnit> list = dao.getList(spot, spot.getPlan().getTransLangMaxCount());
        
        if(list == null) throw new ObjectNotExistException();
        
        return list;
    }
    
//    /**
//     * リストの取得
//     * @param spot
//     * @param invalid
//     * @return
//     * @throws ObjectNotExistException 
//     */
//    public static List<SpotLangUnit> getList(Spot spot, boolean invalid) throws ObjectNotExistException {
//
//        List<SpotLangUnit> allUnitList = getList(spot);
//        
//        List<SpotLangUnit> unitList = new ArrayList<SpotLangUnit>();
//        for(SpotLangUnit unit: allUnitList) {
//            if(unit.isInvalid() == invalid) unitList.add(unit);
//        }
//        
//        return unitList;
//    }
    
    /**
     * 新しいモデルの取得(未永久化)
     * @param spot
     * @param lang
     * @param geoModel
     * @return
     */
    public static SpotLangUnit getNewModel(Spot spot, Lang lang, String phoneNumber, GeoModel geoModel) {
        
        SpotLangUnit info = new SpotLangUnit();
        
        // 言語の設定
        info.setLang(lang);
        
        // ベース言語の場合
        if(spot.getBaseLang() == lang) {
            info.setPhoneDisplayFlg(true);
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
    protected static SpotLangUnit addBaseLang(Transaction tx, Spot spot, SpotLangUnit info) throws TooManyException {

        try {
            // 既に存在した場合はエラー
            get(spot, info.getLang());
            throw new TooManyException();
            
        } catch (ObjectNotExistException e) {}
        
        // キーの設定
        info.setKey(createKey(spot));
        info.setBaseLang(true);
        info.setTransStatus(TransStatus.TRANSLATED);
        
        // 保存
        Datastore.put(tx, info);
        
        return info;
    }
    
    /**
     * 追加(用コミット)
     * @param tx
     * @param spot
     * @param lang
     * @param geoModel
     * @param transType
     * @param trans
     * @return
     * @throws ArgumentException 
     * @throws TooManyException
     */
    protected static SpotLangUnit add(
            Transaction tx, 
            Spot spot, 
            Lang lang, 
            TransType transType, 
            TransStatus trans,
            GeoModel geoModel
            ) throws ArgumentException, TooManyException {
        
        // baseLangの場合はエラー
        if(spot.getBaseLang() == lang) throw new ArgumentException();
        
        try {
            // 既に存在した場合はエラー
            get(spot, lang);
            throw new TooManyException();
            
        } catch (ObjectNotExistException e) {}
        
        SpotLangUnit info =  getNewModel(spot, lang, spot.getPhoneNumber(), geoModel);
        
        // キーの設定
        info.setKey(createKey(spot));
        
        info.setTransType(transType);
        info.setTransStatus(trans);
        
        // 保存
        Datastore.put(tx, info);

        return info;
    }
    
    /**
     * 電話番号表示フラグの変更
     * @param spot
     * @param lang
     * @param displayFlg
     * @throws ObjectNotExistException 
     */
    public static void changePhoneDisplayFlg(Spot spot, Lang lang, boolean displayFlg) throws ObjectNotExistException {
        // 取得
        SpotLangUnit info = get(spot, lang);
        
        info.setPhoneDisplayFlg(displayFlg);
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
        info.setGeoAreaLevel1(geoModel.getAdministrativeAreaLevel1LongName());
        info.setGeoAreaLevel2(geoModel.getAdministrativeAreaLevel2LongName());
        info.setGeoAreaLevel3(geoModel.getAdministrativeAreaLevel3LongName());
        info.setGeoLocality(geoModel.getLocalityLongName());
        info.setGeoWardLocality(geoModel.getWardLocalityLongName());
        info.setGeoSublocality(geoModel.getSublocalityLongName());
    }

}
