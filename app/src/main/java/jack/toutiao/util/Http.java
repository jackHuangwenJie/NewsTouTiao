package jack.toutiao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 黄文杰 on 2016/7/7.
 */
public class Http {

    public static String getJsonString(String urlAddress) throws IOException {
        URL url = null;
        HttpURLConnection connection = null;
      //  if (Tools.isNetConnected()) {
            try {
                url = new URL(urlAddress);
                connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == 200) {
                    InputStream in = connection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = in.read(date)) != -1) {
                        byteArrayOutputStream.write(date, 0, len);
                    }
                    return new String(byteArrayOutputStream.toByteArray());
                } else {
                    throw new IOException("Network Error - response code: " + connection.getResponseCode());
                }


            } catch (IOException e) {

            }

      //  }

        return null;
    }



    public static Bitmap getImage(String urlAddr) throws IOException {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
       // if (Tools.isNetConnected()) {
            try {
                url = new URL(urlAddr);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] date = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(date)) != -1) {
                        byteArrayOutputStream.write(date, 0, len);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                    return bitmap;
                } else {
                    throw new IOException("Network Error - response code: " + httpURLConnection.getResponseCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
      //  }
        return null;
    }

}