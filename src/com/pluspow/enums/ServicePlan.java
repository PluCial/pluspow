package com.pluspow.enums;

/**
 * サービスプラン
 * <pre>
 * Freeプラン：
 *      フリープランはベース言語を含めて2か国語に設定
 *      
 * Standardプラン：
 *      検索条件から言語を絞っているので、無制限でも言語の数を登録する必要がある。
 * </pre>
 * @author takahara
 */
public enum ServicePlan {
    FREE("フリー", 0, 2, 3000),
    STANDARD("スタンダード", 380, Lang.values().length, -1);
    
    /** 名前 */
    private String planName;
    
    /** 毎月の請求額 */
    private double monthlyAmount = 0;
    
    /**
     * 翻訳可能言語数上限
     */
    private int transLangMaxCount = 0;
    
    /**
     * 翻訳可能文字数上限(マイナスは無制限)
     */
    private int transCharMaxCount = -1;
    
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
            int transLangMaxCount,
            int transCharMaxCount) {
        this.planName = planName;
        this.monthlyAmount = monthlyAmount;
        this.transCharMaxCount = transCharMaxCount;
        this.transLangMaxCount = transLangMaxCount;
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

    public int getTransLangMaxCount() {
        return transLangMaxCount;
    }

    public void setTransLangMaxCount(int transLangMaxCount) {
        this.transLangMaxCount = transLangMaxCount;
    }
}
