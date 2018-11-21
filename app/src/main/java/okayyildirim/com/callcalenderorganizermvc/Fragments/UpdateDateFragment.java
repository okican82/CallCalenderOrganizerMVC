package okayyildirim.com.callcalenderorganizermvc.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.R;

public class UpdateDateFragment extends Dialog
{

    private Context context;
    OnMyDialogResult mDialogResult;

    public UpdateDateFragment(Context _context) {
        super(_context, R.style.FullScreenDialogTheme);
        context = _context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_list_date);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if( mDialogResult != null ){

                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.YEAR, year);// for 6 hour
                    calendar.set(Calendar.MONTH, month);// for 0 min
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);// for 0 sec

                    Date date = calendar.getTime();

                    mDialogResult.finish(date);
                }
                UpdateDateFragment.this.dismiss();
            }
        });

    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(Date result);
    }
}
