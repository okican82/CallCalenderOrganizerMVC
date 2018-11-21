package okayyildirim.com.callcalenderorganizermvc.AppSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings
{

    private static AppSettings INSTANCE = null;
    private Context context;

    public static AppSettings getInstance(Context contect)
    {
        if (INSTANCE == null) {
            INSTANCE = new AppSettings(contect.getApplicationContext());
        }
        return INSTANCE;
    }

    public AppSettings(Context context)
    {
        this.context = context;
    }

    public String getValue(String key)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String result = preferences.getString(key, null);
        if(result == null) result = "";
        return result;
    }

    public void setValue(String key, String value)
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }




}
