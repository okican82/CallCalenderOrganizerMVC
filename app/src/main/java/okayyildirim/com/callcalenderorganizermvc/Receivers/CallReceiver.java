package okayyildirim.com.callcalenderorganizermvc.Receivers;

import android.content.Context;

import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.Model.CallObject;

/**
 * Created by okay.yildirim on 27.06.2017.
 */


public class CallReceiver extends PhonecallReceiver
{

    public CallReceiver()
    {

    }

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start)
    {
        System.out.println("onIncomingCallStarted number:" + number + "Date:" + start);
        CallObject callObject = new CallObject(number,0,start);
        ObservableObject.getInstance().updateValue(callObject);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
        System.out.println("onOutgoingCallStarted number:" + number + "Date:" + start);
        CallObject callObject = new CallObject(number,1,start);
        ObservableObject.getInstance().updateValue(callObject);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        System.out.println("onIncomingCallEnded number:" + number + "Date:" + start);
        CallObject callObject = new CallObject(number,2,start);
        ObservableObject.getInstance().updateValue(callObject);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
        System.out.println("onOutgoingCallEnded number:" + number + "Date:" + start);
        CallObject callObject = new CallObject(number,3,start);
        ObservableObject.getInstance().updateValue(callObject);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {
        System.out.println("onMissedCall number:" + number + "Date:" + start);
        CallObject callObject = new CallObject(number,4,start);
        ObservableObject.getInstance().updateValue(callObject);
    }






}
