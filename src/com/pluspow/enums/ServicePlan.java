package com.pluspow.enums;

/**
 * サービスプラン
 * @author takahara
 */
public enum ServicePlan {
    FREE("フリー", 0, 3000, 10, 3),
    STANDARD("スタンダード", 980, -1, 50, 10),
    PREMIUM("プレミアム", 9800, -1, -1, -1);
    
    /** 名前 */
    private String planName;
    
    /** 毎月の請求額 */
    private double monthlyAmount = 0; 
    
    /**
     * 翻訳可能文字数上限(マイナスは無制限)
     */
    private int transCharMaxCount = -1;
    
    /**
     * 作成可能アイテム上限数(マイナスは無制限)
     */
    private int createItemMaxCount = -1;
    
    /**
     * 作成可能ハウツー上限数(マイナスは無制限)
     */
    private int createHowtoMaxCount = -1;
    
    /**
     * コンストラクタ
     * @param name
     * @param monthlyAmount
     * @param transCharMaxCount
     * @param createItemMaxCount
     * @param createHowtoMaxCount
     */
    private ServicePlan(
            String planName,
            double monthlyAmount,
            int transCharMaxCount, 
            int createItemMaxCount, 
            int createHowtoMaxCount) {
        this.planName = planName;
        this.monthlyAmount = monthlyAmount;
        this.transCharMaxCount = transCharMaxCount;
        this.createItemMaxCount = createItemMaxCount;
        this.createHowtoMaxCount = createHowtoMaxCount;
        
    }

    public String getPlanName() {
        return planName;
    }

    public double getMonthlyAmount() {
        return monthlyAmount;
    }

    public int getTransCharMaxCount() {
        return transCharMaxCount;
    }

    public int getCreateItemMaxCount() {
        return createItemMaxCount;
    }

    public int getCreateHowtoMaxCount() {
        return createHowtoMaxCount;
    }
}
