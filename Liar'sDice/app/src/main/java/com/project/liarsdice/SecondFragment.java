package com.project.liarsdice;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;


// Imports the widgets
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

// Imports the listeners
import android.view.View.OnClickListener;

// imports the number format tool
import java.text.NumberFormat;

// imports everything for preferences and menu not already in app
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import android.app.Fragment;

import org.w3c.dom.Text;

public class SecondFragment extends Fragment
implements OnClickListener {

    public static final String COMP_BID = "computer_bid";

    public LiarDiceGame game;
    public TextView compBid, compBluff;
    public Button bluff, newBid, check;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        // create references to data displaying widgets
        compBid = (TextView) view.findViewById(R.id.compBidTextView);
        compBluff = (TextView) view.findViewById(R.id.compBluffTextView);
        bluff = (Button) view.findViewById(R.id.callBluffButton);
        newBid = (Button) view.findViewById(R.id.newBidButton);
        check = (Button) view.findViewById(R.id.checkBluffButton);

        //TODO: check computer bid/bluff

        // Set listeners
        bluff.setOnClickListener(this);
        newBid.setOnClickListener(this);
        check.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.callBluffButton) {
            Toast.makeText(getActivity(), "Call Computer's Bluff Button", Toast.LENGTH_SHORT).show();
            //TODO: only enabled if the computer made a bid and not a bluff
            // if comp made a bid, then send that bid back to the first fragment
            // to be checked
        }
        else if(view.getId() == R.id.newBidButton) {
            Toast.makeText(getActivity(), "Make a New Bid Button", Toast.LENGTH_SHORT).show();
            //TODO: only enabled if the computer made a bid and not a bluff
            // send user bid back to the first activity
        }
        else {
            Toast.makeText(getActivity(), "Check Bluff Button", Toast.LENGTH_SHORT).show();
            //TODO: check button only enabled if the computer made a bluff and not a bid
            // sends user bid and computer bid back to the first fragment to be checked
        }
    }
}
