package okayyildirim.com.callcalenderorganizermvc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Model.ListItem;
import okayyildirim.com.callcalenderorganizermvc.R;

public class CallListAdapter extends ArrayAdapter<ListItem>
{
    private Context _context;
    private final ArrayList<ListItem> items;
    private ViewGroup _parent;

    public CallListAdapter(Context context, ArrayList<ListItem> callItems)
    {
        super(context,0,callItems);
        _context = context;
        this.items = callItems;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.call_list_item,parent,false);

        ListItem item = getItem(position);

        TextView ListName = (TextView) convertView.findViewById(R.id.ListName);
        TextView ListCount = (TextView) convertView.findViewById(R.id.ListCount);
        TextView ListDate = (TextView) convertView.findViewById(R.id.ListDate);

        ListName.setText(item.getName());
        ListDate.setText(item.getDate() + " - " + item.getEndDate());

        int personCount = DB.getInstance(getContext()).getListCountById(item.getID());
        String personString  = "";


        if(personCount>0)
        {
            //ListCount.setTextColor(_context.getResources().getColor(R.color.warning_color));
            personString = String.valueOf(personCount) + " " + _context.getResources().getString(R.string.person);
            ListCount.setTextSize(20);

        }
        else
        {
            personString  = _context.getString(R.string.noPersonSelectedWarning);
            //ListCount.setTextColor(_context.getResources().getColor(R.color.warning_color));
            ListCount.setTextSize(14);
        }

        ListCount.setText(personString);

        return convertView;
    }
}
