package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

/**
 * Spot Id の重複チェック
 * @author takahara
 *
 */
public class PasswordConfirmationValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static PasswordConfirmationValidator INSTANCE = new PasswordConfirmationValidator();
    
    /**
     * passwordConfirmation
     */
    private String passwordConfirmation;
    
    /**
     * コンストラクタ
     */
    public PasswordConfirmationValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public PasswordConfirmationValidator(String passwordConfirmation) {
        super();
        this.passwordConfirmation = passwordConfirmation;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public PasswordConfirmationValidator(String passwordConfirmation, String message) {
        super(message);
        this.passwordConfirmation = passwordConfirmation;
    }

    /**
     * validate
     */
    public String validate(Map<String, Object> parameters, String name) {
        
        Object value = parameters.get(name);
        if (value == null || "".equals(value)) {
            return null;
        }
        
        // 正常の場合
        if(passwordConfirmation != null 
                && value.equals(passwordConfirmation)) {
            return null;
        }
        
        // 正常ではない場合
        if (message != null) {
            return message;
        }
        return ApplicationMessage.get(getMessageKey(), getLabel(name));
    }

    @Override
    protected String getMessageKey() {
        return "validator.passwordConfirmation";
    }

}
