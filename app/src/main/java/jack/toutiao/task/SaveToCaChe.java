package jack.toutiao.task;

import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.text.LoginFilter;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jack.toutiao.bean.NewsEntity;
import jack.toutiao.cache.ACache;
import jack.toutiao.cache.DiskLruCache;
import jack.toutiao.util.MyApplication;
import jack.toutiao.util.Utils;

/**
 * Created by 黄文杰 on 2016/7/18.
 */


public class SaveToCaChe extends AsyncTask<Void, Void, Void> {


    private NewsEntity newsEntity;
    private String url;
    private ACache cache;
    private String images_url;
    private Bitmap bitmap;
    private String bitmap_1;
    private DiskLruCache diskLruCache;
    private int maxSize = 20 * 1024 * 1024;

    public SaveToCaChe(String url, NewsEntity newsEntity) {
        this();
        this.url = url;
        this.newsEntity = newsEntity;
        Log.d("Tak", url);
    }

    public SaveToCaChe(String images_url, Bitmap bitmap) {
        this();
        this.images_url = images_url;
        this.bitmap = bitmap;
        Log.d("images_url", this.images_url);
    }

    public SaveToCaChe() {
        cache = ACache.get(MyApplication.getContext());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("getBitmap", String.valueOf(bitmap));

        Log.d("getNewsEntity", String.valueOf(newsEntity));
        if (newsEntity != null) {
            saveNewsEntityToCache();
        }
        if (bitmap != null) {
            saveBitmapToCache();
        }


        return null;
    }

    /**
     * 将一个可序列化对象newsEntity写入缓存
     */
    private void saveNewsEntityToCache() {
        cache.put(url, newsEntity);
    }


    /**
     * 将一张bitmap图片写入缓存
     */
    private void saveBitmapToCache() {
        Log.d("saveBitmapToCache", images_url);
        cache.put(images_url, bitmap);
        Log.d("setBitmap", String.valueOf(bitmap));
        initDiskLruCache();

    }

    /**
     * 将一张图片写入内存缓存
     */
    private void saveBitmapToLruCache() {
        MyApplication.getLruCache().put(images_url, bitmap);
    }

    /**
     * 初始化DiskLruCache
     */
    private void initDiskLruCache() {
        try {
            Log.d(getClass().getName(), "initDiskLruCache");
            File cacheDir = Utils.getDiskLruCacheDir(MyApplication.getContext(), bitmap_1);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            int versionCode = Utils.getAppVersionCode(MyApplication.getContext());

            diskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, maxSize);

            saveDataToDiskLruCache();
            //saveNewsToDiskLruCache();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToDiskLruCache() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String key = Utils.getStringByMD5(images_url);
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                    if (snapshot != null) {
                        Log.d("Tauu","已存在图片");
                        return;
                    }
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        boolean isSuccessful = Utils.getBitmapFromNetWorkAndSaveToDiskLruCache(images_url, outputStream);
                        if (isSuccessful) {
                            editor.commit();
                            Log.d("Tauu","请求成功");
                        } else {
                            editor.abort();
                        }
                        diskLruCache.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void saveNewsToDiskLruCache() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String key = Utils.getStringByMD5(url);
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        boolean isSuccessful = Utils.getNewsFromNetWorkAndSaveToDiskLruCache(url, outputStream);
                        if (isSuccessful) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                        diskLruCache.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}


