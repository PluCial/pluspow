package com.pluspow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.OfficeHoursDao;
import com.pluspow.enums.DayOfWeek;
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
     */
    public static List<OfficeHours> getOfficeHourList(Spot spot) {
        return dao.getOfficeHourList(spot);
    }
    
    /**
     * 営業時間マップの取得
     * @return
     */
    public static Map<String, OfficeHours> getOfficeHoursMap(List<OfficeHours> list) {
        Map<String,OfficeHours> map = new HashMap<String,OfficeHours>();
        
        if(list == null) return map;
        
        for (OfficeHours i : list) map.put(i.getDayOfWeek().toString(),i);
        
        return map;
    }
    
    /**
     * 特定曜日の営業時間の取得
     * @param spot
     * @param dayOfWeek
     * @return
     */
    public static OfficeHours getOfficeHour(Spot spot, DayOfWeek dayOfWeek) {
        return dao.get(createKey(spot, dayOfWeek));
    }
    
    /**
     * デフォルト営業時間の追加
     * @param tx
     * @param spot
     * @param dayOfWeek
     * @return
     */
    public static OfficeHours addDefault(
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
     */
    public static OfficeHours update(Spot spot, DayOfWeek dayOfWeek, int openHour, int openMinute, int closeHour, int closeMinute) {
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
     */
    public static OfficeHours setIsNotOpen(Spot spot, DayOfWeek dayOfWeek) {
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
    public static Key createKey(Spot spot, DayOfWeek dayOfWeek) {
        return createKey(spot.getKey().getId() + "_" + dayOfWeek.toString());
    }

}
