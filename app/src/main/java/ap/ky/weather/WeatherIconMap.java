package ap.ky.weather;

import java.util.HashMap;
import java.util.Map;

public class WeatherIconMap {
    static Map<String, Integer> iconMap;
    static {
        iconMap = new HashMap<>();

        iconMap.put("晴天", R.drawable.icons8_summer_48);
        iconMap.put("多雲", R.drawable.icons8_clouds_48);
        iconMap.put("陰天", R.drawable.icons8_clouds_48);
        iconMap.put("陰有霧", R.drawable.icons8_clouds_48);
        iconMap.put("陰有雨", R.drawable.icons8_rain_48);
        iconMap.put("陰陣雨", R.drawable.icons8_rain_48);
        iconMap.put("多雲時陰", R.drawable.icons8_clouds_48);
        iconMap.put("陰時多雲", R.drawable.icons8_clouds_48);
        iconMap.put("多雲時晴", R.drawable.icons8_clouds_48);
        iconMap.put("晴時多雲", R.drawable.icons8_partly_cloudy_day_48);
        iconMap.put("多雲時陰有雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲短暫雨", R.drawable.icons8_rain_48);
        iconMap.put("多雲時陰短暫雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲局部雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲時陰局部雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陣雨", R.drawable.icons8_rain_48);
        iconMap.put("多雲時陰陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲短暫陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲時陰短暫陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲局部陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲時陰局部陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲午後短暫陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲時陰陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("多雲短暫陣雨或雷雨",  R.drawable.icons8_storm_40);
        iconMap.put("多雲時陰短暫陣雨或雷雨",  R.drawable.icons8_storm_40);
        iconMap.put("多雲局部陣雨或雷雨",  R.drawable.icons8_storm_40);
        iconMap.put("多雲時陰局部陣雨或雷雨",  R.drawable.icons8_storm_40);
        iconMap.put("多雲午後短暫雷陣雨",  R.drawable.icons8_storm_40);
        iconMap.put("晴午後短暫陣雨", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陰時多雲有雨", R.drawable.icons8_rain_48);
        iconMap.put("陰時多雲短暫雨", R.drawable.icons8_rain_48);
        iconMap.put("陰短暫雨", R.drawable.icons8_rain_48);
        iconMap.put("陰時多雲局部雨", R.drawable.icons8_rain_48);
        iconMap.put("陰局部雨", R.drawable.icons8_rain_48);
        iconMap.put("陰時多雲陣雨", R.drawable.icons8_rain_48);
        iconMap.put("陰時多雲短暫陣雨", R.drawable.icons8_rain_48);
        iconMap.put("陰短暫陣雨", R.drawable.icons8_rain_48);
        iconMap.put("陰時多雲局部陣雨", R.drawable.icons8_rain_48);
        iconMap.put("陰局部陣雨", R.drawable.icons8_rain_48);
        iconMap.put("陰陣雨或雷雨",R.drawable.icons8_storm_40);
        iconMap.put("晴午後短暫雷陣雨",  R.drawable.icons8_storm_40);
        iconMap.put("陰時多雲陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("陰時多雲短暫陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("陰短暫陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("陰時多雲局部陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("陰局部陣雨或雷雨", R.drawable.icons8_storm_40);
        iconMap.put("晴有霧", R.drawable.icons8_haze_48);
        iconMap.put("多雲有霧", R.drawable.icons8_fog_50);
        iconMap.put("多雲時陰有霧", R.drawable.icons8_fog_50);
        iconMap.put("陰時多雲有霧", R.drawable.icons8_fog_50);
        iconMap.put("多雲時晴有霧", R.drawable.icons8_fog_50);
        iconMap.put("晴時多雲有霧", R.drawable.icons8_haze_48);
        iconMap.put("多雲局部雨有霧", R.drawable.icons8_fog_50);
        iconMap.put("多雲時陰局部雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲局部陣雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲時陰局部陣雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陰時多雲局部雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陰局部雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陰時多雲局部陣雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("陰局部陣雨有霧", R.drawable.icons8_rain_cloud_48);
        iconMap.put("多雲局部陣雨或雷雨有霧", R.drawable.icons8_storm_40);
        iconMap.put("多雲時陰局部陣雨或雷雨有霧", R.drawable.icons8_storm_40);
        iconMap.put("陰時多雲局部陣雨或雷雨有霧", R.drawable.icons8_storm_40);
        iconMap.put("陰局部陣雨或雷雨有霧", R.drawable.icons8_storm_40);
        iconMap.put("多雲時陰有雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("陰時多雲有雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("陰有雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("多雲短暫雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("多雲時陰短暫雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("陰時多雲短暫雨或雪", R.drawable.icons8_sleet_30);
        iconMap.put("陰短暫雨或雪 ", R.drawable.icons8_sleet_30);
    }
}
