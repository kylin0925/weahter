package ap.ky.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WeeklyListAdapter extends BaseAdapter {
    List<WeatherElement> weatherElement;

    TextView txtStartTime;
    TextView txtTemp;
    TextView txtTemp1;

    ImageView ivWeather;
    ImageView ivWeather1;
    LayoutInflater inflater ;
    Context context;

    public WeeklyListAdapter(Context context,List<WeatherElement> weatherElement) {
        this.context = context;
        this.weatherElement = weatherElement;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        WeatherElement wx = weatherElement.get(0);
        return wx.time.size()/2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.weekly_item, null);

        txtStartTime = (TextView)convertView.findViewById(R.id.txtStartTime);
        txtTemp = (TextView)convertView.findViewById(R.id.txtTemp);
        ivWeather = (ImageView) convertView.findViewById(R.id.ivWeather);

        txtTemp1 = (TextView)convertView.findViewById(R.id.txtTemp1);
        ivWeather1 = (ImageView) convertView.findViewById(R.id.ivWeather1);
        WeatherElement wx = weatherElement.get(0);
        WeatherElement maxt = weatherElement.get(1);
        WeatherElement mint = weatherElement.get(2);

        String timelst = "";
        String templst = "";
        String templstNight = "";
        int weatherIconlst = 0;
        int weatherIconlstNight = 0;


        String startTime = maxt.time.get(position*2).startTime;
        String endTime = maxt.time.get(position).endTime;

        String day = "";
        String night = "";
        // date format 2018-02-11T12:00:00+08:00
        int tmp;
        String time = "";
        tmp = startTime.indexOf('T');
        time = startTime.substring(tmp+1,startTime.length());
        startTime = startTime.substring(0,tmp);
        if(time.substring(0,5).equals("06:00")){
            day = startTime + " " + context.getResources().getString(R.string.weekly_day) ;
            tmp = endTime.indexOf('T');
            night = endTime.substring(0,tmp) + " " + context.getResources().getString(R.string.weekly_night) ;
        }else{
            day = startTime + " " + context.getResources().getString(R.string.weekly_night) ;
            tmp = endTime.indexOf('T');
            night = endTime.substring(0,tmp) + " " + context.getResources().getString(R.string.weekly_day) ;
        }

        timelst = day + " ~ " +  night ;


        templst = mint.time.get(position*2).parameter.parameterName + " " +
                maxt.time.get(position*2).parameter.parameterName + "\n" +
                wx.time.get(position*2).parameter.parameterName ;
        templstNight = mint.time.get(position*2 + 1).parameter.parameterName + " " +
                maxt.time.get(position*2 + 1).parameter.parameterName + "\n" +
                wx.time.get(position*2 + 1).parameter.parameterName;
        weatherIconlst = WeatherIconMap.iconMap.get( wx.time.get(position*2).parameter.parameterName);
        weatherIconlstNight = WeatherIconMap.iconMap.get( wx.time.get(position*2+1).parameter.parameterName);

        txtStartTime.setText(timelst);
        txtTemp.setText(templst);
        txtTemp1.setText(templstNight);

        ivWeather.setImageResource(weatherIconlst);
        ivWeather1.setImageResource(weatherIconlstNight);
        return convertView;
    }
}
