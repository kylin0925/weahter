package ap.ky.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
        textView.setText(location.Name);

        if (location.weatherElement != null) {
            WeatherElement maxt = location.weatherElement.get(1);
            WeatherElement mint = location.weatherElement.get(2);

            String time1 = maxt.time.get(0).startTime + "\n" + maxt.time.get(0).endTime;
            String time2 = maxt.time.get(1).startTime + "\n" + maxt.time.get(1).endTime;
            String time3 = maxt.time.get(2).startTime + "\n" + maxt.time.get(2).endTime;
            String time4 = maxt.time.get(3).startTime + "\n" + maxt.time.get(3).endTime;
            String time5 = maxt.time.get(4).startTime + "\n" + maxt.time.get(4).endTime;
            String time6 = maxt.time.get(5).startTime + "\n" + maxt.time.get(5).endTime;
            String time7 = maxt.time.get(6).startTime + "\n" + maxt.time.get(6).endTime;



            txtTemp1.setText(mint.time.get(0).parameter.parameterName + " " +
                    maxt.time.get(0).parameter.parameterName + "\n");
            txtTemp2.setText(mint.time.get(1).parameter.parameterName + " " +
                    maxt.time.get(1).parameter.parameterName + "\n");
            txtTemp3.setText(mint.time.get(2).parameter.parameterName + " " +
                    maxt.time.get(2).parameter.parameterName + "\n");

            txtTemp4.setText(mint.time.get(3).parameter.parameterName + " " +
                    maxt.time.get(3).parameter.parameterName + "\n");
            txtTemp5.setText(mint.time.get(4).parameter.parameterName + " " +
                    maxt.time.get(4).parameter.parameterName + "\n");
            txtTemp6.setText(mint.time.get(5).parameter.parameterName + " " +
                    maxt.time.get(5).parameter.parameterName + "\n");
            txtTemp7.setText(mint.time.get(6).parameter.parameterName + " " +
                    maxt.time.get(6).parameter.parameterName + "\n");

            txtStartTime1.setText(time1);
            txtStartTime2.setText(time2);
            txtStartTime3.setText(time3);
            txtStartTime4.setText(time4);
            txtStartTime5.setText(time5);
            txtStartTime6.setText(time6);
            txtStartTime7.setText(time7);
        }

        addView(view);
    }
}
