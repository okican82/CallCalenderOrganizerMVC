package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.R;

public class Language extends AppCompatActivity {

    ArrayList<String> languageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        ListView languageList = (ListView) findViewById(R.id.language_list);

        languageArrayList = new ArrayList<String>();
        languageArrayList.add("TR-RTR");
        languageArrayList.add("EN");
        languageArrayList.add("DE");
        languageList.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, languageArrayList));

        languageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print(position);
                selectLanguage(languageArrayList.get(position));
            }

        });
    }

    private void selectLanguage(String lang)
    {
        Configuration c = new Configuration();
        c.locale = new Locale(lang);
        getResources().updateConfiguration(c, null);
        //"tr-rTR"
        AppSettings.getInstance(getApplicationContext()).setValue("LANGUAGE", getBaseContext().getResources().getConfiguration().locale.getLanguage());
        restartApp();
    }


    private void restartApp()
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
