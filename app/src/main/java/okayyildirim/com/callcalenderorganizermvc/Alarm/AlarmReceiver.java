package okayyildirim.com.callcalenderorganizermvc.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import okayyildirim.com.callcalenderorganizermvc.Notifications.CalendarManagerNotifier;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        AlarmSetManager.setNewAlarm(context);
        CalendarManagerNotifier.getInstance(context).checkDates(intent);
        // burada Notification çalışacak.
    }
}
