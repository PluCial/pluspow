package com.pluspow.validator;

import java.util.Map;

import org.slim3.controller.validator.AbstractValidator;
import org.slim3.util.ApplicationMessage;

import com.google.code.geocoder.model.GeocoderLocationType;
import com.google.code.geocoder.model.GeocoderStatus;
import com.pluspow.model.GeoModel;

/**
 * Spot Id の重複チェック
 * @author takahara
 *
 */
public class GeoAddressValidator extends AbstractValidator {
    
    /**
     * The instance.
     */
    public static GeoAddressValidator INSTANCE = new GeoAddressValidator();
    
    /**
     * GeoModel
     */
    private GeoModel geoModel;
    
    /**
     * コンストラクタ
     */
    public GeoAddressValidator() {
        super();
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public GeoAddressValidator(GeoModel geoModel) {
        super();
        this.geoModel = geoModel;
    }
    
    /**
     * コンストラクタ
     * @param message
     */
    public GeoAddressValidator(GeoModel geoModel, String message) {
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
        if(geoModel != null 
                && geoModel.getStatus().equals(GeocoderStatus.OK) // API処理が以上の場合、getLocationでエラーが発生するので、念のために入れておく
                && geoModel.getLocationType().equals(GeocoderLocationType.ROOFTOP)) {
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
        return "validator.geoAddress";
    }

}
