package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/23/2015.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
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
    String[] eventDesc =
    {
        "Midterm Exam for CS 4349",
        "6 Year Marriage Anniversary",
        "Fake Christmas Event",
        "John Doe Birthday",
        "Cowboys vs. Saints",
        "Test Event"
    };

    //Temporary until figure out storage.
    String[] eventDates =
    {
        "2015-22-10",
        "2015-04-10",
        "2015-12-10",
        "2015-01-10",
        "2015-18-10",
        "2015-11-11"
    };

    //Temporary until figure out storage.
    Integer[] imageId =
    {
        R.drawable.applications_education,
        R.drawable.bookmark,
        R.drawable.christmass_tree,
        R.drawable.cookie,
        R.drawable.football_ball,
        0
    };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_view );
        CalendarInfo.date_collection_arr=new ArrayList<>();

        //Populate the List View class with its data.
        for( int itr = 0; itr < imageId.length; itr++ )
        {
            CalendarInfo.date_collection_arr.add(new CalendarInfo(eventDates[itr], eventDesc[itr], imageId[itr]));
        }
        getWidget();
    }


    //Function to get the calendar button. (Maybe temporary until side menu implemented.)
    public void getWidget()
    {
        calender_button = (Button) findViewById( R.id.calendar_button );
        calender_button.setOnClickListener(this);

        activity_list_view = (ListView) findViewById( R.id.list_view_main );
        list_adapter=new CalendarListAdapter( ListViewActivity.this,R.layout.list_item, CalendarInfo.date_collection_arr );
        activity_list_view.setAdapter( list_adapter );
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