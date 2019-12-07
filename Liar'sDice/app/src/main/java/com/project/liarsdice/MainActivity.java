package com.project.liarsdice;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import android.os.Bundle;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Fields
    private LiarDiceGame game;
    public LiarDiceGame getGame() { return game; }
    public void setGame(LiarDiceGame game) { this.game = game; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstFragment firstFragment = (FirstFragment)getFragmentManager()
                .findFragmentById(R.id.first_fragment);

    }

    public void newGame(LiarDiceGame g) {
        game = g;
        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);
        //secondFragment.StartGame(game);
    }
}
