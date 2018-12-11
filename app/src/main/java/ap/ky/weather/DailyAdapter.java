package ap.ky.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    List<WeatherElement> weatherElement;
    LayoutInflater inflater ;
    Context mContext;
    public DailyAdapter(Context context, List<WeatherElement> weatherElement) {

        this.weatherElement = weatherElement;
        mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.daily_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        WeatherElement wx = weatherElement.get(0);
        return wx.time.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView txtTemp,txtStartTime;
        ImageView ivWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTemp = (TextView)itemView.findViewById(R.id.txtTemp);
            ivWeather = (ImageView) itemView.findViewById(R.id.ivWeather);
            txtStartTime = (TextView)itemView.findViewById(R.id.txtStartTime);
        }

        public void bindTo(int position) {

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
        }
    }
}
