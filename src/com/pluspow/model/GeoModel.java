package com.pluspow.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderLocationType;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;

public class GeoModel {
    
    /**
     * GEOレスポンス
     */
    private GeocodeResponse geocoderResponse;
    
    /**
     * 処理ステータス
     */
    private GeocoderStatus status;
    
    /**
     * コンストラクタ
     */
    public GeoModel(GeocodeResponse geocoderResponse) {
        this.setGeocoderResponse(geocoderResponse);
        this.status = geocoderResponse.getStatus();
    }
    
    /**
     * GeocoderResult
     * @return
     */
    public GeocoderResult getGeocoderResult() {
        return geocoderResponse.getResults().get(0);
    }
    
    /**
     * formatted_address
     * <pre>
     * 人が読み取れる、この場所の住所を含む文字列です。
     * 多くの場合、この住所は「郵便の宛先」と同一ですが、
     * 国によっては異なる場合があります（
     * イギリスなど一部の国では、ライセンス上の制限により実際の郵便の宛先を配布できません）。
     * 通常、この住所は、1 件以上の住所コンポーネントから構成されます。
     * たとえば、「111 8th Avenue, New York, NY」という住所には、
     * 「111」（番地）、「8th Avenue」（通り）、「New York」（市）、「NY」（米国の州）という独立した住所コンポーネントが含まれています。
     * </pre>
     * @return formatted_address
     */
    public String getFormattedAddress() {
        return getGeocoderResult().getFormattedAddress();
    }
    
    /**
     * geometry
     * @return
     */
    public GeocoderGeometry getGeometry() {
        return getGeocoderResult().getGeometry();
    }
    
    /**
     * location_type
     * <pre>
     * ・"ROOFTOP": 返された結果が、番地まで含めた位置情報に対する正確なジオコードであることを示します。
     * ・"RANGE_INTERPOLATED": 返された結果が、交差点など正確な 2 地点間で補間された近似値（通常は道路上）を反映していることを示します。
     *   補間された結果は通常、当該住所でルーフトップ ジオコーディングが利用できない場合に返されます。
     * ・"GEOMETRIC_CENTER": 返された結果がポリライン（道路など）やポリゴン（領域）などの結果の幾何学的中心であることを示します。
     * ・"APPROXIMATE": 返された結果が近似値であることを示します。
     * </pre>
     * @return
     */
    public GeocoderLocationType getLocationType() {
        return getGeometry().getLocationType();
    }
    
    /**
     * Location
     * @return
     */
    public LatLng getLocation() {
        return getGeometry().getLocation();
    }
    
    /**
     * Lat
     * @return
     */
    public BigDecimal getLat() {
        return getLocation().getLat();
    }
    
    /**
     * Lng
     * @return
     */
    public BigDecimal getLng() {
        return getLocation().getLng();
    }
    
    /**
     * address_components
     * <pre>
     * 個別の住所コンポーネントを格納した配列です
     * </pre>
     * @return
     */
    public List<GeocoderAddressComponent> getAddressComponents() {
        return getGeocoderResult().getAddressComponents();
    }
    
    /**
     * AddressComponent
     * <pre>
     * 政治的エンティティを示します。通常、このタイプは一部の民政のポリゴンを示します。
     * </pre>
     * @param type
     * @return
     */
    public GeocoderAddressComponent getAddressComponent(String... types) {
        List<GeocoderAddressComponent> gacList = getGeocoderResult().getAddressComponents();
        
        
        if(gacList.isEmpty()) return null;
        
        for(GeocoderAddressComponent gac: gacList) {
            List<String> typeList = gac.getTypes();
            
            if(typeList.size() == types.length && typeList.containsAll(Arrays.asList(types))) {
                return gac;
            }
        }
        
        return null;
    }
    
    /**
     * country Long_name
     * <pre>
     * 国政エンティティを示します。一般的に、ジオコーダーから返されるタイプの中で最上位に位置付けられます。
     * </pre>
     * @return 
     */
    public String getCountryLongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("country", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * country short_name
     * <pre>
     * 国政エンティティを示します。一般的に、ジオコーダーから返されるタイプの中で最上位に位置付けられます。
     * </pre>
     * @return 
     */
    public String getCountryShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("country", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * administrative_area_level_1 Long_name
     * <pre>
     * 国レベルの下の第 1 次行政エンティティを示します。米国内の場合、この行政レベルは州です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel1LongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_1", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * administrative_area_level_1 short_name
     * <pre>
     * 国レベルの下の第 1 次行政エンティティを示します。米国内の場合、この行政レベルは州です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel1ShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_1", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * administrative_area_level_2 Long_name
     * <pre>
     * 国レベルの下の第 2 次行政エンティティを示します。米国内の場合、この行政レベルは郡です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel2LongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_2", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * administrative_area_level_2 short_name
     * <pre>
     * 国レベルの下の第 2 次行政エンティティを示します。米国内の場合、この行政レベルは郡です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel2ShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_2", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * administrative_area_level_3 Long_name
     * <pre>
     * 国レベルの下の第 3 次行政エンティティを示します。米国内の場合、この行政レベルは郡です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel3LongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_3", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * administrative_area_level_3 short_name
     * <pre>
     * 国レベルの下の第 3 次行政エンティティを示します。米国内の場合、この行政レベルは郡です。この行政レベルがない国もあります。
     * </pre>
     * @return 
     */
    public String getAdministrativeAreaLevel3ShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("administrative_area_level_3", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * locality Long_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getLocalityLongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("locality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * locality short_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getLocalityShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("locality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * ward Long_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getWardLocalityLongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("ward", "locality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * ward locality short_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getWardLocalityShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("ward", "locality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * sublocality Long_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getSublocalityLongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("sublocality_level_1", "sublocality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * sub locality short_name
     * <pre>
     * 都市や町などの政治的エンティティの地域を示します。
     * </pre>
     * @return 
     */
    public String getSublocalityShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("sublocality_level_1", "sublocality", "political");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }
    
    /**
     * postal_code Long_name
     * <pre>
     * 対象の国内で郵便物の宛先として使用される郵便番号を示します。
     * </pre>
     * @return 
     */
    public String getPostalCodeLongName() {
        GeocoderAddressComponent addressComp = getAddressComponent("postal_code");
        
        if(addressComp == null) return null;
        
        return addressComp.getLongName();
    }
    
    /**
     * postal_code short_name
     * <pre>
     * 対象の国内で郵便物の宛先として使用される郵便番号を示します。
     * </pre>
     * @return 
     */
    public String getPostalCodeShortName() {
        GeocoderAddressComponent addressComp = getAddressComponent("postal_code");
        
        if(addressComp == null) return null;
        
        return addressComp.getShortName();
    }

    public GeocodeResponse getGeocoderResponse() {
        return geocoderResponse;
    }

    public void setGeocoderResponse(GeocodeResponse geocoderResponse) {
        this.geocoderResponse = geocoderResponse;
    }

    public GeocoderStatus getStatus() {
        return status;
    }

    public void setStatus(GeocoderStatus status) {
        this.status = status;
    }

}
