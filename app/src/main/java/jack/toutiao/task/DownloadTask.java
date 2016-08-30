package jack.toutiao.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

import jack.toutiao.bean.NewsEntity;
import jack.toutiao.cache.ACache;
import jack.toutiao.util.Http;

/**
 * Created by 黄文杰 on 2016/7/13.
 */
public class DownloadTask extends AsyncTask<String ,Void,Bitmap> {

    private ImageView imageView;
    private String [] image_url;
    public DownloadTask(ImageView imageView) {
        this.imageView = imageView;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        image_url = strings;
        Bitmap bitmap = null;
        for (int i = 0; i < strings.length; i++) {
            try {
                bitmap = Http.getImage(strings[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        for (int i = 0; i < image_url.length; i++) {
            Log.d(getClass().getName(), image_url[i]);
            new SaveToCaChe(image_url[i],bitmap).execute();
            Log.d("url",image_url[i]);
        }
    }

}
