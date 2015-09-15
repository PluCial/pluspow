package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

/**
 * このバリデーションは必ずNGを返す
 * @author takahara
 *
 */
public class NGValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static NGValidator INSTANCE = new NGValidator();
    
    /**
     * コンストラクタ
     */
    public NGValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public NGValidator(String message) {
        super(message);
    }

    /**
     * validate
     */
    public String validate(Map<String, Object> parameters, String name) {
        
        Object value = parameters.get(name);
        if (value == null || "".equals(value)) {
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
        return "";
    }

}
