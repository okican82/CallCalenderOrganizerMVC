package okayyildirim.com.callcalenderorganizermvc.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Date;

import okayyildirim.com.callcalenderorganizermvc.R;


public class ListPopUp extends Dialog {

    private Context context;
    OnMyDialogResult mDialogResult;

    public ListPopUp(Context _context) {
        super(_context, R.style.FullScreenDialogTheme);
        context = _context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_list_pop_up);

        Button copy_btn = (Button) findViewById(R.id.copy_btn);
        Button delete_btn = (Button) findViewById(R.id.delete_btn);

        copy_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if( mDialogResult != null ){
                    mDialogResult.finish(1);
                    ListPopUp.this.dismiss();
                }

            }
        });


        delete_btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mDialogResult.finish(0);
                ListPopUp.this.dismiss();
            }
        });

        //mDialogResult.finish(1);

    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(int result);
    }


}
