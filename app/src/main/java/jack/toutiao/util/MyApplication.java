package jack.toutiao.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by 黄文杰 on 2016/7/7.
 */
public class MyApplication extends Application {

    private static Context context;
    //内存缓存对象 缓存bitmap图片
    private static LruCache<String, Bitmap> lruCache;
    //内存缓存的大小
    private final static int CACHESIZE = 4 * 1024 * 1024; // 4MiB
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        lruCache = new LruCache<String, Bitmap>(CACHESIZE){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static Context getContext() {
        return context;
    }
    /**
     * 通过该方法全局调用内存缓存类对象
     * @return 内存缓存类对象
     */
    public static LruCache<String, Bitmap> getLruCache() {
        return lruCache;
    }
}

