package ap.ky.weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class DailyAdapter extends BaseAdapter {

    List<WeatherElement> weatherElement;

    TextView txtTemp,txtStartTime;
    ImageView ivWeather;
    LayoutInflater inflater ;

    public DailyAdapter(Context context, List<WeatherElement> weatherElement) {

        this.weatherElement = weatherElement;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        WeatherElement wx = weatherElement.get(0);
        return wx.time.size();
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
        convertView = inflater.inflate(R.layout.daily_item, null);

        txtTemp = (TextView)convertView.findViewById(R.id.txtTemp);
        ivWeather = (ImageView) convertView.findViewById(R.id.ivWeather);
        txtStartTime = (TextView)convertView.findViewById(R.id.txtStartTime);

        if(weatherElement!=null) {
            WeatherElement wx = weatherElement.get(0);
            WeatherElement maxt = weatherElement.get(4);
            WeatherElement mint = weatherElement.get(2);
            WeatherElement pop = weatherElement.get(1);

            String time1 = maxt.time.get(position).startTime + " ~ " + maxt.time.get(position).endTime;



            txtTemp.setText(mint.time.get(position).parameter.parameterName + " ~ " +
                    maxt.time.get(position).parameter.parameterName + "\n"  + wx.time.get(position).parameter.parameterName+ "\n" +
                    "降雨機率: " + pop.time.get(position).parameter.parameterName + "%");


            Log.e("temp debug","get icon" + WeatherIconMap.iconMap.get( wx.time.get(position).parameter.parameterName));
            ivWeather.setImageResource(WeatherIconMap.iconMap.get( wx.time.get(position).parameter.parameterName));

            txtStartTime.setText(time1);

        }
        return convertView;
    }
}
