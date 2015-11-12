package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/15/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;


public class CalendarListAdapter extends CursorAdapter
{
    public class ViewHolder
    {
        LinearLayout tv_linear_layout;
        TextView tv_event;
        TextView tv_date;
        LinearLayout tv_color;
        TextView tv_day;
    }

    private ViewHolder viewHolder;
    private  Context context;
    private  int resourceId;
    private Cursor cursor;
    LayoutInflater mInflater;
    String date;

    public CalendarListAdapter(Context context, int resourceId,Cursor cursor )
    {
        super(context, cursor, 0);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.resourceId= resourceId;
        this.cursor = cursor;
    }

    public Cursor getCursor()
    {
        return cursor;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        View v = mInflater.inflate(resourceId, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor)
    {

        String newDate = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_DATE));
        String year = newDate.substring(0, 4);
        String month = newDate.substring(4, 6);
        String day = newDate.substring(6, 8);

        String newDateString = month + "/" + day + "/" + year;

        viewHolder = new ViewHolder();
        viewHolder.tv_linear_layout = (LinearLayout) view.findViewById(R.id.list_item_linear_layout);
        viewHolder.tv_color = (LinearLayout) view.findViewById(R.id.list_item_color);
        viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);
        viewHolder.tv_event = (TextView) view.findViewById(R.id.tv_event);
        viewHolder.tv_day = (TextView) view.findViewById(R.id.day_of_event);

        if( newDate.equals(date) )
        {
            viewHolder.tv_day.setVisibility(view.GONE);
        }
        else
        {
            if(newDate != null && viewHolder.tv_day.getVisibility() != view.GONE)
            {
                viewHolder.tv_day.setText(newDateString);
                date = newDate;
            }

        }

        viewHolder.tv_color.setBackgroundColor(Color.parseColor(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_COLOR))));

        String eventDuration;
        eventDuration = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_START_TIME)) + " to " + cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_END_TIME));
        viewHolder.tv_date.setText(eventDuration);
        viewHolder.tv_event.setText(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_NAME)));
        view.setTag(cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_ROWID)));
    }

}