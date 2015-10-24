package com.cs3354group09.calendar_app;

import java.util.ArrayList;

/**
 * Created by Jacob on 10/15/2015.
 */

//This Class is to hold calendar dates and messages.
public class CalendarInfo
{
    public String date = "";
    public String eventDesc = "";
    public Integer imageId = 0;

    public static ArrayList<CalendarInfo> date_collection_arr = new ArrayList(50);

    public CalendarInfo( String date, String eventDesc, Integer imageId )
    {
        this.date = date;
        this.eventDesc = eventDesc;
        this.imageId = imageId;
    }
}
