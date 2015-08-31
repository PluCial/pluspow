package com.pluspow.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    
    /**
     * 現在の日付・時刻から指定の【年数】を加算・減算した結果を返します。
     * @param addYera 加算・減算する年数
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addYera(int addYera, TimeZone timeZone){
        return add(null,timeZone, addYera,0,0,0,0,0,0);
    }
    /**
     * 現在の日付・時刻から指定の【月数】を加算・減算した結果を返します。
     * @param addMonth 加算・減算する月数
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addMonth(int addMonth, TimeZone timeZone){
        return add(null,timeZone, 0,addMonth,0,0,0,0,0);
    }
    /**
     * 現在の日付・時刻から指定の【日数】を加算・減算した結果を返します。
     * @param addDate 加算・減算する日数
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addDate(int addDate, TimeZone timeZone){
        return add(null,timeZone, 0,0,addDate,0,0,0,0);
    }
    /**
     * 現在の日付・時刻から指定の【時間】を加算・減算した結果を返します。
     * @param addHour 加算・減算する時間
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addHour(int addHour, TimeZone timeZone){
        return add(null,timeZone, 0,0,0,addHour,0,0,0);
    }
    /**
     * 現在の日付・時刻から指定の【分】を加算・減算した結果を返します。
     * @param addMinute 加算・減算する分
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addMinute(int addMinute, TimeZone timeZone){
        return add(null,timeZone, 0,0,0,0,addMinute,0,0);
    }
    /**
     * 現在の日付・時刻から指定の【秒】を加算・減算した結果を返します。
     * @param addSecond 加算・減算する秒
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar addSecond(int addSecond, TimeZone timeZone){
        return add(null,timeZone, 0,0,0,0,0,addSecond,0);
    }
    /**
     * 現在の日付・時刻から指定の時間量を加算・減算した結果を返します。
     * 年、月、日、時間、分、秒、ミリ秒の各時間フィールドに対し、
     * 任意の時間量を設定できます。
     * たとえば、現在の日付時刻から 10 日前を計算する場合は以下となります。
     * Calendar cal = add(null,0,0,-10,0,0,0,0);
     * 
     * 各時間フィールドの値がその範囲を超えた場合、次の大きい時間フィールドが
     * 増分または減分されます。
     * たとえば、以下では1時間と5分進めることになります。
     * Calendar cal = add(null,0,0,0,0,65,0,0);
     * 
     * 各時間フィールドに設定する数量が0の場合は、現在の値が設定されます。
     * java.util.GregorianCalendarの内部処理では以下の分岐を行っている。
     *     if (amount == 0) {
     *         return;
     *     }
     *     
     * @param cal 日付時刻の指定があればセットする。
     *     nullの場合、現在の日付時刻で新しいCalendarインスタンスを生成する。
     * @param addYera 加算・減算する年数
     * @param addMonth 加算・減算する月数
     * @param addDate 加算・減算する日数
     * @param addHour 加算・減算する時間
     * @param addMinute 加算・減算する分
     * @param addSecond 加算・減算する秒
     * @param addMillisecond 加算・減算するミリ秒
     * @return    計算後の Calendar インスタンス。
     */
    public static Calendar add(Calendar cal,TimeZone timeZone,
                               int addYera,int addMonth,int addDate,
                               int addHour,int addMinute,int addSecond,
                               int addMillisecond){
        if (cal == null) {
            cal = Calendar.getInstance(timeZone);
        }
        cal.add(Calendar.YEAR, addYera);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.DATE, addDate);
        cal.add(Calendar.HOUR_OF_DAY, addHour);
        cal.add(Calendar.MINUTE, addMinute);
        cal.add(Calendar.SECOND, addSecond);
        cal.add(Calendar.MILLISECOND, addMillisecond);
        return cal;
    }


    /**
     * 指定されたパターン文字列の文字列を Date オブジェクトにして返します。
     * Date オブジェクトとして有効でない場合は null を返します。
     *
     * @param value 日付を表す文字列
     * @param format 日付を表す文字列のパターン書式 (yyyy/MM/dd など)
     * @return 日付を表す文字列の Date オブジェクト
     * @throws ParseException 
     */
    public static Date toDate(String value, String format, TimeZone timeZone, Locale locale) {

        // 日付フォーマットを作成
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        dateFormat.setTimeZone(timeZone);

        // 日付の厳密チェックを指定
        dateFormat.setLenient(false);

        try {
            // 日付値を返す
            return dateFormat.parse(value);
        } catch ( ParseException e ) {
            // 日付値なしを返す
            return null;
        } finally {
            dateFormat = null;
        }
    }
    
    public static String dateToString(String format, TimeZone timeZone, Locale locale) {
        return dateToString(new Date(), format, timeZone, locale);
    }
    
    public static String dateToString(Date date, String format, TimeZone timeZone, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        dateFormat.setTimeZone(timeZone);
        
        return dateFormat.format(date);
    }
    
    public static String dateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        
        return dateFormat.format(date);
    }
}
