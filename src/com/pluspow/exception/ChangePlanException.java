package com.pluspow.exception;

/**
 * データ重複エラー
 * @author takahara
 *
 */
public class ChangePlanException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private static final String message = "プラン変更できません。";
    
    /**
     * コンストラクタ
     */
    public ChangePlanException() {
        super(message);
    }
    
    /**
     * コンストラクタ
     */
    public ChangePlanException(String message) {
        super(message);
    }

}
