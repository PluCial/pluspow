package com.pluspow.service;

import java.util.Calendar;
import java.util.Date;

import com.pluspow.dao.SpotPayPlanDao;
import com.pluspow.enums.ServicePlan;
import com.pluspow.exception.ChangePlanException;
import com.pluspow.model.Spot;
import com.pluspow.model.SpotPayPlan;


public class SpotPayPlanService {
    
    /** DAO */
    private static final SpotPayPlanDao dao = new SpotPayPlanDao();
    
    /**
     * スポットの現在の有料プランを取得
     * @param spot
     * @param plan
     * @return
     */
    public static SpotPayPlan getPlan(Spot spot) {
        return dao.getPlan(spot);
    }
    
    /**
     * スタンダードプランスタート
     * @param spot
     * @return
     * @throws ChangePlanException
     */
    public static SpotPayPlan startStandardPlan(Spot spot) throws ChangePlanException {

        SpotPayPlan plan = dao.getPlan(spot);
        
        // 有効なものが既に存在している場合はエラー
        if(plan != null) throw new ChangePlanException();
        
        plan = new SpotPayPlan();
        plan.setPlan(ServicePlan.STANDARD);
        plan.setMonthlyAmount(ServicePlan.STANDARD.getMonthlyAmount());
        plan.setStartDate(new Date());
        plan.setEndDate(getEndDate());
        
        dao.put(plan);
        
        return plan;
    }
    
    /**
     * プレミアムスタート
     * @param spot
     * @return
     * @throws ChangePlanException
     */
    public static SpotPayPlan startPremiumPlan(Spot spot) throws ChangePlanException {
        
        SpotPayPlan plan = dao.getPlan(spot);
        
        // 有効なものが既に存在している場合はエラー
        if(plan != null) throw new ChangePlanException();
        
        plan = new SpotPayPlan();
        plan.setPlan(ServicePlan.PREMIUM);
        plan.setMonthlyAmount(ServicePlan.PREMIUM.getMonthlyAmount());
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
