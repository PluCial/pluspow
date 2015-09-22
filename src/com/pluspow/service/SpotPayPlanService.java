package com.pluspow.service;

import java.util.Calendar;
import java.util.Date;

import com.pluspow.dao.SpotPayPlanDao;
import com.pluspow.enums.ServicePlan;
import com.pluspow.exception.ChangePlanException;
import com.pluspow.exception.ObjectNotExistException;
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
     * @throws ObjectNotExistException 
     */
    public static SpotPayPlan getPlan(Spot spot) throws ObjectNotExistException {
        SpotPayPlan model =  dao.getPlan(spot);
        if(model == null) throw new ObjectNotExistException();
        return model;
    }
    
    /**
     * スタンダードプランスタート
     * @param spot
     * @return
     * @throws ChangePlanException
     */
    public static SpotPayPlan startStandardPlan(Spot spot) throws ChangePlanException {

        SpotPayPlan plan = null;
        try {
            plan = getPlan(spot);
            // 有効なものが既に存在している場合はエラー
            throw new ChangePlanException();
            
        } catch (ObjectNotExistException e) {}
        
        plan = new SpotPayPlan();
        plan.setPlan(ServicePlan.STANDARD);
        plan.setMonthlyAmount(ServicePlan.STANDARD.getMonthlyAmount());
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
