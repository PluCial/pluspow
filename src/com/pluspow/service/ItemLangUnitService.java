package com.pluspow.service;

import java.util.ArrayList;
import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.ItemLangUnitDao;
import com.pluspow.enums.Lang;
import com.pluspow.enums.TransStatus;
import com.pluspow.enums.TransType;
import com.pluspow.exception.ArgumentException;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.exception.TooManyException;
import com.pluspow.model.Item;
import com.pluspow.model.ItemLangUnit;
import com.pluspow.model.Spot;


public class ItemLangUnitService extends LangUnitService {
    
    /** DAO */
    private static final ItemLangUnitDao dao = new ItemLangUnitDao();
    
    /**
     * 取得
     * @param spot
     * @param lang
     * @return
     * @throws ObjectNotExistException 
     */
    public static ItemLangUnit get(Item item, Lang lang) throws ObjectNotExistException {
        ItemLangUnit model = dao.getLangInfo(item, lang);
        
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    /**
     * リストの取得
     * @param spot
     * @return
     * @throws ObjectNotExistException 
     */
    public static List<ItemLangUnit> getList(Item item) throws ObjectNotExistException {
        List<ItemLangUnit> list = dao.getList(item);
        
        if(list == null) throw new ObjectNotExistException();
        
        return list;
    }
    
    /**
     * リストの取得
     * @param spot
     * @param invalid
     * @return
     * @throws ObjectNotExistException 
     */
    public static List<ItemLangUnit> getList(Item item, boolean invalid) throws ObjectNotExistException {

        List<ItemLangUnit> allUnitList = getList(item);
        
        List<ItemLangUnit> unitList = new ArrayList<ItemLangUnit>();
        for(ItemLangUnit unit: allUnitList) {
            if(unit.isInvalid() == invalid) unitList.add(unit);
        }
        
        return unitList;
    }
    
    /**
     * ベース言語の追加
     * @param tx
     * @param spot
     * @param item
     * @param lang
     * @return
     * @throws TooManyException
     */
    protected static ItemLangUnit addBaseLang(Transaction tx, Spot spot, Item item) throws TooManyException {

        try {
            // 既に存在した場合はエラー
            get(item, item.getBaseLang());
            throw new TooManyException();
            
        } catch (ObjectNotExistException e) {}
        
        ItemLangUnit model = new ItemLangUnit();
        model.setKey(createKey(spot));
        model.setLang(item.getBaseLang());
        model.getSpotRef().setModel(spot);
        model.getItemRef().setModel(item);
        model.setTransStatus(TransStatus.TRANSLATED);
        
        // 保存
        Datastore.put(tx, model);
        
        return model;
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
    protected static ItemLangUnit add(
            Transaction tx, 
            Spot spot, 
            Item item,
            Lang baseLang,
            Lang lang, 
            TransType transType, 
            TransStatus trans
            ) throws ArgumentException, TooManyException {
        
        // baseLangの場合はエラー
        if(item.getBaseLang() == lang) throw new ArgumentException();
        
        try {
            // 既に存在した場合はエラー
            get(item, lang);
            throw new TooManyException();
            
        } catch (ObjectNotExistException e) {}
        
        ItemLangUnit model = new ItemLangUnit();
        model.setKey(createKey(spot));
        model.setBaseLang(baseLang);
        model.setLang(lang);
        model.getSpotRef().setModel(spot);
        model.getItemRef().setModel(item);
        model.setTransType(transType);
        model.setTransStatus(trans);
        
        // 保存
        Datastore.put(tx, model);

        return model;
    }
}
