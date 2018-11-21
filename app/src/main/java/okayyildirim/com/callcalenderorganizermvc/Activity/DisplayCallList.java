package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import okayyildirim.com.callcalenderorganizermvc.Adapter.CallListPhoneBookAdapter;
import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Fragments.UpdateDateFragment;
import okayyildirim.com.callcalenderorganizermvc.Model.CallObject;
import okayyildirim.com.callcalenderorganizermvc.Model.ListPhoneBookItem;
import okayyildirim.com.callcalenderorganizermvc.R;
import okayyildirim.com.callcalenderorganizermvc.Receivers.ObservableObject;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;


public class DisplayCallList extends AppCompatActivity implements Observer
{
    private ListView CallLists;
    private int callListID;
    private ImageButton startCall;
    private ArrayList<ListPhoneBookItem> personList;
    private CallListPhoneBookAdapter callListPhoneBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration c = new Configuration();
        c.locale = new Locale(AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE"));
        getResources().updateConfiguration(c, null);

        setContentView(R.layout.activity_display_call_list);

        personList = new ArrayList<ListPhoneBookItem>();

        ObservableObject.getInstance().addObserver(this);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            callListID = (Integer) extras.get("selectedListID");
            // DB den ID si bu olan listeyi seç

            personList = DB.getInstance(getApplicationContext()).getPersonListByListID(callListID);
            getSupportActionBar().setTitle(DB.getInstance(getApplicationContext()).getListNameByID(callListID));
        }

        callListPhoneBookAdapter = new CallListPhoneBookAdapter(getApplicationContext(),personList);

        startCall = (ImageButton) findViewById(R.id.startCall);
        startCall.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(personList.size()>0)
                {
                    findNextCallablePersonAndCall();
                }
                else
                {
                    alertNoPerson();
                }

            }
        });

        CallLists = (ListView) findViewById(R.id.CallLists);
        CallLists.setAdapter(callListPhoneBookAdapter);


    }

    private void findNextCallablePersonAndCall()
    {

        boolean notCalledPersonFound = false;
        int notCalledPersonIndex = 0;
        boolean notAvaiablePersonFound = false;
        int notAvaiablePersonIndex = 0;

        for(int i = 0;i<personList.size();i++)
        {
            if(personList.get(i).getStatus() == 0 )
            {
                notCalledPersonFound = true;
                notCalledPersonIndex = i;
                break;


            }
            else if(personList.get(i).getStatus() == 2 )
            {
                if(!notAvaiablePersonFound) {
                    notAvaiablePersonFound = true;
                    notAvaiablePersonIndex = i;
                }
            }
        }

        if(notCalledPersonFound)
        {
            callByIndex(notCalledPersonIndex);
        }
        else
        {
            if(notAvaiablePersonFound)
            {
                callByIndex(notAvaiablePersonIndex);
            }
            else
            {
                alertNoPerson();
            }
        }
    }

    private void alertNoPerson()
    {
        new AlertDialog.Builder(this)
                .setMessage(R.string.inform_no_person_on_the_list)
                .setPositiveButton(R.string.add_new_person, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        openAddNewActivity();
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        }).show();
    }

    private void callByIndex(int index)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + personList.get(index).getPerson().getPhoneNumber()));
            startActivity(callIntent);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    public void update(Observable observable, Object data) {
        //Toast.makeText(this, String.valueOf("activity observer " + data), Toast.LENGTH_SHORT).show();
        // burada artık numarayı biliyorum.
        // kritik konu görüşmenin gerçekleşip gerçekleşmeme durumu
        // bunu nasıl tespit ederiz?
        // call Historye bakacağız. eğer son aranan süresi >0 ise tamamdır. arama gerçekleşmiş.

        CallObject callObject = (CallObject) data;
        if(callObject.getCallState() == 2 || callObject.getCallState() == 3)
        {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED )
            {
                String[] projection = new String[] {
                        CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls.DURATION
                };

                Cursor cursor = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, null);
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(0);
                    String number = cursor.getString(1);
                    String type = cursor.getString(2); // https://developer.android.com/reference/android/provider/CallLog.Calls.html#TYPE
                    String time = cursor.getString(3); // epoch time - https://developer.android.com/reference/java/text/DateFormat.html#parse(java.lang.String
                    String duration = cursor.getString(4); // duration



                    int index = findIndexByPhoneNumber(number);

                    if(index>-1) {

                        if (Integer.parseInt(duration) > 0) {
                            updateStatusByIndex(index, 1);
                        } else {
                            updateStatusByIndex(index, 2);
                        }
                    }
                }
                cursor.close();
            }
        }
    }

    private void callNext(int index)
    {
        int nextIndex = index + 1;

        if(nextIndex < personList.size())
        {
            if(!(personList.get(index).getStatus() == 1))
            {
                callByIndex(nextIndex);
            }
            else
            {
                callNext(nextIndex);
            }
        }
        else
        {
            Toast.makeText(this, String.valueOf("Call Completed"), Toast.LENGTH_SHORT).show();
        }
    }

    private int findIndexByPhoneNumber(String phoneNumber)
    {
        for(int i = 0;i<personList.size();i++)
        {
            String perfonPhoneNumber = personList.get(i).getPerson().getPhoneNumber();
            perfonPhoneNumber = Util.removeSpaceFromString(perfonPhoneNumber," ","");
            perfonPhoneNumber = Util.removeSpaceFromString(perfonPhoneNumber,")","");
            perfonPhoneNumber = Util.removeSpaceFromString(perfonPhoneNumber,"(","");
            if(phoneNumber.equals(perfonPhoneNumber) || phoneNumber.equals("+9" + perfonPhoneNumber))
            {
                return i;
            }
        }

        return -1;
    }

    private void updateStatusByIndex(int index,int status)
    {
        personList.get(index).setStatus(status);

        DB.getInstance(getApplicationContext()).updateStatusListCall(callListID,index,status);

        callListPhoneBookAdapter.notifyDataSetChanged();

    }

    private void openAddNewActivity()
    {
        Intent createIntent = new Intent(this, CreateNewCallList.class);
        createIntent.putExtra("selectedListID",callListID);
        startActivity(createIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_new:
                openAddNewActivity();
                return true;
            case R.id.remove_list:
                confirmRemove();
                return true;
            case R.id.edit_list:
                editList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editList() {



        Intent callListeIntent = new Intent(this,ListDetail.class);
        callListeIntent.putExtra("selectedListID",callListID);
        startActivity(callListeIntent);

        /*UpdateDateFragment cc = new UpdateDateFragment(this);
        cc.setDialogResult(new UpdateDateFragment.OnMyDialogResult(){
            public void finish(String result){

            }
        });
        cc.show();
        //*/

    }

    private void removeList()
    {
        DB.getInstance(getApplicationContext()).removeListFromDB(callListID);
        this.finish();
    }

    private void confirmRemove()
    {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        removeList();
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        }).show();
    }

}
