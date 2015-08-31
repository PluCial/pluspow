package com.pluspow.utils;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InfoUtils {
    
    /**
     * HTML内の絶対サイズ指定を削除
     * @param document
     * @param tagType
     */
    public static void deleteWidthAndHeightStyle(Document document, String tagType) {
        Elements tagElements = document.getElementsByTag(tagType);
        
        for(Element element : tagElements) {
            Attributes attributes = element.attributes();
            
            for(Attribute attribute: attributes) {
                if(attribute.getKey().equals("style")){
                    // スタイル要素リスト
                    String[] items = attribute.getValue().trim().split(";");
                    String newValue = "";
                    for(String item: items){

                        if(!item.contains("width:") && !item.contains("height:")){
                            newValue = newValue.concat(item).concat(";");
                        }
                    }
                    
                    attribute.setValue(newValue);
                }
            }
        }
    }
    
    /**
     * CSSの変更
     * @param document
     * @return
     */
    public static String changeCss(Document document) {
        
        Elements tagElements = document.getElementsByTag("style");
        Element styleElement = tagElements.get(0);
        String style = styleElement.data();
        String newStyles = "";
        
        String[] items = style.split("}");
        for(String item: items) {
            
            String[] tmp =  item.split("\\{");
            String propertyKey = tmp[0];
            String propertyAllStyle = tmp[1];
            
            String[] propertys = propertyAllStyle.split(";");
            
            String elmStyle = propertyKey + "{";
            
            for(String property: propertys) {
                if(property.trim().toLowerCase().indexOf("font-size") < 0 && property.trim().toLowerCase().indexOf("font-family") < 0) {
                    elmStyle = elmStyle + property + ";";
                }
            }
            
            newStyles = newStyles + "#article-content " + elmStyle + "}";
        }
        
        return newStyles + "#article-content img{max-width:90%;border:1px #aaa solid;box-shadow:2px 2px 2px 2px #aaa;}#article-content h2{font-size: 1.3em;margin-top:2.5em;margin-bottom:0.5em;padding: 18px 0;border-top: 1px solid #000;border-bottom: 1px solid #000;}#article-content h3{padding: 10px 0 10px 10px;border-left: 5px solid #555;margin-top: 1em;margin-bottom: 0.5em;}";
    }
    
    /**
     * 文字の切り出し
     * @param str
     * @param num
     * @return
     */
    public static String subContentsString(String str, int num) {

        if(str == null) return "";

        if(str.length() > num) {
            return str.substring(0, num) + "....";
        }

        return str;
    }

}
