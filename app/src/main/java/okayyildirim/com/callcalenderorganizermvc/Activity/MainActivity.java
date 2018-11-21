package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration c = new Configuration();
        c.locale = new Locale(AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE"));
        getResources().updateConfiguration(c, null);

        setContentView(R.layout.activity_main);
    }
}
