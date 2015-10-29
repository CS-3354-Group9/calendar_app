package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/23/2015.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewActivity extends Activity implements OnClickListener
{

    private ListView activity_list_view;
    private CalendarListAdapter list_adapter;
    private Button calender_button;

    //Temporary until figure out storage.

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView( R.layout.activity_list_view_land );
        }
        else
        {
            setContentView( R.layout.activity_list_view );
            getWidget();
        }
    }


    //Function to get the calendar button. (Maybe temporary until side menu implemented.)
    public void getWidget()
    {
        calender_button = (Button) findViewById( R.id.calendar_button );
        calender_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch ( v.getId() )
        {
            case R.id.calendar_button:
                startActivity(new Intent(ListViewActivity.this,CalendarMainActivity.class));
                break;
            default:
                break;
        }
    }


}