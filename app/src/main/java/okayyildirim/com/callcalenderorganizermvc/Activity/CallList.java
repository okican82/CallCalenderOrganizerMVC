package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import okayyildirim.com.callcalenderorganizermvc.Adapter.CallListAdapter;
import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.R;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;


import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CallList extends AppCompatActivity implements View.OnClickListener {

    private ListView CallLists;
    private Button noListHere;
    private CallListAdapter callListAdapter;
    private ArrayList<ListItem> ListItems;

    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        CallLists = (ListView) findViewById(R.id.CallLists);

        noListHere = (Button) findViewById(R.id.noListHere);
        noListHere.setOnClickListener(this);

        getSupportActionBar().setTitle(getApplicationContext().getResources().getString(R.string.list));

        ListItems = new ArrayList<ListItem>(); //DB.getInstance(getApplicationContext()).getCallLists();

        callListAdapter = new CallListAdapter(getApplicationContext(),ListItems);

        CallLists.setAdapter(callListAdapter);
        CallLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem listItem = callListAdapter.getItem(position);
                OPenSelectedList(listItem.getID());
            }

        });

        if(!checkPermission())
        {
            requestPermission();
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        resfreshLayoutView();
    }

    private void addNewCallList()
    {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setMessage(R.string.add_new_list)
                .setView(input)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        addNewCallItemToList(input.getText().toString());
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        }).show();
    }

    private void addNewCallItemToList(String listName)
    {
        DB.getInstance(getApplicationContext()).addNewListItem(listName, Util.convertDateToString(Calendar.getInstance().getTime()),Util.convertDateToString(Calendar.getInstance().getTime()));
        resfreshLayoutView();
    }

    private void resfreshLayoutView()
    {
        ListItems.clear();
        ListItems.addAll(DB.getInstance(getApplicationContext()).getCallLists());

        if(ListItems.size()>0)
        {
            CallLists.setVisibility(View.VISIBLE);
            noListHere.setVisibility(View.GONE);
        }
        else
        {
            noListHere.setVisibility(View.VISIBLE);
            CallLists.setVisibility(View.GONE);

        }

        callListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v)
    {
        addNewCallList();
    }

    private void OPenSelectedList(int id)
    {
        Intent callListeIntent = new Intent(this,DisplayCallList.class);
        callListeIntent.putExtra("selectedListID",id);
        startActivity(callListeIntent);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(CallList.this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO,READ_PHONE_STATE,MODIFY_AUDIO_SETTINGS,READ_CONTACTS,CALL_PHONE,READ_CALL_LOG}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case RequestPermissionCode:
                if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    boolean StoragePermission   = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission    = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean PhonePermission     = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ContactPermission   = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean CallPermision       = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean CallLogPermision    = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission && PhonePermission && ContactPermission && CallPermision && CallLogPermision)
                    {
                        Toast.makeText(CallList.this, "Permission Granted",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(CallList.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),READ_PHONE_STATE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),READ_CONTACTS);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(),READ_CALL_LOG);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED  ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.call_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.newList:
                addNewCallList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
