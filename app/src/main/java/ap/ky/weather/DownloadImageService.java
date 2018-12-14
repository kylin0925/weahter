package ap.ky.weather;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DownloadImageService extends Service {
    final String url = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/%s?authorizationkey=%s&format=json";
    String TAG = "DownloadImageService";
    String apikey = "";
    String DataID = "O-B0028-003";
    ServiceBinder mBinder = new ServiceBinder();
    public DownloadImageService() {
        Log.d(TAG,"DownloadImageService()");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand ");
        apikey = intent.getStringExtra("APIKEY");
        Log.d(TAG,"apikey :" + apikey);
        startDownloadTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
    class ImageData{
        String obsTime;
        String uri;
    }
    ImageData parserJSON(String text){
        ImageData imageData = new ImageData();
        try {
            JSONObject jsonObject = new JSONObject(text);
            JSONObject cwbopendata = jsonObject.getJSONObject("cwbopendata");
            JSONObject dataset = cwbopendata.getJSONObject("dataset");
            JSONObject resource = dataset.getJSONObject("resource");
            String uri = resource.getString("uri");
            String obsTime = dataset.getJSONObject("time").getString("obsTime");
            Log.d(TAG,"uri " + uri + " obsTime " + obsTime);
            imageData.obsTime = obsTime.replace(':','_');
            imageData.uri = uri;
        }catch (Exception ex){
            Log.e(TAG,"error " + ex.toString());
        }
        return imageData;
    }
    void startDownloadTask(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(url!=null && url!="") {
                    WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
                    String fullurl = String.format(url, DataID, apikey);
                    String jsonData = weatherDataFetcher.sentHttpRequestGet(fullurl);
                    Log.d(TAG, "jsonData " + jsonData);
                    ImageData imageData =parserJSON(jsonData);
                    Bitmap bitmap = weatherDataFetcher.getSatelliteImage(imageData.uri);

                    String output = String.format("%s/%s_%s.jpg",WeatherDataFetcher.downloadPath,
                            DataID,imageData.obsTime);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(output);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,90,fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
    class ServiceBinder extends Binder{
        public void startDownload(){
            Log.d(TAG,"start download");
        }
    }
}
