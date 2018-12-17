package ap.ky.weather;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONObject;

import java.io.FileOutputStream;
import android.os.Handler;




public class DownloadImageService extends IntentService {
    final String url = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/%s?authorizationkey=%s&format=json";
    String TAG = "DownloadImageService";
    String apikey = "";
    String DataID = "O-B0028-003";
    ServiceBinder mBinder = new ServiceBinder();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.d(TAG,"apikey :" + apikey);

            try {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        startDownloadTask();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                //thread.join();
            }catch (Exception ex){
                Log.e(TAG,"error "+ ex.toString());
            }
        }
    };
    public DownloadImageService() {
        super("DownloadImageService");
        Log.d(TAG,"DownloadImageService()");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate ...");
        String CHANNEL_ID = "my_channel_01";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).
                createNotificationChannel(channel);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build();

        startForeground(1,notification);
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG,"onStartCommand ");
//        handler.sendEmptyMessage(0);
//        return START_STICKY;
//    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        return mBinder;
//    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG,"onHandleIntent");
        apikey = intent.getStringExtra("APIKEY");
        Log.d(TAG,"apikey :" + apikey);
        startDownloadTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
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
    void startDownloadTask() {
        Log.d(TAG, "startDownloadTask");

        WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
        String fullurl = String.format(url, DataID, apikey);
        String jsonData = weatherDataFetcher.sentHttpRequestGet(fullurl);
        Log.d(TAG, "jsonData " + jsonData);
        ImageData imageData = parserJSON(jsonData);
        Bitmap bitmap = weatherDataFetcher.getSatelliteImage(imageData.uri);

        String output = String.format("%s/%s_%s.jpg", WeatherDataFetcher.downloadPath,
                DataID, imageData.obsTime);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ServiceBinder extends Binder{
        public void startDownload(){
            Log.d(TAG,"start download");
        }
    }
}
