package ap.ky.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PowerManager;
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

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            }else {
                context.startService(intent);
            }
            Log.e(TAG, "end Receive");
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }
}
