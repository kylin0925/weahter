package ap.ky.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainKeyActivity extends AppCompatActivity {
    private static final String TAG = "MainKeyActivity";
    Button btnSave;
   // TextView txtKey;
    TextView txtCWB;
    EditText editText;
    final String KEY= "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_key);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        final SharedPreferences sharedPref = getSharedPreferences("", Context.MODE_PRIVATE);
        Log.e(TAG,"" + sharedPref);
        String key = sharedPref.getString(KEY,"");
        Log.e(TAG,"key is " + key);
        editText = (EditText) findViewById(R.id.edtKey);
        editText.setText(key);
        btnSave = (Button)findViewById(R.id.btnSavekey);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                Log.e(TAG,"set key is " + editText.getText().toString());
                editor.putString(KEY, editText.getText().toString());
                editor.commit();
            }
        });

        txtCWB = (TextView)findViewById(R.id.txtCWB);
        txtCWB.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
