package ap.ky.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WeatherDataFetcher {
    String TAG = "WeatherDataFetcher";
    static WeatherDataFetcher weatherDataFetcher = null;
    String DAILYDATA = "F-C0032-001";
    String WEEKLYDATA = "F-C0032-005";
    String dataurl = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/%s?authorizationkey=%s";

    static File file = Environment.getExternalStorageDirectory();
    public static String downloadPath = file.getPath() + File.separator + "satellite";
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
    Record getDailyData(String apiKey){
        String reqUrl =String.format(dataurl, DAILYDATA,apiKey);
        String weatherData = sentHttpRequestGet(reqUrl);
        try {
            JSONObject jsonObject = new JSONObject(weatherData);
            JSONObject record = jsonObject.getJSONObject("records");
            Gson gson = new Gson();
            Record weatherRecord = gson.fromJson(record.toString(),Record.class);
            Log.e(TAG,"wether record " + weatherRecord.datasetDescription);
            return weatherRecord;
        }catch (Exception ex){
            Log.e(TAG,"getDailyData error " + ex.toString());
        }
        return null;
    }
}
