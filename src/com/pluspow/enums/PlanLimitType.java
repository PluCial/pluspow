package com.pluspow.enums;

/**
 * テキストリソース役割
 * <pre>
 * 追加や変更の場合は必ずTextResourcesService 内のUpdateも合わせて修正
 * </pre>
 * @author takahara
 *
 */
public enum PlanLimitType {
    TRANS_LANG_MAX_COUNT("現在ご利用しているプランの「利用可能言語数」を超えました。", "言語の追加をご希望の場合はスタンダードプランをご利用ください。"), 
    TRANS_CHAR_MAX_COUNT("現在ご利用しているプランの「翻訳文字数上限」を超えました。", "これ以上翻訳をご希望の場合はスタンダードプランをご利用ください。"), 
    HUMAN_TRANS("フリープランでは「人力翻訳」機能をご利用できません。", "人力翻訳をご希望の場合はスタンダードプランをご利用ください。");
    
    private String title;
    private String message;
    
    private PlanLimitType(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
