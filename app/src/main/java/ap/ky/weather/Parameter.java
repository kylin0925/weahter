package ap.ky.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kylin25 on 2018/2/4.
 */

public class Parameter {
    String parameterName;
    @SerializedName(value="parameterValue", alternate={"parameterUnit"}) String parameterValue;
}
