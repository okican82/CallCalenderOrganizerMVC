package okayyildirim.com.callcalenderorganizermvc.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import okayyildirim.com.callcalenderorganizermvc.Adapter.CallListAdapter;
import okayyildirim.com.callcalenderorganizermvc.R;

public class CallList extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private ListView CallLists;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        CallLists = (ListView) findViewById(R.id.CallLists);
        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);


        CallListAdapter callListAdapter = new CallListAdapter(getApplicationContext(),);

        CallLists.setAdapter(callListAdapter);
        CallLists.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        //open Add Create Activity view
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // liste görüntüleme açılacak.
    }
}
