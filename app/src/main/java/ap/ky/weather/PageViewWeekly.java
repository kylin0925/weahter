package ap.ky.weather;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
        textView.setText(location.locationName);


        WeeklyListAdapter weeklyListAdapter = new WeeklyListAdapter(context,location.weatherElement);
        RecyclerView listView = (RecyclerView)view.findViewById(R.id.listView);
        listView.setAdapter(weeklyListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(context));
        addView(view);
    }
}
