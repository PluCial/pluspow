package com.pluspow.controller;

import org.slim3.controller.router.RouterImpl;

/**
 * 公開部分ルーティング
 * addRoutingメソッドの順番変更は禁止！
 * @author takahara
 *
 */
public class AppRouter extends RouterImpl {

	public AppRouter() {
	    
	    // SPOT TOP
        addRouting(
                "/l-{lang}/",
                "/?lang={lang}");
	    
//	    setSpotEdit();
	    setSpot();
	    
	}
	
	/**
     * スポット
     */
    private void setSpot() {
        // SPOT TOP
        addRouting(
                "/\\+{spotId}",
                "/spot/index?spotId={spotId}");
        addRouting(
                "/\\+{spotId}/",
                "/spot/index?spotId={spotId}");
        addRouting(
            "/\\+{spotId}/l-{lang}/",
            "/spot/index?spotId={spotId}&lang={lang}");
        
        // ITEM LIST
        addRouting(
            "/\\+{spotId}/l-{lang}/itemList",
            "/spot/itemList?spotId={spotId}&lang={lang}");
        
        // ITEM
        addRouting(
            "/\\+{spotId}/l-{lang}/item/{itemId}",
            "/spot/item?spotId={spotId}&lang={lang}&itemId={itemId}");
        
        // howto list
        addRouting(
                "/\\+{spotId}/howtos",
                "/spot/howtoList?spotId={spotId}");
        addRouting(
            "/\\+{spotId}/l-{lang}/howtos",
            "/spot/howtoList?spotId={spotId}&lang={lang}");
        
        
        // howto
        addRouting(
                "/\\+{spotId}/howto/{howtoId}",
                "/spot/howto?spotId={spotId}&howtoId={howtoId}");
        addRouting(
            "/\\+{spotId}/l-{lang}/howto/{howtoId}",
            "/spot/howto?spotId={spotId}&howtoId={howtoId}&lang={lang}");
    }

}
