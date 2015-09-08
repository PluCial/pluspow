package com.pluspow.dao;

import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.meta.OfficeHoursMeta;
import com.pluspow.model.OfficeHours;
import com.pluspow.model.Spot;

public class OfficeHoursDao extends DaoBase<OfficeHours>{
    
    /** META */
    private static final OfficeHoursMeta meta =  OfficeHoursMeta.get();
    
    /**
     * 営業時間曜日リストの取得
     * @param spot
     * @return
     */
    public List<OfficeHours> getOfficeHourList(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey())
                    ).asList();
    }

}
