package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Fragments.UpdateDateFragment;
import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.R;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;

public class ListDetail extends AppCompatActivity {

    private int callListID;
    Button end_date_btn;
    Button begin_date_btn;
    Button save_btn;
    String endDate;
    String beginDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration c = new Configuration();
        c.locale = new Locale(AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE"));
        getResources().updateConfiguration(c, null);

        setContentView(R.layout.activity_list_detail);

        begin_date_btn = (Button) findViewById(R.id.begin_date_btn);
        begin_date_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                openBeginDate();
            }
        });

        end_date_btn = (Button) findViewById(R.id.end_date_btn);
        end_date_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                openEndDate();
            }
        });

        save_btn = (Button) findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                save();
            }
        });

        Switch begin_date_switch = (Switch) findViewById(R.id.begin_date_switch);
        Switch end_date_switch = (Switch) findViewById(R.id.end_date_switch);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            callListID = (Integer) extras.get("selectedListID");

            ListItem item = DB.getInstance(getApplicationContext()).getListItemByID(callListID);

            endDate = item.getEndDate();
            beginDate = item.getDate();

            begin_date_switch.setChecked((item.getNotifyBeginDate()==1)? true: false);
            end_date_switch.setChecked((item.getNotifyEndDate()==1)? true: false);


            setBeginDate(beginDate);
            setEndDate(endDate);
        }
    }

    private void openBeginDate() {
        UpdateDateFragment cc = new UpdateDateFragment(this);
        cc.setDialogResult(new UpdateDateFragment.OnMyDialogResult(){
            public void finish(Date result){
                beginDate = Util.convertDateToString(result);
                setBeginDate(beginDate);
            }
        });
        cc.show();
    }

    private void openEndDate() {
        UpdateDateFragment cc = new UpdateDateFragment(this);
        cc.setDialogResult(new UpdateDateFragment.OnMyDialogResult(){
            public void finish(Date result){
                endDate = Util.convertDateToString(result);
                setEndDate(endDate);
            }
        });
        cc.show();
    }

    private void setBeginDate(String dateString) {
        begin_date_btn.setText(beginDate);
    }

    private void setEndDate(String dateString) {
        end_date_btn.setText(endDate);
    }

    private void save() {
        Switch begin_date_switch = (Switch) findViewById(R.id.begin_date_switch);
        Switch end_date_switch = (Switch) findViewById(R.id.end_date_switch);

        DB.getInstance(getApplicationContext()).updateListItem(callListID,beginDate,endDate,(begin_date_switch.isChecked()? 1: 0),(end_date_switch.isChecked()? 1: 0));
        finish();

    }
}
