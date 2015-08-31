package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

/**
 * Spot Id の重複チェック
 * @author takahara
 *
 */
public class NoContentsValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static NoContentsValidator INSTANCE = new NoContentsValidator();
    
    /**
     * Spot
     */
    private Object target;
    
    /**
     * コンストラクタ
     */
    public NoContentsValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public NoContentsValidator(Object target) {
        super();
        this.target = target;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public NoContentsValidator(Object target, String message) {
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
        
        // 存在すれば正常
        if(target != null) {
            return null;
        }
        
        // 存在する場合エラーを返す
        if (message != null) {
            return message;
        }
        return ApplicationMessage.get(getMessageKey(), getLabel(name));
    }

    @Override
    protected String getMessageKey() {
        return "validator.geoAddressTooMany";
    }

}
