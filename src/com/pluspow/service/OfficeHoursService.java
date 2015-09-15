package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.OfficeHoursDao;
import com.pluspow.enums.DayOfWeek;
import com.pluspow.exception.ObjectNotExistException;
import com.pluspow.model.OfficeHours;
import com.pluspow.model.Spot;

/**
 * スポットの営業時間
 * @author takahara
 *
 */
public class OfficeHoursService {
    
    /**
     * DAO
     */
    private static final OfficeHoursDao dao = new OfficeHoursDao();
    
    /**
     * 営業時間曜日リストの取得
     * @param spot
     * @return
     * @throws ObjectNotExistException 
     */
    protected static List<OfficeHours> getOfficeHourList(Spot spot) throws ObjectNotExistException {
        List<OfficeHours> list =  dao.getOfficeHourList(spot);
        
        if(list == null) throw new ObjectNotExistException();
        
        return list;
    }
    
    /**
     * 営業時間マップの取得
     * @return
     */
    public static Map<String, OfficeHours> getOfficeHoursMap(List<OfficeHours> list) {
        Map<String,OfficeHours> map = new HashMap<String,OfficeHours>();
        
        for (OfficeHours i : list) map.put(i.getDayOfWeek().toString(),i);
        
        return map;
    }
    
    /**
     * 特定曜日の営業時間の取得
     * @param spot
     * @param dayOfWeek
     * @return
     * @throws ObjectNotExistException 
     */
    public static OfficeHours getOfficeHour(Spot spot, DayOfWeek dayOfWeek) throws ObjectNotExistException {
        OfficeHours model =  dao.get(createKey(spot, dayOfWeek));
        
        if(model == null) throw new ObjectNotExistException();
        
        return model;
    }
    
    /**
     * デフォルト営業時間の追加
     * @param tx
     * @param spot
     * @param dayOfWeek
     * @return
     */
    protected static OfficeHours addDefault(
            Transaction tx, 
            Spot spot, 
            DayOfWeek dayOfWeek) {
        
        OfficeHours model = new OfficeHours();
        model.setKey(createKey(spot, dayOfWeek));
        model.setDayOfWeek(dayOfWeek);
        model.getSpotRef().setModel(spot);
        
        // 追加
        Datastore.put(tx, model);
        
        return model;
    }
    
    /**
     * 更新
     * @param spot
     * @param dayOfWeek
     * @param open
     * @param openHour
     * @param openMinute
     * @param closeHour
     * @param closeMinute
     * @return
     * @throws ObjectNotExistException 
     */
    public static OfficeHours update(Spot spot, DayOfWeek dayOfWeek, int openHour, int openMinute, int closeHour, int closeMinute) throws ObjectNotExistException {
        
        OfficeHours model = getOfficeHour(spot, dayOfWeek);

        model.setOpen(true);
        model.setOpenHour(openHour);
        model.setOpenMinute(openMinute);
        model.setCloseHour(closeHour);
        model.setCloseMinute(closeMinute);
        
        // 更新
        dao.put(model);
        
        return model;
    }
    
    /**
     * 非営業日に設定
     * @param spot
     * @param dayOfWeek
     * @return
     * @throws ObjectNotExistException 
     */
    public static OfficeHours setIsNotOpen(Spot spot, DayOfWeek dayOfWeek) throws ObjectNotExistException {
        OfficeHours model = getOfficeHour(spot, dayOfWeek);

        model.setOpen(false);
        
        // 更新
        dao.put(model);
        
        return model;
    }
    
    // ----------------------------------------------------------------------
    // キーの作成
    // ----------------------------------------------------------------------
    /**
     * キーの作成
     * @param keyString
     * @return
     */
    private static Key createKey(String keyString) {
        return Datastore.createKey(OfficeHours.class, keyString);
    }
    
    
    /**
     * キーの作成
     * @return
     */
    protected static Key createKey(Spot spot, DayOfWeek dayOfWeek) {
        return createKey(spot.getKey().getId() + "_" + dayOfWeek.toString());
    }

}
