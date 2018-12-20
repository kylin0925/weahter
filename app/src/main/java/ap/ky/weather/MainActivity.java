package ap.ky.weather;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Weather";
    ImageView imageView;
    TextView txtWeather;
    Bitmap bitmap;

    ActionBarDrawerToggle actionBarDrawerToggle;
    WebView webView;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    final String KEY= "key";
    String apiKey = "";
    String DATATYPE = "O-B0028-003";
    String SATELLITEIMAGE = "O-B0028-003";
    String DAILYDATA = "F-C0032-001";
    String WEEKLYDATA = "F-C0032-005";
    String[] DATATYPELIST = {DAILYDATA,WEEKLYDATA,SATELLITEIMAGE};
    String[] weatherType;
    final int TYPE_DAILY = 0;
    final int TYPE_WEEKLY = 1;
    final int TYPE_SATELLITE = 2;

    String queryStr = "http://opendata.cwb.gov.tw/opendataapi?dataid=%s&authorizationkey=%s";
    ProgressBar progressBar;
    SeekBar seekBar;
    ConstraintLayout satellite;
    File[] imageFileList = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    void setAlarmManager(){
        Log.e(TAG,"setAlarmManager");
        Intent intent = new Intent(this,WeatherReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,
               0);

        AlarmManager  alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        int offset = 10 * 1000;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + offset ,pendingIntent);
    }
    private class FetchSatelliteImageTask extends AsyncTask<String,Integer,Bitmap>{
        String TAG = "FetchDataTask";
        @Override
        protected Bitmap doInBackground(String... urls) {
            WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
            Bitmap bitmap = weatherDataFetcher.getSatelliteImage(urls[0]);
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //int queryType = integer;
            Log.e(TAG,"Image download complete");

            webView.setVisibility(View.GONE);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
            else
                Toast.makeText(getApplicationContext(),"Get Satellite image error",Toast.LENGTH_LONG).show();
            imageView.setVisibility(View.VISIBLE);
            webView.clearFormData();
            progressBar.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
        }
    }
    private class FetchWeatherTask extends AsyncTask<Integer,Integer,List<View>>{
        String TAG = "FetchDataTask";
        List<Location> getDailyData(){
            WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
            Record record = weatherDataFetcher.getDailyData(apiKey);
            Log.e(TAG,"record " + record.datasetDescription);
            List<Location> locations = new LocationSort().sortLocation(record.location);
            return locations;
        }
        List<Location> getWeeklyData(){
            String queryLink = String.format(queryStr, DATATYPELIST[queryType], apiKey);
            Log.e(TAG, queryLink);
            WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
            String result = weatherDataFetcher.sentHttpRequestGet(queryLink);
            //setWeatherData(result,queryType);
            Log.e(TAG, "result " + result);

            weatherParser w = new weatherParser();
            StringReader in = new StringReader( result );
            try {
                List<Location> locations = new LocationSort().sortLocation(w.parserDaily(in));
                return locations;
            }catch (Exception ex){
                Log.e(TAG,"get weekly data error: " + ex.toString());
            }
            return null;
        }
        int queryType;
        @Override
        protected List<View> doInBackground(Integer... params) {
            try {
                queryType = params[0];
                List<Location> lstLocation =null ;
                if(queryType == TYPE_DAILY){
                    lstLocation = getDailyData();
                }
                if(queryType == TYPE_WEEKLY){
                    lstLocation = getWeeklyData();
                }
                List<View> lstView = new ArrayList<>();
                if(lstLocation!=null) {
                    for (Location l : lstLocation) {
                        if (queryType == TYPE_DAILY) {
                            lstView.add(new PageView(getApplicationContext(), l));
                        } else if (queryType == TYPE_WEEKLY) {
                            lstView.add(new PageViewWeekly(getApplicationContext(), l));
                        }
                    }
                }
                return lstView;
            } catch (Exception e) {
                Log.e(TAG, "exception ===>" + e.toString());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<View> lstView) {
            super.onPostExecute(lstView);
            Log.e(TAG,"download complete");
            if(lstView!=null)
                viewPager.setAdapter(new pagerAdapter(lstView));
            else
                Toast.makeText(getApplicationContext(),"get data error",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            //imageView.setVisibility(View.GONE);
            satellite.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        satellite = (ConstraintLayout) findViewById(R.id.satellite_constraint);
        satellite.setVisibility(View.GONE);

        txtWeather = (TextView)findViewById(R.id.txtWeatherData);
        weatherType = getResources().getStringArray(R.array.datatype_array);

        webView = (WebView)findViewById(R.id.webResult);
        imageView = (ImageView)findViewById(R.id.imageView);

        final SharedPreferences sharedPref = getSharedPreferences("", Context.MODE_PRIVATE);
        apiKey = sharedPref.getString(KEY,"");
        Log.e(TAG,"key is " + apiKey);

        viewPager = (ViewPager)findViewById(R.id.pager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(0);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.e(TAG,"" + id);
                int position = 0;
                progressBar.setVisibility(View.VISIBLE);
                if(id == R.id.menu_daily){
                    position = TYPE_DAILY;
                    FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
                    fetchWeatherTask.execute(TYPE_DAILY);
                }else if(id == R.id.menu_weekly){
                    position = TYPE_WEEKLY;
                    FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
                    fetchWeatherTask.execute(TYPE_WEEKLY);
                }else if(id == R.id.menu_satellite){
                    position = TYPE_SATELLITE;
                    //FetchSatelliteImageTask fetchDataTask = new FetchSatelliteImageTask();
                    //fetchDataTask.execute("http://opendata.cwb.gov.tw/opendata/MSC/O-B0028-003.jpg");
                    File directory = new File(WeatherDataFetcher.downloadPath);
                    imageFileList = directory.listFiles();
                    for (File file :imageFileList){
                        Log.d(TAG,file.getName());
                    }
                    seekBar.setMax(imageFileList.length-1);
                    seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
                    seekBar.setProgress(imageFileList.length-1);

                    satellite.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                }
                Log.e(TAG,"position " + position);
                //Thread t = new Thread(new queryRun(position));
                //t.start();

                return true;
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        //Thread t = new Thread(new queryRun(0));
        //t.start();
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        fetchWeatherTask.execute(TYPE_DAILY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0
                );
        }

        File downloadFolder = new File(WeatherDataFetcher.downloadPath);
        Log.d(TAG,"path " + downloadFolder.getPath());

        if(downloadFolder.isDirectory() == false) {
            boolean b = downloadFolder.mkdir();
            Log.e(TAG,"create "+ b);
        }

//        Intent intent = new Intent(this,DownloadImageService.class);
//        intent.putExtra("APIKEY",apiKey);
//        startService(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setAlarmManager();
        }
    }
    private class pagerAdapter extends PagerAdapter{
        private List<View> lstPage;
        public pagerAdapter( List<View> lstPage) {
            this.lstPage = lstPage;
        }

        @Override
        public int getCount() {
            return lstPage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = lstPage.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sharedPref = getSharedPreferences("", Context.MODE_PRIVATE);
        apiKey = sharedPref.getString(KEY,"");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        if(i == R.id.menukey){
            startActivity(new Intent(this,MainKeyActivity.class));
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d(TAG,"progress " + progress);
            Bitmap bitmap = BitmapFactory.decodeFile(WeatherDataFetcher.downloadPath +
                    File.separator + imageFileList[progress].getName());

            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
