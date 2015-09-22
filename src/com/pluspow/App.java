package com.pluspow;

import com.pluspow.enums.Lang;


public class App {
    
    /** アプリケーション名 */
    public static final String APP_DISPLAY_NAME = "Pluspow";
    
    public static final Lang APP_BASE_LANG = Lang.en;
    
    /** メール送信元アドレス */
    public static final String EMAIL_FROM_ADDRESS = "info@pluspow.com";
    
    // ---------------------------------------------------------------------------
    // GOOGLE PROJECT 定数
    // ---------------------------------------------------------------------------
    /** アプリケーション名(Google API 用) */
    public static final String GOOGLE_API_APPLICATION_NAME = "Pluspow";
    
    /** 翻訳APIの公開キー */
    public static final String GOOGLE_TRANS_API_PUBLIC_KEY = "AIzaSyBrVk6ho9rDWIcvbl6sCibhmUYv_MezFAk";
    
    
    
    // ---------------------------------------------------
    // 画面表示
    // ---------------------------------------------------
    public static final int SPOT_LIST_LIMIT = 10;
    
    public static final int SPOT_LIST_BY_ACTIVITY_LIMIT = 10;
    
    public static final int SPOT_ITEM_LIST_LIMIT = 3;
    
    public static final int SPOT_ITEM_LIST_BY_TYPE_LIMIT = 10;
    
    public static final int SPOT_HOWTO_LIST_LIMIT = 10;
    
    public static final int TRANS_HISTORY_LIST_LIMIT = 20;
    
    // ---------------------------------------------------
    // 登録制限
    // ---------------------------------------------------
    public static final int CONTENTS_CATCH_COPY_STRING_SIZE = 200;
    // 登録できるハウツーステップ数
    public static final int HOWTO_STEP_COUNT_LIMIT = 10;

}
