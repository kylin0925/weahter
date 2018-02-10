package ap.ky.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kylin25 on 2018/2/9.
 */

public class PageView extends RelativeLayout{
    public PageView(Context context,Location location) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pageview_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.txtLocation);

        TextView txtStartTime1 = (TextView) view.findViewById(R.id.txtStartTime1);
        TextView txtStartTime2 = (TextView) view.findViewById(R.id.txtStartTime2);
        TextView txtStartTime3 = (TextView) view.findViewById(R.id.txtStartTime3);


        TextView txtTemp1 = (TextView) view.findViewById(R.id.txtTemp);
        TextView txtTemp2 = (TextView) view.findViewById(R.id.txtTemp2);
        TextView txtTemp3 = (TextView) view.findViewById(R.id.txtTemp3);
        textView.setText(location.Name);

        if(location.weatherElement!=null) {
            WeatherElement maxt = location.weatherElement.get(1);
            WeatherElement mint = location.weatherElement.get(2);

            String time1 = maxt.time.get(0).startTime + "\n" + maxt.time.get(0).endTime;
            String time2 = maxt.time.get(1).startTime + "\n" + maxt.time.get(1).endTime;
            String time3 = maxt.time.get(2).startTime + "\n" + maxt.time.get(2).endTime;


            txtTemp1.setText(mint.time.get(0).parameter.parameterName + " " +
                     maxt.time.get(0).parameter.parameterName + "\n");
            txtTemp2.setText(mint.time.get(1).parameter.parameterName + " " +
                    maxt.time.get(1).parameter.parameterName + "\n");
            txtTemp3.setText(mint.time.get(2).parameter.parameterName + " " +
                    maxt.time.get(2).parameter.parameterName + "\n");

            txtStartTime1.setText(time1);
            txtStartTime2.setText(time2);
            txtStartTime3.setText(time3);
        }

        addView(view);
    }
}
