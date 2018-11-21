package okayyildirim.com.callcalenderorganizermvc.Activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration c = new Configuration();
        c.locale = new Locale(AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE"));
        getResources().updateConfiguration(c, null);

        setContentView(R.layout.activity_settings);

        final Button language_btn = (Button) findViewById(R.id.language_btn);
        Button save_btn = (Button) findViewById(R.id.save_btn);
        final Switch begin_date_switch = (Switch) findViewById(R.id.begin_date_switch);
        final Switch end_date_switch = (Switch) findViewById(R.id.end_date_switch);

        String lang = AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE");
        String endNotEnable = AppSettings.getInstance(getApplicationContext()).getValue("END_NOTIFICATION");
        String beginNotEnable = AppSettings.getInstance(getApplicationContext()).getValue("BEGIN_NOTIFICATION");

        language_btn.setText(lang);

        if(beginNotEnable.equals("1"))
        {
            begin_date_switch.setChecked(true);
        }
        else
        {
            begin_date_switch.setChecked(false);
        }

        if(endNotEnable.equals("1"))
        {
            end_date_switch.setChecked(true);
        }
        else
        {
            end_date_switch.setChecked(false);
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AppSettings.getInstance(getApplicationContext()).setValue("END_NOTIFICATION", (end_date_switch.isChecked()? "1" : "0"));
                AppSettings.getInstance(getApplicationContext()).setValue("BEGIN_NOTIFICATION", (begin_date_switch.isChecked()? "1" : "0"));
                AppSettings.getInstance(getApplicationContext()).setValue("LANGUAGE", String.valueOf(language_btn.getText()));
                finish();

            }
        });

        language_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLanguageList();

            }


        });
    }

    private void openLanguageList() {
        //Language
        Intent languageIntent = new Intent(getApplicationContext(),Language.class);
        startActivity(languageIntent);

    }
}
