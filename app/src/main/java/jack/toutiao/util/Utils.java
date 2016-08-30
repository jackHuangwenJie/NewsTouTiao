package jack.toutiao.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import jack.toutiao.util.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 黄文杰 on 2016/7/20.
 */
public class Utils {
    /**
     * 获取DiskLruCache的缓存文件夹
     * 注意第二个参数dataType
     * DiskLruCache用一个String类型的唯一值对不同类型的数据进行区分.
     * 比如bitmap,object等文件夹.其中在bitmap文件夹中缓存了图片.
     *
     * 缓存数据的存放位置为:
     * /sdcard/Android/data//cache
     * 如果SD卡不存在时缓存存放位置为:
     * /data/data//cache
     *
     */
    public static File getDiskLruCacheDir(Context context, String dataType) {
        String dirPath;
        File cacheFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            dirPath=context.getExternalCacheDir().getPath();
        } else {
            dirPath=context.getCacheDir().getPath();
        }
        cacheFile=new File(dirPath+File.separator+dataType);
        return cacheFile;
    }



    /**
     * 获取APP当前版本号
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context){
        int versionCode=1;
        try {
            String packageName=context.getPackageName();
            PackageManager packageManager=context.getPackageManager();
            PackageInfo packageInfo=packageManager.getPackageInfo(packageName, 0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 将字符串用MD5编码.
     * 比如在改示例中将url进行MD5编码
     */
    public static String getStringByMD5(String string) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(string.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(string.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * 从网络获取图片且保存至SD卡中的缓存
     */
    public static boolean getBitmapFromNetWorkAndSaveToDiskLruCache(String imageUrl,OutputStream outputStream){
        boolean isSuccessful=false;
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            try {
                url=new URL(imageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            httpURLConnection=(HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                System.out.println("请求成功");
                isSuccessful=true;
                return true;
            }
            else
            {
                isSuccessful=false;
                System.out.println("图片请求失败");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccessful;
    }

    /**
     * 从网络获取新闻数据并保存至SD卡中
     */
    public static boolean getNewsFromNetWorkAndSaveToDiskLruCache(String Url,OutputStream outputStream){
        boolean isSuccessful=false;
        URL url=null;
        HttpURLConnection httpURLConnection=null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            try {
                url=new URL(Url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            httpURLConnection=(HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                System.out.println("储存成功");
                isSuccessful=true;
                return true;
            }
            else
            {
                isSuccessful=false;
                System.out.println("储存失败");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccessful;
    }
}

