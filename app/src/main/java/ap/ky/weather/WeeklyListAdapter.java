package ap.ky.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WeeklyListAdapter extends RecyclerView.Adapter<WeeklyListAdapter.ViewHolder> {
    List<WeatherElement> weatherElement;


    LayoutInflater inflater ;
    Context context;

    public WeeklyListAdapter(Context context,List<WeatherElement> weatherElement) {
        this.context = context;
        this.weatherElement = weatherElement;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    @Override
//    public int getCount() {
//        WeatherElement wx = weatherElement.get(0);
//        return wx.time.size()/2;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

    @NonNull
    @Override
    public WeeklyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeeklyListAdapter.ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.weekly_item, parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull WeeklyListAdapter.ViewHolder holder, int position) {
        holder.bindTo(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        WeatherElement wx = weatherElement.get(0);
        return wx.time.size()/2;
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtStartTime;
        TextView txtTemp;
        TextView txtTemp1;

        ImageView ivWeather;
        ImageView ivWeather1;
        public ViewHolder(View itemView) {
            super(itemView);
            txtStartTime = (TextView)itemView.findViewById(R.id.txtStartTime);
            txtTemp = (TextView)itemView.findViewById(R.id.txtTemp);
            ivWeather = (ImageView) itemView.findViewById(R.id.ivWeather);

            txtTemp1 = (TextView)itemView.findViewById(R.id.txtTemp1);
            ivWeather1 = (ImageView) itemView.findViewById(R.id.ivWeather1);
        }

        public void bindTo(int position) {

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


            templst = mint.time.get(position*2).parameter.parameterName + " ~ " +
                    maxt.time.get(position*2).parameter.parameterName + " ℃\n" +
                    wx.time.get(position*2).parameter.parameterName ;
            templstNight = mint.time.get(position*2 + 1).parameter.parameterName + " ~ " +
                    maxt.time.get(position*2 + 1).parameter.parameterName + " ℃\n" +
                    wx.time.get(position*2 + 1).parameter.parameterName;
            weatherIconlst = WeatherIconMap.iconMap.get( wx.time.get(position*2).parameter.parameterName);
            weatherIconlstNight = WeatherIconMap.iconMap.get( wx.time.get(position*2+1).parameter.parameterName);

            txtStartTime.setText(timelst);
            txtTemp.setText(templst);
            txtTemp1.setText(templstNight);

            ivWeather.setImageResource(weatherIconlst);
            ivWeather1.setImageResource(weatherIconlstNight);

        }
    }
}
