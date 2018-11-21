package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Locale;

import okayyildirim.com.callcalenderorganizermvc.Adapter.PersonListAdapter;
import okayyildirim.com.callcalenderorganizermvc.AppSettings.AppSettings;
import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Model.Person;
import okayyildirim.com.callcalenderorganizermvc.R;
import okayyildirim.com.callcalenderorganizermvc.Utiliy.Util;

import static okayyildirim.com.callcalenderorganizermvc.Utiliy.Util.arrayHasElement;

public class CreateNewCallList extends AppCompatActivity
{

    private ListView result_list;
    private SearchView search_view;
    private PersonListAdapter personListAdapter;
    private ArrayList<Person> personList;
    private int callListID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration c = new Configuration();
        c.locale = new Locale(AppSettings.getInstance(getApplicationContext()).getValue("LANGUAGE"));
        getResources().updateConfiguration(c, null);

        setContentView(R.layout.activity_create_new_call_list);

        search_view = (SearchView) findViewById(R.id.search_view);
        result_list = (ListView) findViewById(R.id.result_list);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            callListID = (Integer) extras.get("selectedListID");
            // DB den ID si bu olan listeyi seç
            getSupportActionBar().setTitle(DB.getInstance(getApplicationContext()).getListNameByID(callListID));

        }

        personList = new ArrayList<Person>();
        //personList.add(new Person("0","Okay Yıldırım"));


        personListAdapter = new PersonListAdapter(getApplicationContext(),personList);

        result_list.setAdapter(personListAdapter);
        result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //callListID
                DB.getInstance(getApplicationContext()).addToCallList(personListAdapter.getItem(position).getPhoneNumber(),callListID);
                addContactAncGoBack();

            }

        });

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFromPhoneBook(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFromPhoneBook(newText);
                return false;
            }
        });
    }

    private void addContactAncGoBack()
    {
        Intent backIntent = new Intent(this,DisplayCallList.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        backIntent.putExtra("selectedListID",callListID);
        startActivity(backIntent);
    }

    private void searchFromPhoneBook(String searhName)
    {
        personList.clear();

        if(searhName.length()>2)
        {
            personList.addAll(Util.searchFromPhoneBook(searhName, getApplicationContext()));
        }

        personListAdapter.notifyDataSetChanged();
    }
}
