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
        textView.setText(location.locationName);

        if (location.weatherElement != null) {
            WeatherElement wx = location.weatherElement.get(0);
            WeatherElement maxt = location.weatherElement.get(1);
            WeatherElement mint = location.weatherElement.get(2);
            String[] timelst = new String[7];
            String[] templst = new String[7];
            for(int i = 0;i<7;i++){
                timelst[i] = maxt.time.get(i*2).startTime + "\n" +
                             maxt.time.get(i*2).endTime  ;
                             //maxt.time.get(i*2 + 1).startTime + "\n" +
                             //maxt.time.get(i*2 + 1).endTime + "\n";

                templst[i] = mint.time.get(i*2).parameter.parameterName + " " +
                             maxt.time.get(i*2).parameter.parameterName + " " +
                             wx.time.get(i*2).parameter.parameterName + "\n" +
                             mint.time.get(i*2 + 1).parameter.parameterName + " " +
                             maxt.time.get(i*2 + 1).parameter.parameterName + " " +
                             wx.time.get(i*2).parameter.parameterName;
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
        }

        addView(view);
    }
}
