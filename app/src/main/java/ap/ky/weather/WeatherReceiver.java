package ap.ky.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import java.io.FileOutputStream;

public class WeatherReceiver extends BroadcastReceiver {
    String TAG = "WeatherReceiver";
    final String KEY= "key";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent ReceiverIntent) {
        try {
            Log.e(TAG, "ACTION " + ReceiverIntent.getAction());
            final SharedPreferences sharedPref = context.getSharedPreferences("", Context.MODE_PRIVATE);
            String apiKey = sharedPref.getString(KEY, "");
            Log.e(TAG, "key is " + apiKey);

            Intent intent = new Intent(context, DownloadImageService.class);
            intent.putExtra("APIKEY", apiKey);

            context.startService(intent);
            setAlarmManager(context);

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setAlarmManager(Context context){
        Log.e(TAG,"setAlarmManager");
        Intent intent = new Intent(context,WeatherReceiver.class);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context,0,intent,
                0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        int offset = 60 * 1000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + offset ,pendingIntent);
    }
}
