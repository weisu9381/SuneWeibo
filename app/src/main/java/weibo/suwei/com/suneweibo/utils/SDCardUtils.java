package weibo.suwei.com.suneweibo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * SDCard相关的辅助类
 * 需要添加权限
 * Created by suwei on 2017/4/6.
 */

public class SDCardUtils {

    /**
     * 判断SDCard是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取 SDCard 路径, 绝对路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 读取 文件 内容
     */
    public static String get(Context context, String fileDir, String fileName) {
        StringBuffer sb;
        File file = new File(fileDir, fileName);
        try {
            BufferedReader bufr = new BufferedReader(new FileReader(file));
            String line = null;
            sb = new StringBuffer();
            while ((line = bufr.readLine()) != null) {
                sb.append(line);
            }
            bufr.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 写入文件内容
     */
    public static void put(Context context,String fileDir,String fileName,String content){
        //file1是 建立 目录
        File file1 = new File(fileDir);
        //如果 file1 不存在的话，就新建
        if(!file1.exists()){
            //创建 多级 目录
            file1.mkdirs();
        }
        //file2是 建立 在 目录file1下的 fileName文件
        File file2 = new File(file1,fileName);
        try {
            //如果file2不存在,则创建,如果存在就不创建
            file2.createNewFile();
            if(isSDCardEnable()){
                FileOutputStream outputStream = new FileOutputStream(file2);
                byte[] buf = content.getBytes();
                outputStream.write(buf);
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
