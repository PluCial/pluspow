package com.pluspow.service;

import java.util.Calendar;
import java.util.Date;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Transaction;
import com.pluspow.dao.SpotPlanDao;
import com.pluspow.enums.ServicePlan;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotPlan;


public class SpotPlanService {
    
    /** DAO */
    private static final SpotPlanDao dao = new SpotPlanDao();
    
    /**
     * フリープランスタート(用コミット)
     * @param client
     * @param spot
     * @return
     */
    public static SpotPlan startFreePlan(Transaction tx, Spot spot) {
        
        SpotPlan plan = new SpotPlan();
        
        plan.setPlan(ServicePlan.FREE);
        plan.setMonthlyAmount(ServicePlan.FREE.getMonthlyAmount());
        plan.setStartDate(new Date());
        plan.setEndDate(new Date());
        plan.getSpotRef().setModel(spot);
        // 保存
        Datastore.put(tx, plan);
        
        return plan;
    }
    
    /**
     * スタンダードプランスタート
     * @param client
     * @param spot
     * @return
     */
    public static SpotPlan startStandardPlan(Spot spot) {
        // 存在しない場合エラー
        SpotPlan plan = spot.getSpotPlanRef().getModel();
        
        plan.setPlan(ServicePlan.STANDARD);
        plan.setMonthlyAmount(ServicePlan.STANDARD.getMonthlyAmount());
        plan.setBillingTarget(true);
        plan.setStartDate(new Date());
        plan.setEndDate(getEndDate());
        
        dao.put(plan);
        
        return plan;
    }
    
    /**
     * プレミアムスタート
     * @param client
     * @param spot
     * @return
     */
    public static SpotPlan startPremiumPlan(Spot spot) {
        
        // 存在しない場合エラー
        SpotPlan plan = spot.getSpotPlanRef().getModel();
        
        plan.setPlan(ServicePlan.PREMIUM);
        plan.setMonthlyAmount(ServicePlan.PREMIUM.getMonthlyAmount());
        plan.setBillingTarget(true);
        plan.setStartDate(new Date());
        plan.setEndDate(getEndDate());
        
        dao.put(plan);
        
        return plan;
    }
    
    /**
     * 終了日の取得(1年縛り)
     * @return
     */
    private static Date getEndDate() {
        // カレンダークラスのインスタンスを取得
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, 1);
        
        return cal.getTime();
    }

}
