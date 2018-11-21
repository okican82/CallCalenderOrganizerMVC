package okayyildirim.com.callcalenderorganizermvc.Application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.Alarm.AlarmSetManager;
import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //needs to run once to generate it
        AlarmSetManager.setNewAlarm(getApplicationContext());
/*
        Resources res = getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("en")); // API 17+ only.
        res.updateConfiguration(conf, dm);
        //*/

        String lang = AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE");

        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(lang);
        locale.getLanguage();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        AppSettings.getInstance(getApplicationContext()).setValue("LANGUAGE", getBaseContext().getResources().getConfiguration().locale.getLanguage());

        setDefaultApplicationConfig();

    }

    private void setDefaultApplicationConfig() {

        String lang = AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE");
        String endNotEnable = AppSettings.getInstance(getApplicationContext()).getValue("END_NOTIFICATION");
        String beginNotEnable = AppSettings.getInstance(getApplicationContext()).getValue("BEGIN_NOTIFICATION");

        if(endNotEnable.isEmpty()) AppSettings.getInstance(getApplicationContext()).setValue("END_NOTIFICATION", "1");
        if(beginNotEnable.isEmpty()) AppSettings.getInstance(getApplicationContext()).setValue("BEGIN_NOTIFICATION", "1");
        if(lang.isEmpty()) AppSettings.getInstance(getApplicationContext()).setValue("LANGUAGE", getBaseContext().getResources().getConfiguration().locale.getLanguage());

        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(lang);
        locale.getLanguage();
        config.locale = locale;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


    }
}
