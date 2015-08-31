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

	    //--------------------------------------------
	    // トップ
	    //--------------------------------------------
	    addRouting(
            "/",
            "/pub/info/features");
	    
	    addRouting(
            "/index",
            "/pub/info/features");
	  
// OPEN時に切り替え
//	    addRouting(
//            "/",
//            "/pub/bl/index");
//        
//        addRouting(
//            "/index",
//            "/pub/bl/index");
	    
	    // infoの設定
	    setInfo();
	    
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
	
	/**
	 * INFO
	 */
	private void setInfo() {
	    // ABOUT
        addRouting(
                "/info/about",
                "/pub/info/about");
        
        // 利用規約
        addRouting(
                "/info/agreement",
                "/pub/info/agreement");
        
        // お問い合わせ
        addRouting(
                "/info/contact",
                "/pub/info/contact");
        addRouting(
            "/info/contactMsgSend",
            "/pub/info/contactMsgSend");
        addRouting(
            "/info/contactEnd",
            "/pub/info/contactEnd");
        
        // 報道関係者各位へ
        addRouting(
                "/info/coverage",
                "/pub/info/coverage");
        
        // サービス特徴
        addRouting(
                "/info/features",
                "/pub/info/features");
        
        // パートナーシップ
        addRouting(
                "/info/partnership",
                "/pub/info/partnership");
        
        // 利用料金とプラン
        addRouting(
                "/info/price",
                "/pub/info/price");
        
        // プライバシーポリシー
        addRouting(
                "/info/privacy",
                "/pub/info/privacy");
        
        // プライバシーポリシー
        addRouting(
                "/info/langs",
                "/pub/info/langs");
	}

}
