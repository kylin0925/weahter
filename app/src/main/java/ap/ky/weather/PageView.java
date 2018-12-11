package ap.ky.weather;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        textView.setText(location.locationName);

        DailyAdapter dailyAdapter = new DailyAdapter(context,location.weatherElement);
        RecyclerView listView = view.findViewById(R.id.dailyList);
        listView.setAdapter(dailyAdapter);
        listView.setLayoutManager(new LinearLayoutManager(context));
        addView(view);
    }
}
