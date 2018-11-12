package ap.ky.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kylin25 on 2018/2/10.
 */

public class PageViewWeekly extends RelativeLayout {
    public PageViewWeekly(Context context, Location location) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pageview_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.txtLocation);

        TextView txtStartTime1 = (TextView) view.findViewById(R.id.txtStartTime1);
        TextView txtStartTime2 = (TextView) view.findViewById(R.id.txtStartTime2);
        TextView txtStartTime3 = (TextView) view.findViewById(R.id.txtStartTime3);
        TextView txtStartTime4 = (TextView) view.findViewById(R.id.txtStartTime4);
        TextView txtStartTime5 = (TextView) view.findViewById(R.id.txtStartTime5);
        TextView txtStartTime6 = (TextView) view.findViewById(R.id.txtStartTime6);
        TextView txtStartTime7 = (TextView) view.findViewById(R.id.txtStartTime7);


        TextView txtTemp1 = (TextView) view.findViewById(R.id.txtTemp);
        TextView txtTemp2 = (TextView) view.findViewById(R.id.txtTemp2);
        TextView txtTemp3 = (TextView) view.findViewById(R.id.txtTemp3);
        TextView txtTemp4 = (TextView) view.findViewById(R.id.txtTemp4);
        TextView txtTemp5 = (TextView) view.findViewById(R.id.txtTemp5);
        TextView txtTemp6 = (TextView) view.findViewById(R.id.txtTemp6);
        TextView txtTemp7 = (TextView) view.findViewById(R.id.txtTemp7);
        textView.setText(location.locationName);

        ImageView ivWeather = (ImageView)view.findViewById(R.id.ivWeather1);
        ImageView ivWeather1 = (ImageView)view.findViewById(R.id.ivWeather2);
        ImageView ivWeather2 = (ImageView)view.findViewById(R.id.ivWeather3);
        ImageView ivWeather3 = (ImageView)view.findViewById(R.id.ivWeather4);
        ImageView ivWeather4 = (ImageView)view.findViewById(R.id.ivWeather5);
        ImageView ivWeather5 = (ImageView)view.findViewById(R.id.ivWeather6);
        ImageView ivWeather6 = (ImageView)view.findViewById(R.id.ivWeather7);


        ImageView ivWeather_1 = (ImageView)view.findViewById(R.id.ivweather1_1);
        ImageView ivWeather2_2 = (ImageView)view.findViewById(R.id.ivweather2_2);
        ImageView ivWeather3_3 = (ImageView)view.findViewById(R.id.ivweather3_3);
        ImageView ivWeather4_4 = (ImageView)view.findViewById(R.id.ivweather4_4);
        ImageView ivWeather5_5 = (ImageView)view.findViewById(R.id.ivweather5_5);
        ImageView ivWeather6_6 = (ImageView)view.findViewById(R.id.ivweather6_6);
        ImageView ivWeather7_7 = (ImageView)view.findViewById(R.id.ivweather7_7);

        TextView txtTemp1_1 = (TextView) view.findViewById(R.id.txtTemp_1);
        TextView txtTemp2_1 = (TextView) view.findViewById(R.id.txtTemp2_1);
        TextView txtTemp3_1 = (TextView) view.findViewById(R.id.txtTemp3_1);
        TextView txtTemp4_1 = (TextView) view.findViewById(R.id.txtTemp4_1);
        TextView txtTemp5_1 = (TextView) view.findViewById(R.id.txtTemp5_1);
        TextView txtTemp6_1 = (TextView) view.findViewById(R.id.txtTemp6_1);
        TextView txtTemp7_1 = (TextView) view.findViewById(R.id.txtTemp7_1);

        if (location.weatherElement != null) {
            WeatherElement wx = location.weatherElement.get(0);
            WeatherElement maxt = location.weatherElement.get(1);
            WeatherElement mint = location.weatherElement.get(2);
            String[] timelst = new String[7];
            String[] templst = new String[7];
            String[] templstNight = new String[7];
            int[] weatherIconlst = new int[7];
            int[] weatherIconlstNight = new int[7];

            for(int i = 0;i<7;i++){
                String startTime = maxt.time.get(i*2).startTime;
                String endTime = maxt.time.get(i*2).endTime;
                String day = "";
                String night = "";
                // date format 2018-02-11T12:00:00+08:00
                int tmp;
                String time = "";
                tmp = startTime.indexOf('T');
                time = startTime.substring(tmp+1,startTime.length());
                startTime = startTime.substring(0,tmp);
                if(time.substring(0,5).equals("06:00")){
                    day = startTime + " " + getResources().getString(R.string.weekly_day) ;
                    tmp = endTime.indexOf('T');
                    night = endTime.substring(0,tmp) + " " + getResources().getString(R.string.weekly_night) ;
                }else{
                    day = startTime + " " + getResources().getString(R.string.weekly_night) ;
                    tmp = endTime.indexOf('T');
                    night = endTime.substring(0,tmp) + " " + getResources().getString(R.string.weekly_day) ;
                }

                timelst[i] = day + " ~ " +  night ;
                             //maxt.time.get(i*2 + 1).startTime + "\n" +
                             //maxt.time.get(i*2 + 1).endTime + "\n";

                templst[i] = mint.time.get(i*2).parameter.parameterName + " " +
                             maxt.time.get(i*2).parameter.parameterName + "\n" +
                             wx.time.get(i*2).parameter.parameterName ;
                templstNight[i] = mint.time.get(i*2 + 1).parameter.parameterName + " " +
                             maxt.time.get(i*2 + 1).parameter.parameterName + "\n" +
                             wx.time.get(i*2 + 1).parameter.parameterName;
                weatherIconlst[i] = WeatherIconMap.iconMap.get( wx.time.get(i*2).parameter.parameterName);
                weatherIconlstNight[i] = WeatherIconMap.iconMap.get( wx.time.get(i*2+1).parameter.parameterName);
            }

            txtTemp1.setText(templst[0]);
            txtTemp2.setText(templst[1]);
            txtTemp3.setText(templst[2]);
            txtTemp4.setText(templst[3]);
            txtTemp5.setText(templst[4]);
            txtTemp6.setText(templst[5]);
            txtTemp7.setText(templst[6]);

            txtStartTime1.setText(timelst[0]);
            txtStartTime2.setText(timelst[1]);
            txtStartTime3.setText(timelst[2]);
            txtStartTime4.setText(timelst[3]);
            txtStartTime5.setText(timelst[4]);
            txtStartTime6.setText(timelst[5]);
            txtStartTime7.setText(timelst[6]);

            ivWeather.setImageResource(weatherIconlst[0]);
            ivWeather1.setImageResource(weatherIconlst[1]);
            ivWeather2.setImageResource(weatherIconlst[2]);
            ivWeather3.setImageResource(weatherIconlst[3]);
            ivWeather4.setImageResource(weatherIconlst[4]);
            ivWeather5.setImageResource(weatherIconlst[5]);
            ivWeather6.setImageResource(weatherIconlst[6]);

            ivWeather_1.setImageResource(weatherIconlstNight[0]);
            ivWeather2_2.setImageResource(weatherIconlstNight[1]);
            ivWeather3_3.setImageResource(weatherIconlstNight[2]);
            ivWeather4_4.setImageResource(weatherIconlstNight[3]);
            ivWeather5_5.setImageResource(weatherIconlstNight[4]);
            ivWeather6_6.setImageResource(weatherIconlstNight[5]);
            ivWeather7_7.setImageResource(weatherIconlstNight[6]);

            txtTemp1_1.setText(templstNight[0]);
            txtTemp2_1.setText(templstNight[1]);
            txtTemp3_1.setText(templstNight[2]);
            txtTemp4_1.setText(templstNight[3]);
            txtTemp5_1.setText(templstNight[4]);
            txtTemp6_1.setText(templstNight[5]);
            txtTemp7_1.setText(templstNight[6]);
        }

        addView(view);
    }
}
