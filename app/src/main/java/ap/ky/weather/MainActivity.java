package ap.ky.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.TextView;
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
    void getImage(String url){
        Log.e(TAG,"GET " + url);
        WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
        bitmap = weatherDataFetcher.getSatelliteImage(url);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"Update UI");
                webView.setVisibility(View.GONE);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                webView.clearFormData();
                progressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
            }
        });
    }
    void setWeatherData(final String data,final int queryType){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weatherParser w = new weatherParser();
                StringReader in = new StringReader( data );
                try {
                    List<Location> lstLocation = w.parserDaily(in);
                    List<View> lstView = new ArrayList<>();
                    for (Location l : lstLocation) {
                        if(queryType == TYPE_DAILY) {
                            lstView.add(new PageView(getApplicationContext(), l));
                        }else if(queryType == TYPE_WEEKLY){
                            lstView.add(new PageViewWeekly(getApplicationContext(), l));
                        }
                    }
                   viewPager.setAdapter(new pagerAdapter(lstView));
                }catch (Exception ex){
                    Log.e(TAG,"xml exception " + ex.toString());
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }
        });
    }

    private class queryRun implements Runnable{
        int queryType;
        public queryRun(int queryType) {
            this.queryType = queryType;
        }

        @Override
        public void run() {
            Log.e(TAG,"queryType " + queryType);
            if(queryType == TYPE_SATELLITE){
                getImage( "http://opendata.cwb.gov.tw/opendata/MSC/O-B0028-003.jpg");
            }else {
                try {
                    String queryLink = String.format(queryStr, DATATYPELIST[queryType], apiKey);
                    Log.e(TAG, queryLink);
                    WeatherDataFetcher weatherDataFetcher = WeatherDataFetcher.getInstance();
                    String result = weatherDataFetcher.sentHttpRequestGet(queryLink);
                    setWeatherData(result,queryType);
                    Log.e(TAG, "result " + result);
                } catch (Exception e) {
                    Log.e(TAG, "exception ===>" + e.toString());
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWeather = (TextView)findViewById(R.id.txtWeatherData);
        weatherType = getResources().getStringArray(R.array.datatype_array);

        webView = (WebView)findViewById(R.id.webResult);
        imageView = (ImageView)findViewById(R.id.imageView);

        final SharedPreferences sharedPref = getSharedPreferences("", Context.MODE_PRIVATE);
        apiKey = sharedPref.getString(KEY,"");
        Log.e(TAG,"key is " + apiKey);

        viewPager = (ViewPager)findViewById(R.id.pager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.e(TAG,"" + id);
                int position = 0;
                if(id == R.id.menu_daily){
                    position = TYPE_DAILY;
                }else if(id == R.id.menu_weekly){
                    position = TYPE_WEEKLY;
                }else if(id == R.id.menu_satellite){
                    position = TYPE_SATELLITE;
                }
                Log.e(TAG,"position " + position);
                Thread t = new Thread(new queryRun(position));
                t.start();
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        Thread t = new Thread(new queryRun(0));
        t.start();
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(i == R.id.menukey){
            startActivity(new Intent(this,MainKeyActivity.class));
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
