package com.cs3354group09.calendar_app;

/**
 * Created by Jacob on 10/15/2015.
 */
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CalendarListAdapter extends ArrayAdapter<CalendarInfo>
{
    public class ViewHolder
    {
        LinearLayout tv_linear_layout;
        TextView tv_event;
        TextView tv_date;
        ImageView tv_image;
    }

    private final Context context;
    private final ArrayList<CalendarInfo> values;
    private ViewHolder viewHolder;
    private final int resourceId;
    int index = 0;

    public CalendarListAdapter(Context context, int resourceId,ArrayList<CalendarInfo> values) {
        super(context, resourceId, values);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.values = values;
        this.resourceId = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        index = position;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tv_linear_layout = (LinearLayout) convertView.findViewById(R.id.list_item_linear_layout);
            viewHolder.tv_linear_layout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_event = (TextView) convertView.findViewById(R.id.tv_event);
            viewHolder.tv_image = (ImageView) convertView.findViewById(R.id.tv_image);
            viewHolder.tv_image.setFocusable(false);
            viewHolder.tv_image.setClickable(false);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CalendarInfo list_obj=values.get(position);
        viewHolder.tv_linear_layout.setBackgroundColor(Color.parseColor("#e67e22"));
        viewHolder.tv_linear_layout.setBackgroundResource(R.drawable.rounded_corners);
        viewHolder.tv_date.setText(list_obj.date);
        viewHolder.tv_event.setText(list_obj.eventDesc);
        viewHolder.tv_image.setImageResource(list_obj.imageId);

        return convertView;
    }



}


