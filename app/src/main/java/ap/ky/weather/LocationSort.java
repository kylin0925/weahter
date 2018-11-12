package ap.ky.weather;

import java.util.ArrayList;
import java.util.List;

public class LocationSort {
    List<Location> locationList = new ArrayList<>();

    String[] locationOrder= new String[]{"基隆市","臺北市","新北市",
    "宜蘭縣","花蓮縣","臺東縣",
    "桃園市","新竹縣","新竹市","苗栗縣",
    "臺中市","彰化縣","南投縣",
    "雲林縣","嘉義縣","嘉義市","臺南市",
    "高雄市","屏東縣","澎湖縣","金門縣","連江縣",};

    List<Location> sortLocation(List<Location> locations){
        for(String loc : locationOrder){
            for(Location l : locations){
                if(loc.equals(l.locationName)){
                    locationList.add(l);
                }
            }
        }
        return locationList;
    }
}
