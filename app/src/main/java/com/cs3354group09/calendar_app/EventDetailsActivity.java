package com.cs3354group09.calendar_app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jacob on 10/28/2015.
 */
public class EventDetailsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }

        if( savedInstanceState == null )
        {
            //setContentView(R.layout.event_details);
            EventDetailFragment details = new EventDetailFragment();

            details.setArguments( getIntent().getExtras() );

            //FragmentManager fm = getFragmentManager();
            //FragmentTransaction ft = fm.beginTransaction();

            //ft.replace(R.id.event_details, details);

            getFragmentManager().beginTransaction().replace(android.R.id.content, details).commit();
        }
    }
}
