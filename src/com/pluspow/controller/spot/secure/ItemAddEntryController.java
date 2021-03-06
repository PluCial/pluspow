package com.pluspow.controller.spot.secure;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.StringUtil;

import com.pluspow.enums.SpotActivity;
import com.pluspow.model.Client;
import com.pluspow.model.Spot;
import com.pluspow.service.ItemService;
import com.pluspow.service.MemcacheService;
import com.pluspow.utils.PathUtils;

public class ItemAddEntryController extends BaseController {

    @Override
    protected Navigation execute(Client client, Spot spot) throws Exception {
        
        // 入力チェック
        if (!isPost() || !validate()) {
            
            return forward("/spot/secure/itemAdd.jsp");
        }
        
        // リクエストパラメーターの取得
        String itemName = asString("itemName");
        String price = asString("price");
        String activity = asString("activity");
        String detail = asString("detail");
        boolean dutyFree = StringUtil.isEmpty(asString("dutyFree")) ? false : true;
        
        FileItem fileItem = requestScope("uploadImage");
        int imageX = asInteger("imageX");
        int imageY = asInteger("imageY");
        int imageWidth = asInteger("imageWidth");
        int imageHeight = asInteger("imageHeight");
        
        // アイテムの追加
        ItemService.add(
            spot, 
            SpotActivity.valueOf(activity), 
            Double.valueOf(price), 
            dutyFree, 
            itemName, 
            detail, 
            fileItem,
            imageX, imageY, imageWidth, imageHeight);
        
        // キャッシュクリア(スポットの最大利用金額を更新するため、すべての言語のキャッシュをクリア)
        MemcacheService.deleteSpotAll(spot);
        
        return redirect(PathUtils.spotPage(spot.getSpotId(), spot.getBaseLang(), isLocal(), true));
    }
    
    /**
     * バリデーション
     * @return
     */
    private boolean validate() {
        Validators v = new Validators(request);
        
        // アイテム名
        v.add("itemName", 
            v.required("アイテム名を入力してください。")
        );
        
        // アイテムタイプ
        v.add("activity", 
            v.required("アイテムの種類を選択してください。")
        );
        
        // 値段
        v.add("price", 
            v.required("金額を入力してください。<br />無料の場合は ¥0 と入力してください。"),
            v.integerType("金額には半角の数字を入力してください。")
        );

        // コンテンツ
        v.add("detail", 
            v.required("アイテムの説明を入力してください。")
        );
        
        // 画像
        v.add("uploadImage", v.required("アイテム画像を添付してください。"));
        
        // 画像情報
        v.add("imageX", v.required());
        v.add("imageY", v.required());
        v.add("imageWidth", v.required());
        v.add("imageHeight", v.required());

        return v.validate();
    }
}
