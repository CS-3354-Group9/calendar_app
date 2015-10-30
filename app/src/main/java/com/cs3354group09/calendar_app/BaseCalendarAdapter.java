package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/15/2015.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class BaseCalendarAdapter extends BaseAdapter
{

    private Context context;
    private View previous_view;
    private java.util.Calendar month;
    private ArrayList<String> items;
    private GregorianCalendar selectedDate;

    public GregorianCalendar previousMonth;    //Used to get previous month for getting complete view.
    public GregorianCalendar previousMonthMaxSet;
    public static List<String> day_string;
    public ArrayList<CalendarInfo>  date_collection_arr;

    int firstDay;
    int maxWeekNumber;
    int maxP;
    int calMaxP;
    int monthLength;

    String itemValue, currentDateString;
    DateFormat df;


    public BaseCalendarAdapter( Context context, GregorianCalendar monthCalendar, ArrayList<CalendarInfo> date_collection_arr )
    {
        this.date_collection_arr=date_collection_arr;
        BaseCalendarAdapter.day_string = new ArrayList<>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        currentDateString = df.format(selectedDate.getTime());
        refreshDays();

    }


    public int getCount()
    {
        return day_string.size();
    }


    public Object getItem(int position)
    {
        return day_string.get(position);
    }


    public long getItemId(int position)
    {
        return 0;
    }


    // create a new view for each item referenced by the Adapter
    public View getView( int position, View convertView, ViewGroup parent )
    {
        View v = convertView;
        TextView dayView;

        if ( convertView == null )
        { // if it's not recycled, initialize some attributes
            LayoutInflater vi = ( LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
            v = vi.inflate( R.layout.cal_item, null );
        }

        dayView = (TextView) v.findViewById( R.id.date );
        String[] separatedTime = day_string.get(position).split( "-" );

        String gridValue = separatedTime[2].replaceFirst( "^0*", "" );
        if ( (Integer.parseInt(gridValue) > 1) && (position < firstDay) )
        {
            dayView.setTextColor( Color.GRAY );
            dayView.setClickable( false );
            dayView.setFocusable( false );
        }
        else if ( (Integer.parseInt(gridValue) < 7) && (position > 28) )
        {
            dayView.setTextColor( Color.GRAY );
            dayView.setClickable( false );
            dayView.setFocusable( false );
        }
        else
        {
            // setting current month's days in blue color.
            dayView.setTextColor( Color.WHITE );
        }

        if ( day_string.get(position).equals(currentDateString) )
        {
            v.setBackgroundColor( Color.parseColor("#10253A") );
        }
        else
        {
            v.setBackgroundColor( Color.parseColor("#343434") );
        }

        dayView.setText(gridValue);

        // create date string for comparison
        String date = day_string.get( position );

        if ( date.length() == 1 ) {
            //date = "0" + date;
        }
        String monthStr = "" + (month.get( GregorianCalendar.MONTH ) + 1);
        if ( monthStr.length() == 1 )
        {
            //monthStr = "0" + monthStr;
        }

        setEventView( v, position,dayView );
        return v;
    }

    //comment
    public View setSelected( View view,int pos )
    {
        if ( previous_view != null )
        {
            previous_view.setBackgroundColor(Color.parseColor( "#343434" ) );
        }

        view.setBackgroundColor( Color.parseColor("#6C7E8F") );

        int len=day_string.size();
        if ( len>pos )
        {
            if ( !day_string.get(pos).equals( currentDateString ) )
            {
                previous_view = view;
            }
        }
        return view;
    }


    public void refreshDays()
    {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault( Locale.US );
        previousMonth = (GregorianCalendar) month.clone();

        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);

        // finding number of weeks in current month.
        maxWeekNumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);

        // allocating maximum row number for the GridView.
        monthLength = maxWeekNumber * 7;
        maxP = getMaxDayofPreviousMonth(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar off day starting 24,25 ...

        //Create calendar instance for complete GridView including previous,current,next dates.
        previousMonthMaxSet = (GregorianCalendar) previousMonth.clone();

        //Set the start date as the previous months max date.
        previousMonthMaxSet.set( GregorianCalendar.DAY_OF_MONTH, calMaxP + 1 );

        //Populate the calender GridView.
        for ( int n = 0; n < monthLength; n++ )
        {
            itemValue = df.format(previousMonthMaxSet.getTime());
            previousMonthMaxSet.add( GregorianCalendar.DATE, 1 );
            day_string.add( itemValue );

        }
    }


    private int getMaxDayofPreviousMonth()
    {
        int maxPreviousMonth;
        if ( month.get(GregorianCalendar.MONTH ) == month.getActualMinimum(GregorianCalendar.MONTH) )
        {
            previousMonth.set( (month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1 );
        } else {
            previousMonth.set( GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1 );
        }
        maxPreviousMonth = previousMonth.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );

        return maxPreviousMonth;
    }


    public void setEventView(View v,int pos,TextView txt){

        int len=CalendarInfo.date_collection_arr.size();
        for (int i = 0; i < len; i++) {
            CalendarInfo cal_obj=CalendarInfo.date_collection_arr.get(i);
            String date=cal_obj.date;
            int len1=day_string.size();
            if (len1>pos) {

                if (day_string.get(pos).equals(date)) {
                    v.setBackgroundColor(Color.parseColor("#724A49"));

                    txt.setTextColor(Color.WHITE);
                }
            }}



    }


    public void checkIfEventDate(String date,final Activity act){

        int len=CalendarInfo.date_collection_arr.size();

        for ( int i = 0; i < len; i++ )
        {
            CalendarInfo cal_collection=CalendarInfo.date_collection_arr.get(i);
            String event_date=cal_collection.date;
            String event_message=cal_collection.eventDesc;

            if ( date.equals(event_date) )
            {

                Toast.makeText(context, "You have event on this date: "+event_date, Toast.LENGTH_LONG).show();
                //Create an alert dialog to display message. TEMPORARY
                //TODO replace with fragment that contains advanced details.
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Date: " + event_date)
                        .setMessage("Event: " + event_message)
                        .setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                act.finish();
                            }
                        }).show();
                break;
            }
        }
    }

}
