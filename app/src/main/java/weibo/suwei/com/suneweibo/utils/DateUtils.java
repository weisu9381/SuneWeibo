package weibo.suwei.com.suneweibo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by suwei on 2017/4/4.
 */

public class DateUtils {

    public static final String WeiBo_ITEM_DATE_FORMAT = "EEE MMM d HH:mm:ss Z yyyy";

    /**
     * 将Date类型转换为 日期String字符串
     */
    public static String formDate(Date date,String type){
        //SimpleDateFormat是 线程不安全的，不要把它当做 全局变量来用
        //每次都要新建SimpleDateFormat其实也不是很浪费内存，当然可以用 ThreadLocal
        SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.US);
        return sdf.format(date);
    }
    /**
     * 将 String类型 转换为 Date日期
     */
    public static Date parseDate(String dateStr,String type){
        SimpleDateFormat sdf = new SimpleDateFormat(type,Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
