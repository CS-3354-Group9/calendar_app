package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/15/2015.
 */

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CalendarMainActivity extends Activity
{
    public GregorianCalendar calendarMonth, calendarMonthCopy;
    private BaseCalendarAdapter cal_adapter;
    private TextView tv_month;
    private ListView calendar_main_list_view;
    private CalendarListAdapter list_adapter;

    static String[] eventDesc =
    {
        "Midterm Exam for CS 4349",
        "6 Year Marriage Anniversary",
        "Fake Christmas Event",
        "John Doe Birthday",
        "Cowboys vs. Saints",
        "Test Event"
    };

    static String[] eventDates =
    {
        "2015-10-22",
        "2015-10-04",
        "2015-10-12",
        "2015-10-01",
        "2015-10-18",
        "2015-11-11"
    };

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
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView( R.layout.activity_calender_land );
        }
        else
        {
            setContentView( R.layout.activity_calender_main );
            //Set the list view for the main calendar activity.
            calendar_main_list_view = (ListView) findViewById( R.id.list_view_main );
            list_adapter=new CalendarListAdapter( CalendarMainActivity.this,R.layout.list_item, CalendarInfo.date_collection_arr );
            calendar_main_list_view.setAdapter(list_adapter);
        }

        CalendarInfo.date_collection_arr=new ArrayList<>();
        //Populate the List View class with its data.
        for( int itr = 0; itr < imageId.length; itr++ )
        {
            CalendarInfo.date_collection_arr.add( new CalendarInfo(eventDates[itr], eventDesc[itr], imageId[itr]) );
        }

        //Setup Calendar.
        calendarMonth = (GregorianCalendar) GregorianCalendar.getInstance();
        calendarMonthCopy = (GregorianCalendar) calendarMonth.clone();
        cal_adapter = new BaseCalendarAdapter( this, calendarMonth,CalendarInfo.date_collection_arr );
        tv_month = (TextView) findViewById( R.id.tv_month );
        tv_month.setText( android.text.format.DateFormat.format("MMMM yyyy", calendarMonth) );

        //Setup Previous button.
        Button previous = (Button) findViewById( R.id.calendar_button_previous );
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        //Setup Next Button.
        Button next = (Button) findViewById( R.id.calendar_button_next );
        next.setOnClickListener( new OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                setNextMonth();
                refreshCalendar();
            }
        } );

        //Setup Add Button.
        Button add = (Button) findViewById( R.id.calendar_button_add );
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddEvent();
                refreshCalendar();
            }
        });

        //Set calendar GridView and create onClick to check for event dates on date selected.
        GridView gridview = (GridView) findViewById(R.id.group_view_calendar);
        gridview.setAdapter(cal_adapter);
        gridview.setOnItemClickListener( new OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parent, View v, int position, long id )
            {
                ((BaseCalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = BaseCalendarAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridValueString = separatedTime[2].replaceFirst("^0*","");
                int gridValue = Integer.parseInt(gridValueString);

                if ( (gridValue > 10) && (position < 8) )
                {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ( (gridValue < 7) && (position > 28) )
                {
                    setNextMonth();
                    refreshCalendar();
                }
                ((BaseCalendarAdapter) parent.getAdapter()).setSelected( v,position );

                ((BaseCalendarAdapter) parent.getAdapter()).checkIfEventDate( selectedGridDate, CalendarMainActivity.this );
            }
        } );
    }


    protected void setNextMonth()
    {
        if ( calendarMonth.get(GregorianCalendar.MONTH) == calendarMonth.getActualMaximum(GregorianCalendar.MONTH) )
        {
            calendarMonth.set( (calendarMonth.get(GregorianCalendar.YEAR) + 1 ), calendarMonth.getActualMinimum( GregorianCalendar.MONTH), 1 );
        }
        else
        {
            calendarMonth.set( GregorianCalendar.MONTH, calendarMonth.get(GregorianCalendar.MONTH) + 1 );
        }
    }


    protected void setPreviousMonth()
    {
        if (calendarMonth.get( GregorianCalendar.MONTH) == calendarMonth.getActualMinimum(GregorianCalendar.MONTH) )
        {
            calendarMonth.set( (calendarMonth.get(GregorianCalendar.YEAR) - 1), calendarMonth.getActualMaximum(GregorianCalendar.MONTH), 1 );
        } else
        {
            calendarMonth.set( GregorianCalendar.MONTH, calendarMonth.get(GregorianCalendar.MONTH) - 1 );
        }
    }

    protected void setAddEvent()
    {
        //create dialog box for new event
        //setContentView( R.layout.new_event);

    }


    //Function to refresh the calendar page if user clicks on a date.
    public void refreshCalendar()
    {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format( "MMMM yyyy", calendarMonth) );
    }
}