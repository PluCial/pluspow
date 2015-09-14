package com.pluspow.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import com.pluspow.App;
import com.pluspow.enums.Lang;
import com.pluspow.exception.TransException;
import com.pluspow.model.TextRes;
import com.pluspow.utils.Utils;

public class TransService {
//    
//    /** History DAO */
//    private static final TransHistoryDao historyDao = new TransHistoryDao();
//    
//    /** TransAccumulationDao DAO */
//    private static final TransHistoryDao accDao = new TransHistoryDao();
    
    /**
     * 機械翻訳
     * @param contents
     * @return
     * @throws Exception 
     */
    public static String machineTrans(
            Lang baseLang,
            Lang transLang, 
            List<? extends TextRes> transContentsList) throws Exception {
        
        if(transContentsList == null || transContentsList.size() <= 0) {
            throw new TransException("翻訳するコンテンツはありません。");
        }
        
        if(baseLang == null || transLang == null || baseLang == transLang) {
            throw new IllegalArgumentException("言語指定が正しくありません");
        }
        
        // 通常モード
        String transSrc = "";
        
        for(TextRes tc: transContentsList) {
            
            // 翻訳対象の場合
            if(tc.getRole().isTransTarget()) {
                String content = tc.getContent().getValue();
                content = Utils.changeIndentionToHtml(content); // 改行コードを<br /> に変換して翻訳する

                transSrc = transSrc + "<div id=\"" + tc.getKey().getName() + "\">" + content +  "</div>";
            }
        }
        
        // 翻訳処理
        String transResult = googleTrans(transSrc, baseLang, transLang);
        
        return transResult;
    }
    
    
    /**
     * Google 翻訳
     * @param transSrc
     * @return
     * @throws IOException 
     */
    private static String googleTrans(String source, Lang baseLang, Lang targetLang) throws Exception {
        
        // 翻訳Translate の生成
        Translate translate = new Translate.Builder(new NetHttpTransport(), new JacksonFactory(), null)
        .setGoogleClientRequestInitializer(new TranslateRequestInitializer(App.GOOGLE_TRANS_API_PUBLIC_KEY))
        .setApplicationName(App.GOOGLE_API_APPLICATION_NAME)
        .build();
        
        // 翻訳
        List<String> qList = new ArrayList<String>();
        qList.add(source);
        TranslationsListResponse response = translate.translations()
                .list(qList, targetLang.getLangKey())
                .setSource(baseLang.getLangKey())
                .setFormat("html")
                .execute();
        
        // 翻訳結果の取得
        List<TranslationsResource> resourceList = response.getTranslations();
        TranslationsResource resource = resourceList.get(0);
        
        return resource.getTranslatedText();
    }

}
