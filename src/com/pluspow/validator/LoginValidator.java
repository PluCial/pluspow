package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

/**
 * ログインチェック
 * @author takahara
 *
 */
public class LoginValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static LoginValidator INSTANCE = new LoginValidator();
    
    /**
     * target
     */
    private Object target;
    
    /**
     * コンストラクタ
     */
    public LoginValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public LoginValidator(Object target) {
        super();
        this.target = target;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public LoginValidator(Object target, String message) {
        super(message);
        this.target = target;
    }

    /**
     * validate
     */
    public String validate(Map<String, Object> parameters, String name) {
        
        Object value = parameters.get(name);
        if (value == null || "".equals(value)) {
            return null;
        }
        
        // 存在すればログインOK
        if(target != null) {
            return null;
        }
        
        // 存在しなければエラー
        if (message != null) {
            return message;
        }
        return ApplicationMessage.get(getMessageKey(), getLabel(name));
    }

    @Override
    protected String getMessageKey() {
        return "validator.login";
    }

}
