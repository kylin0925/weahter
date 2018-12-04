package ap.ky.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kylin25 on 2018/2/9.
 */

public class PageView extends RelativeLayout{
    public PageView(Context context,Location location) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pageview_daily_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.txtLocation);

//        TextView txtStartTime1 = (TextView) view.findViewById(R.id.txtStartTime1);
//        TextView txtStartTime2 = (TextView) view.findViewById(R.id.txtStartTime2);
//        TextView txtStartTime3 = (TextView) view.findViewById(R.id.txtStartTime3);
//        ImageView ivWeather1 = (ImageView)view.findViewById(R.id.ivWeather1);
//        ImageView ivWeather2 = (ImageView)view.findViewById(R.id.ivWeather2);
//        ImageView ivWeather3 = (ImageView)view.findViewById(R.id.ivWeather3);
        //TextView txtStartTime4 = (TextView) view.findViewById(R.id.txtStartTime4);
        //TextView txtStartTime5 = (TextView) view.findViewById(R.id.txtStartTime5);
        //TextView txtStartTime6 = (TextView) view.findViewById(R.id.txtStartTime6);
        //TextView txtStartTime7 = (TextView) view.findViewById(R.id.txtStartTime7);
        //txtStartTime4.setVisibility(GONE);
        //txtStartTime5.setVisibility(GONE);
        //txtStartTime6.setVisibility(GONE);
        //txtStartTime7.setVisibility(GONE);

        TextView txtTemp1 = (TextView) view.findViewById(R.id.txtTemp);
//        TextView txtTemp2 = (TextView) view.findViewById(R.id.txtTemp2);
//        TextView txtTemp3 = (TextView) view.findViewById(R.id.txtTemp3_1);
        /*TextView txtTemp4 = (TextView) view.findViewById(R.id.txtTemp4);
        TextView txtTemp5 = (TextView) view.findViewById(R.id.txtTemp5);
        TextView txtTemp6 = (TextView) view.findViewById(R.id.txtTemp6);
        TextView txtTemp7 = (TextView) view.findViewById(R.id.txtTemp7);*/

        /*txtTemp4.setVisibility(GONE);
        txtTemp5.setVisibility(GONE);
        txtTemp6.setVisibility(GONE);
        txtTemp7.setVisibility(GONE);*/

        textView.setText(location.locationName);

//        if(location.weatherElement!=null) {
//            WeatherElement wx = location.weatherElement.get(0);
//            WeatherElement maxt = location.weatherElement.get(4);
//            WeatherElement mint = location.weatherElement.get(2);
//            WeatherElement pop = location.weatherElement.get(1);
//
//            String time1 = maxt.time.get(0).startTime + " ~ " + maxt.time.get(0).endTime;
//            String time2 = maxt.time.get(1).startTime + " ~ " + maxt.time.get(1).endTime;
//            String time3 = maxt.time.get(2).startTime + " ~ " + maxt.time.get(2).endTime;
//
//
//            txtTemp1.setText(mint.time.get(0).parameter.parameterName + " ~ " +
//                    maxt.time.get(0).parameter.parameterName + "\n"  + wx.time.get(0).parameter.parameterName+ "\n" +
//                    "降雨機率: " + pop.time.get(0).parameter.parameterName + "%");
//            txtTemp2.setText(mint.time.get(1).parameter.parameterName + " ~ " +
//                    maxt.time.get(1).parameter.parameterName + "\n" + wx.time.get(1).parameter.parameterName + "\n" +
//                    "降雨機率: " + pop.time.get(1).parameter.parameterName + "%");
//            txtTemp3.setText(mint.time.get(2).parameter.parameterName + " ~ " +
//                    maxt.time.get(2).parameter.parameterName + "\n" + wx.time.get(2).parameter.parameterName +"\n" +
//                    "降雨機率: " + pop.time.get(2).parameter.parameterName + "%");
//
//            Log.e("temp debug","get icon" + WeatherIconMap.iconMap.get( wx.time.get(1).parameter.parameterName));
//            ivWeather1.setImageResource(WeatherIconMap.iconMap.get( wx.time.get(0).parameter.parameterName));
//            ivWeather2.setImageResource(WeatherIconMap.iconMap.get( wx.time.get(1).parameter.parameterName));
//            ivWeather3.setImageResource(WeatherIconMap.iconMap.get( wx.time.get(2).parameter.parameterName));
//            txtStartTime1.setText(time1);
//            txtStartTime2.setText(time2);
//            txtStartTime3.setText(time3);
//        }
        DailyAdapter dailyAdapter = new DailyAdapter(context,location.weatherElement);
        ListView listView = view.findViewById(R.id.dailyList);
        listView.setAdapter(dailyAdapter);
        addView(view);
    }
}
