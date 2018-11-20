package okayyildirim.com.callcalenderorganizermvc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import okayyildirim.com.callcalenderorganizermvc.Model.Person;
import okayyildirim.com.callcalenderorganizermvc.R;

public class PersonListAdapter extends ArrayAdapter<Person>
{
    private Context _context;
    private final ArrayList<Person> items;
    private ViewGroup _parent;

    public PersonListAdapter(Context context, ArrayList<Person> callItems)
    {
        super(context,0,callItems);
        _context = context;
        this.items = callItems;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_new_person,parent,false);

        Person item = getItem(position);

        TextView NameText = (TextView) convertView.findViewById(R.id.NameText);
        TextView PhoneNumberText = (TextView) convertView.findViewById(R.id.PhoneNumberText);

        NameText.setText(item.getPersonName());
        PhoneNumberText.setText(item.getPhoneNumber());

        return convertView;
    }
}
