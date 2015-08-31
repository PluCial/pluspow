package com.pluspow.utils;

import java.util.List;

public class Utils {
    
    /**
     * List型の引数がnullまたは空であればtrueを返します
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * List型の引数がnullでも空でもなければtrueを返却します
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }
    
    /**
     * 
     * @param content
     * @return
     */
    public static String getJspDisplayString(String content) {
        if(content == null || content.trim().length() <= 0) return "";
        
        // HTMLエスケープ処理
        String newdata = htmlEscape(content);
        
        return changeIndentionToHtml(newdata);
    }
    
    /**
     * 文字列を適切なHTMLに変換
     * @param data
     * @return
     */
    public static String changeIndentionToHtml(String content) {
        if(content == null || content.trim().length() <= 0) return null;
        
        String newContent = content.replaceAll("\\r\\n|\\n\\r|\\r|\\n", "<br />");
        
        return newContent;
    }
    
    /**
     * 文字列を適切なHTMLに変換
     * @param data
     * @return
     */
    public static String changeBrToTextIndention(String content) {
        if(content == null || content.trim().length() <= 0) return null;
        
        String newContent = content.replaceAll("<br />|<br>|<BR />|<BR>", "\n");
        
        return newContent;
    }
    
    /**
     * 文字列の改行コードを除去する
     * @param data
     * @return
     */
    public static String clearTextIndention(String content) {
        if(content == null || content.trim().length() <= 0) return null;
        
        String newContent = content.replaceAll("\\r\\n|\\n\\r|\\r|\\n", "");
        
        return newContent;
    }
    
    /**
     * 改行の統一(HTMLの改行、改行コードをすべて\nに統一する)
     * @param content
     * @return
     */
    public static String unityIndention(String content) {
        
        if(content == null || content.trim().length() <= 0) return null;
        
        // 改行
        String newContent = content.replaceAll("\\r\\n|\\n\\r|\\r", "\n");
        
        return newContent;
    }
    
    /**
     * 文字の切り取り
     * @param str
     * @param num
     * @param correctionString
     * @return
     */
    public static String cutString(String str, int num, String correctionString) {

        if(str == null || str.trim().length() <= 0) return null;

        if(str.length() > num) {
            
            String cutString = str.substring(0, num);
            
            return correctionString == null ? cutString : cutString + correctionString;
        }

        return str;
    }
    
    /**
     * <p>[概 要] HTMLエスケープ処理</p>
     * <p>[詳 細] </p>
     * <p>[備 考] </p>
     * @param  str 文字列
     * @return HTMLエスケープ後の文字列
     */
    public static String htmlEscape(String str){
        if(str == null || str.trim().length() <= 0) return null;
        
        StringBuffer result = new StringBuffer();
        for(char c : str.toCharArray()) {
            switch (c) {
            case '&' :
                result.append("&amp;");
                break;
            case '<' :
                result.append("&lt;");
                break;
            case '>' :
                result.append("&gt;");
                break;
            case '"' :
                result.append("&quot;");
                break;
            case '\'' :
                result.append("&#39;");
                break;
//            case ' ' :
//                result.append("&nbsp;");
//                break;
            default :
                result.append(c);
                break;
            }
        }
        return result.toString();
    }

}
