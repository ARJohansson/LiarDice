package com.project.liardice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

import static com.project.liardice.MainActivity.BID_DIE;
import static com.project.liardice.MainActivity.BID_NUM_OF_DIE;
import static com.project.liardice.MainActivity.PLAYER_BID;
import static com.project.liardice.MainActivity.ROUND;
import static com.project.liardice.MainActivity.WINNER;
import static com.project.liardice.MainActivity.bidDie;
import static com.project.liardice.MainActivity.bidNumOfDie;

public class SecondActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String COMP_BID = "computer_bid";
    private static final String COMP_BID_NUM_OF_DIE = "computer bid number of die";
    private static final String COMP_BID_NUM_ON_DIE = "computer bid number on die";

    public static LiarDiceGame game;
    public static TextView compBid;
    public static TextView compBluff;
    public Button bluff, newBid, check;
    public static int playerNumOfDie;
    static int playerDieNum;
    public static String bluffing;

    public static final String COMPUTER_BID = "computer bid";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view
        setContentView(R.layout.second_activity_main);

    }

    @Override
    public void onResume(){

        // create references to data displaying widgets
        compBid = (TextView) findViewById(R.id.compBidTextView);
        compBluff = (TextView) findViewById(R.id.compBluffTextView);
        bluff = (Button) findViewById(R.id.callBluffButton);
        newBid = (Button) findViewById(R.id.newBidButton);
        check = (Button) findViewById(R.id.checkBluffButton);

        Intent intent = getIntent();
        playerNumOfDie = intent.getExtras().getInt(BID_NUM_OF_DIE);
        playerDieNum = intent.getExtras().getInt(BID_DIE);
        continueGame();

        // Set listeners
        bluff.setOnClickListener(this);
        newBid.setOnClickListener(this);
        check.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String playerBid = savedInstanceState.getString(PLAYER_BID);
        String computerBid = savedInstanceState.getString(COMPUTER_BID);
        int bidDie = savedInstanceState.getInt(BID_DIE);
        int bidNumOfDie = savedInstanceState.getInt(BID_NUM_OF_DIE);
        int roundNum = savedInstanceState.getInt(ROUND);
        int compBidNumOfDie = savedInstanceState.getInt(COMP_BID_NUM_OF_DIE);
        int compBidNumOnDie = savedInstanceState.getInt(COMP_BID_NUM_ON_DIE);

        playerNumOfDie = bidNumOfDie;
        playerDieNum = bidDie;
        compBid.setText(computerBid);

        game.setPlayerBid(playerBid);
        game.setCompBid(computerBid);
        game.setRound(roundNum);
        game.setCompBidNumOfDie(compBidNumOfDie);
        game.setCompBidNumOnDie(compBidNumOnDie);
    }

    public static void continueGame() {

        game = MainActivity.game;
        if (game == null){
            game= new LiarDiceGame();
        }

        game.compBid(playerNumOfDie, playerDieNum);
        if (game.getIsBluff())
        {
            bluffing = "You are bluffing.";
        }
        else {
            bluffing = "";
        }
        compBluff.setText(bluffing);
        compBid.setText(game.getCompBid());

    }

    @Override
    public void onClick(View view) {
        String compBidding;
        String compBluffing;
        String makeBluff;
        if (view.getId() == R.id.callBluffButton) {
            //Toast.makeText(getActivity(), "Call Computer's Bluff Button", Toast.LENGTH_SHORT).show();
            if(game.getCompBid() == ""){
                Toast.makeText(this, "Computer did not make a bid, cannot call bluff.", Toast.LENGTH_LONG).show();
            }
            else{
                game.setIsBluff(true);
                compBidding = game.getCompBid();
                compBluffing = "";
                makeBluff = "true";
                Intent callBluff = new Intent(this, MainActivity.class);
                callBluff.putExtra(MainActivity.COMP_BLUFF, compBluffing);
                callBluff.putExtra(MainActivity.COMP_BID, compBidding);
                callBluff.putExtra(MainActivity.CALL_BLUFF, makeBluff);
                setResult(RESULT_OK, callBluff);
                finish();
            }
        }
        else if(view.getId() == R.id.newBidButton) {
            // Toast.makeText(this, "Make a New Bid Button",
                    //Toast.LENGTH_SHORT).show();
            if(game.getCompBid() == ""){
                Toast.makeText(this, "Computer did not make a bid, cannot make a new bid.",
                        Toast.LENGTH_LONG).show();
            }
            else{
                game.setIsBluff(false);
                compBidding = game.getCompBid();
                compBluffing = "";
                makeBluff = "";
                Intent callBluff = new Intent(this, MainActivity.class);
                callBluff.putExtra(MainActivity.COMP_BLUFF, compBluffing);
                callBluff.putExtra(MainActivity.COMP_BID, compBidding);
                callBluff.putExtra(MainActivity.CALL_BLUFF, makeBluff);
                setResult(RESULT_OK, callBluff);
                finish();
            }
        }
        else {
            //Toast.makeText(this, "Check Bluff Button", Toast.LENGTH_SHORT).show();
            if(game.getCompBid() != ""){
                Toast.makeText(this, "Computer did not make a bluff, cannot call check bluff.",
                        Toast.LENGTH_LONG).show();
            }
            else{
                game.setIsBluff(true);
                compBidding = "";
                compBluffing = "true";
                makeBluff = "";
                Intent callBluff = new Intent(this, MainActivity.class);
                callBluff.putExtra(MainActivity.COMP_BLUFF, compBluffing);
                callBluff.putExtra(MainActivity.COMP_BID, compBidding);
                callBluff.putExtra(MainActivity.CALL_BLUFF, makeBluff);
                setResult(RESULT_OK, callBluff);
                finish();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(PLAYER_BID, game.getPlayerBid());
        outState.putInt(BID_DIE, bidDie);
        outState.putInt(BID_NUM_OF_DIE, bidNumOfDie);
        outState.putInt(ROUND, game.getRound());
        outState.putString(COMPUTER_BID, game.getCompBid());
        outState.putInt(COMP_BID_NUM_OF_DIE, game.getCompBidNumOfDie());
        outState.putInt(COMP_BID_NUM_ON_DIE, game.getCompBidNumOnDie());
        super.onSaveInstanceState(outState);
    }
}
