package com.pluspow.dao;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.meta.SpotPayPlanMeta;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotPayPlan;

public class SpotPayPlanDao extends DaoBase<SpotPayPlan>{
    
    /** META */
    private static final SpotPayPlanMeta meta = SpotPayPlanMeta.get();
    
    /**
     * スポットの有料プランを取得
     * @param spot
     * @param plan
     * @return
     */
    public SpotPayPlan getPlan(Spot spot) {
        return Datastore.query(meta)
                .filter(
                    meta.spotRef.equal(spot.getKey()),
                    meta.endDate.greaterThanOrEqual(new Date())
                        )
                    .sort(meta.endDate.desc)
                    .asSingle();
    }
    
    /**
     * 決済対象リストを取得
     * @return
     */
    public List<SpotPayPlan> getBillingTargetList() {
        return Datastore.query(meta)
                .filter(
                    meta.endDate.greaterThanOrEqual(new Date())
                        ).asList();
    }

}
