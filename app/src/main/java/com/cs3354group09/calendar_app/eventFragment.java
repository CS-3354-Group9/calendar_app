package com.cs3354group09.calendar_app;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Jacob on 10/28/2015.
 */
public class eventFragment extends ListFragment
{
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

    boolean mDuelPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Populate the List View class with its data.
        for( int itr = 0; itr < imageId.length; itr++ )
        {
            CalendarInfo.date_collection_arr.add(new CalendarInfo(eventDates[itr], eventDesc[itr], imageId[itr]));
        }

        CalendarListAdapter list_adapter = new CalendarListAdapter(getActivity(), R.layout.list_item, CalendarInfo.date_collection_arr);
        setListAdapter(list_adapter);


        View eventDetailsFrame = getActivity().findViewById(R.id.event_details);

        mDuelPane = eventDetailsFrame != null && eventDetailsFrame.getVisibility() == View.VISIBLE;

        if( savedInstanceState!= null )
        {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if(mDuelPane)
        {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
       showDetails(position);
    }


    void showDetails( int index )
    {
        mCurCheckPosition = index;

        if(mDuelPane) {

            getListView().setItemChecked(index, true);

            EventDetailFragment details = (EventDetailFragment) getFragmentManager().findFragmentById(R.id.event_details);

            if (details == null || details.getShownIndex() != index) {
                details = EventDetailFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.event_details, details);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }
        }
        else
        {
            Intent intent = new Intent();

            intent.setClass(getActivity(), EventDetailsActivity.class);

            intent.putExtra("index", index);

            startActivity(intent);
        }
    }
}
