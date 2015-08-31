package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

/**
 * Spot Id の重複チェック
 * @author takahara
 *
 */
public class TooManyValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static TooManyValidator INSTANCE = new TooManyValidator();
    
    /**
     * Spot
     */
    private Object target;
    
    /**
     * コンストラクタ
     */
    public TooManyValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public TooManyValidator(Object target) {
        super();
        this.target = target;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public TooManyValidator(Object target, String message) {
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
        
        // 存在しなければ正常
        if(target == null) {
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
