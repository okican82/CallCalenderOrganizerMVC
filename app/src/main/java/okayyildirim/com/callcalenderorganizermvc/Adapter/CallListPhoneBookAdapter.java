package okayyildirim.com.callcalenderorganizermvc.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import okayyildirim.com.callcalenderorganizermvc.DB.DB;
import okayyildirim.com.callcalenderorganizermvc.Model.ListPhoneBookItem;
import okayyildirim.com.callcalenderorganizermvc.Model.Person;
import okayyildirim.com.callcalenderorganizermvc.R;

public class CallListPhoneBookAdapter extends ArrayAdapter<ListPhoneBookItem>
{
    private Context _context;
    private final ArrayList<ListPhoneBookItem> items;
    private ViewGroup _parent;
    private int position;



    public CallListPhoneBookAdapter(Context context, ArrayList<ListPhoneBookItem> items)
    {
        super(context,0,items);
        _context = context;
        this.items = items;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_person_list_item,parent,false);

        this.position = position;

        final ListPhoneBookItem item = getItem(position);

        TextView NameText = (TextView) convertView.findViewById(R.id.NameText);
        TextView PhoneNumberText = (TextView) convertView.findViewById(R.id.PhoneNumberText);
        LinearLayout statusLayout = (LinearLayout) convertView.findViewById(R.id.statusLayout);

        ImageButton delete_btn = (ImageButton) convertView.findViewById(R.id.delete_btn);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(view.getRootView().getContext())
                        .setMessage(R.string.confirm_delete)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                deleteThis(position);
                            }
                        }).setNegativeButton(view.getRootView().getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
            }
        });

        ImageButton call_btn = (ImageButton) convertView.findViewById(R.id.call_btn);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(_context.getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + item.getPerson().getPhoneNumber()));
                    _context.startActivity(callIntent);
                }
            }
        });


        NameText.setText(item.getPerson().getPersonName());
        PhoneNumberText.setText(item.getPerson().getPhoneNumber());

        switch(item.getStatus()) {
            case 0:
                setbackground(R.drawable.round_grey,statusLayout);

                break;
            case 1:
                setbackground(R.drawable.round_green,statusLayout);
                NameText.setTextColor(_context.getResources().getColor(R.color.text_disable_color));
                PhoneNumberText.setTextColor(_context.getResources().getColor(R.color.text_disable_color));
                break;
            default:
                setbackground(R.drawable.round_red,statusLayout);
        }

        return convertView;
    }

    private void deleteThis(int pos)
    {
        ListPhoneBookItem item = getItem(pos);

        DB.getInstance(getContext()).deletePersonFromAList(item.getListID(),item.getPerson().getPhoneNumber());

        items.remove(pos);

        this.notifyDataSetChanged();
    }

    private void setbackground(int sourceId,LinearLayout layout)
    {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackgroundDrawable(ContextCompat.getDrawable(_context, sourceId) );
        } else {
            layout.setBackground(ContextCompat.getDrawable(_context, sourceId));
        }
    }
}
