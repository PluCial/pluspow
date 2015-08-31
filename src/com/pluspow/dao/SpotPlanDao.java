package com.pluspow.dao;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.DaoBase;
import org.slim3.datastore.Datastore;

import com.pluspow.meta.SpotPlanMeta;
import com.pluspow.model.SpotPlan;

public class SpotPlanDao extends DaoBase<SpotPlan>{
    
    /** META */
    private static final SpotPlanMeta meta = SpotPlanMeta.get();
    
    /**
     * 決済対象リストを取得
     * @return
     */
    public List<SpotPlan> getBillingTargetList() {
        return Datastore.query(meta)
                .filter(
                    meta.billingTarget.equal(true),
                    meta.endDate.greaterThanOrEqual(new Date())
                        ).asList();
    }

}
