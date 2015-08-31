package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

import com.google.code.geocoder.model.GeocoderStatus;
import com.pluspow.model.GeoModel;

/**
 * Spot Id の重複チェック
 * @author takahara
 *
 */
public class GeoStatusValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static GeoStatusValidator INSTANCE = new GeoStatusValidator();
    
    /**
     * GeoModel
     */
    private GeoModel geoModel;
    
    /**
     * コンストラクタ
     */
    public GeoStatusValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public GeoStatusValidator(GeoModel geoModel) {
        super();
        this.geoModel = geoModel;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public GeoStatusValidator(GeoModel geoModel, String message) {
        super(message);
        this.geoModel = geoModel;
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
        if(geoModel != null && geoModel.getStatus().equals(GeocoderStatus.OK)) {
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
        return "validator.geoStatus";
    }

}
