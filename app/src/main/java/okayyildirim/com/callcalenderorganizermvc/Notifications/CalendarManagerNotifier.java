package okayyildirim.com.callcalenderorganizermvc.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.Activity.CallList;
import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.R;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;

public class CalendarManagerNotifier
{

    private static CalendarManagerNotifier INSTANCE = null;
    private Context context;

    public static CalendarManagerNotifier getInstance(Context contect)
    {
        if (INSTANCE == null) {
            INSTANCE = new CalendarManagerNotifier(contect.getApplicationContext());
        }
        return INSTANCE;
    }

    public CalendarManagerNotifier(Context context)
    {
        this.context = context;
    }

    public void checkDates(Intent intent)
    {
        String notificationContexTitle = "";
        String notificationContexDescription = "";

        StringBuilder notificationTitleStringBuilder = new StringBuilder();
        StringBuilder notificationDescriptionStringBuilder = new StringBuilder();
        boolean beginListExist = false;
        boolean endLisExist = false;
        ArrayList<ListItem> callList = DB.getInstance(context).getCallLists();
        for(int i=0;i<callList.size();i++)
        {
            if(callList.get(i).getNotifyBeginDate() == 1)
            {
                try {

                    if ((Util.convertStringToDate(callList.get(i).getDate()).compareTo(Calendar.getInstance().getTime()) <= 0)
                            && (callList.get(i).getNotifyBeginDate() == 1))
                    {


                        beginListExist = true;

                        notificationDescriptionStringBuilder.append(callList.get(i).getName());
                        notificationDescriptionStringBuilder.append("\n");
                        //burada başlangıç notifikastonu verilecek hazırlığı yapılacak.
                        //Notify(intent,context.getResources().getString(R.string.begin_date_title),callList.get(i).getName());
                    }
                }
                catch (Exception e)
                {

                }
            }

            if(callList.get(i).getNotifyEndDate() == 1)
            {
                try {

                    if ((Util.convertStringToDate(callList.get(i).getEndDate()).compareTo(Calendar.getInstance().getTime()) <= 0)
                            && (callList.get(i).getNotifyEndDate() == 1))
                    {
                        endLisExist = true;

                        notificationDescriptionStringBuilder.append(callList.get(i).getName());
                        notificationDescriptionStringBuilder.append("\n");
                    }
                }
                catch (Exception e)
                {

                }
            }

        }

        boolean isNotify = (beginListExist || endLisExist);


        if(beginListExist && endLisExist)
        {
            notificationTitleStringBuilder.append(context.getResources().getString(R.string.begin_date_title_and));
            notificationTitleStringBuilder.append(" ");
            notificationTitleStringBuilder.append(context.getResources().getString(R.string.end_date_title_and));


            notificationContexTitle = notificationTitleStringBuilder.toString();
            notificationContexDescription = notificationDescriptionStringBuilder.toString();

        }
        else if(beginListExist)
        {
            notificationTitleStringBuilder.append(context.getResources().getString(R.string.begin_date_title));
            notificationContexTitle = notificationTitleStringBuilder.toString();
            notificationContexDescription = notificationDescriptionStringBuilder.toString();
        }
        else if(endLisExist)
        {
            notificationTitleStringBuilder.append(context.getResources().getString(R.string.end_date_title));
            notificationContexTitle = notificationTitleStringBuilder.toString();
            notificationContexDescription = notificationDescriptionStringBuilder.toString();
        }

        if(isNotify)
        {
            Notify(intent,notificationContexTitle,notificationContexDescription);
        }
        //getCallLists
    }

    public void Notify(Intent intent,String title,String description)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        String channelId = "channel-01";
        String channelName = "Channel Name";

        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, CallList.class), PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());

    }
}
