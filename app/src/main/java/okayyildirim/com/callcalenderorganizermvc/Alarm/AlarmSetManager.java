package okayyildirim.com.callcalenderorganizermvc.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmSetManager
{

    public static void setNewAlarm(Context context)
    {


        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, calNow.get(Calendar.HOUR_OF_DAY));
        calSet.set(Calendar.MINUTE, calNow.get(Calendar.MINUTE) + 1);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){

            calSet.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calSet.getTimeInMillis(),pendingIntent);//*/

    }
}
