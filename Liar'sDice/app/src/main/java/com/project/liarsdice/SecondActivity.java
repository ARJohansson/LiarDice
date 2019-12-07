package com.project.liarsdice;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class SecondActivity extends AppCompatActivity {

    private LiarDiceGame game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view
        setContentView(R.layout.second_activity_main);

        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);
    }

}
