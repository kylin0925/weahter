package ap.ky.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Weather";
    ImageView imageView;
    TextView txtWeather;
    Bitmap bitmap;
    private DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle actionBarDrawerToggle;
    WebView webView;
    ViewPager viewPager;

    final String KEY= "key";
    String apiKey = "";
    String DATATYPE = "O-B0028-003";
    String SATELLITEIMAGE = "O-B0028-003";
    String DAILYDATA = "F-C0032-001";
    String WEEKLYDATA = "F-C0032-003";
    String[] DATATYPELIST = {DAILYDATA,WEEKLYDATA,SATELLITEIMAGE};
    String[] weatherType;
    String queryStr = "http://opendata.cwb.gov.tw/opendataapi?dataid=%s&authorizationkey=%s";
    ProgressBar progressBar;
    String getSytelSheet(int resid){
        String strXsl = "";
        InputStream raw = getResources().openRawResource(resid);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int size = 0;
        try{
            while( (size = raw.read(buffer,0,1024)) > 0){
                outputStream.write(buffer,0,size);
            }
            raw.close();
            strXsl = outputStream.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return strXsl;
    }
    String xmltoXslt(String xml,String xsl){
        String html = "";
        try {
            InputStream ds = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Source xmlSource = new StreamSource(ds);

            InputStream xs = new ByteArrayInputStream(xsl.getBytes("UTF-8"));
            Source xslSource = new StreamSource(xs);

            StringWriter writer = new StringWriter();
            Result result = new StreamResult(writer);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);
            transformer.transform(xmlSource,result);
            html = writer.toString();
            ds.close();
            xs.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return html;
    }
    void getImage(String url){
        Log.e(TAG,"GET " + url);
        try{
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.setVisibility(View.GONE);
                    imageView.setImageBitmap(bitmap);
                    webView.clearFormData();
                }
            });

        }catch (Exception ex){
            Log.e(TAG,"error " + ex);
        }

    }
    void setWeatherData(final String data){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //txtWeather.setText(data);

                weatherParser w = new weatherParser();
                StringReader in = new StringReader( data );
                try {
                    List<Location> lstLocation = w.parserDaily(in);
                    List<View> lstView = new ArrayList<>();
                    for(Location l:lstLocation){
                        lstView.add(new PageView(getApplicationContext() ,l));
                    }

                   viewPager.setAdapter(new pagerAdapter(lstView));

                }catch (Exception ex){
                    Log.e(TAG,"xml exception " + ex.toString());
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
//                String xsl = getSytelSheet(R.raw.c0032);
//                String html = xmltoXslt(data,xsl);
//                webView.loadData(html,"text/html; charset=utf-8", "utf-8");
//                imageView.setImageBitmap(null);
//                webView.setVisibility(View.VISIBLE);
            }
        });
    }
    String httpreq(String url){
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
                // Log.e(TAG, "url ok " + tmp);
            }
            h.disconnect();
        }catch (Exception e){
            Log.e(TAG,"exception : " + e.toString());
        }
        return result;
    }
//    Runnable r = new Runnable() {
//
//        @Override
//        public void run() {
//            try {
//                String queryLink = String.format(queryStr,DATATYPE,apiKey );
//                Log.e(TAG,queryLink);
//                String result = httpreq(queryLink);
//                getImage( "http://opendata.cwb.gov.tw/opendata/MSC/O-B0028-003.jpg");
//                //String result = getSearchResult(types);
//                //setData(result);
//                Log.e(TAG,"result " + result);
//            }catch (Exception e){
//                Log.e(TAG,"exception ===>" + e.toString());
//            }
//        }
//    };
    private class queryRun implements Runnable{
        int queryType;
        public queryRun(int queryType) {
            this.queryType = queryType;
        }

        @Override
        public void run() {
            Log.e(TAG,"queryType " + queryType);
            if(queryType == 2){
                getImage( "http://opendata.cwb.gov.tw/opendata/MSC/O-B0028-003.jpg");

            }else {
                try {
                    String queryLink = String.format(queryStr, DATATYPELIST[queryType], apiKey);
                    Log.e(TAG, queryLink);
                    String result = httpreq(queryLink);

                    //String result = getSearchResult(types);
                    setWeatherData(result);
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
        mDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        weatherType = getResources().getStringArray(R.array.datatype_array);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,weatherType));
        webView = (WebView)findViewById(R.id.webResult);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        imageView = (ImageView)findViewById(R.id.imageView);

        final SharedPreferences sharedPref = getSharedPreferences("", Context.MODE_PRIVATE);
        apiKey = sharedPref.getString(KEY,"");
        Log.e(TAG,"key is " + apiKey);

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.e(TAG,"" + id);
                int position = 0;
                if(id == R.id.daily){
                    position = 0;
                }else if(id == R.id.weekly){
                    position = 1;
                }else if(id == R.id.satimage){
                    position = 2;
                }

                Thread t = new Thread(new queryRun(position));
                t.start();
                mDrawerLayout.closeDrawers();
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }
        });

        viewPager = (ViewPager)findViewById(R.id.pager);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
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

//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            //
//            Thread t = new Thread(new queryRun(position));
//            t.start();
//
//            mDrawerLayout.closeDrawers();
//
//        }
//    }
}
