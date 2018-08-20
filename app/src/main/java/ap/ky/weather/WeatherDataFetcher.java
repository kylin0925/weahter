package ap.ky.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WeatherDataFetcher {
    String TAG = "WeatherDataFetcher";
    static WeatherDataFetcher weatherDataFetcher = null;
    public static WeatherDataFetcher getInstance(){
        if(weatherDataFetcher == null)
            return new WeatherDataFetcher();
        else
            return weatherDataFetcher;
    }
    Bitmap getSatelliteImage(String url){
        Bitmap bitmap=null;
        Log.e(TAG,"getSatelliteImage");
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        }catch (Exception ex){
            Log.e(TAG,"error " + ex);
        }
        return bitmap;
    }
    String sentHttpRequestGet(String url){
        String result = "";
        try {
            URL u = new URL(url);
            HttpURLConnection h = (HttpURLConnection) u.openConnection();
            //InputStream in = new BufferedInputStream(h.getInputStream());
            //readStream(in);
            int len = 0;

            BufferedReader br = new BufferedReader(new InputStreamReader(h.getInputStream()));

            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                result +=tmp;
            }
            h.disconnect();
        }catch (Exception e){
            Log.e(TAG,"exception : " + e.toString());
        }
        return result;
    }
}
