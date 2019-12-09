package com.project.liardice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

// Import listeners
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

// Import the widgets
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnEditorActionListener, OnItemSelectedListener {

    // constants to save values
    protected static final String WINNER = "winner";
    protected static final String ROUND = "round_one";
    // constants to be sent to second fragment/activity & saved
    protected static final String PLAYER_BID = "playerBid";
    protected static final String BID_NUM_OF_DIE = "number of bid die";
    protected static final String BID_DIE = "bid die";
    protected static final int REQUEST_1 = 1;

    // Sent back to the first fragment from second fragment
    public static final String COMP_BLUFF = "comp called bluff";
    public static final String COMP_BID = "comp made a bid";
    public static final String CALL_BLUFF = "comp made a bid, user called bluff";

    // global variables for the widgets
    public static LiarDiceGame game;
    protected EditText numOnDie;
    protected TextView bid, winner;
    private Button roll, submit;
    private ImageView die1, die2, die3, die4, die5;
    protected Spinner numOfDie;
    private MainActivity activity;

    // integer that equals the player's number of bidden die
    // EX the '3' in the bid: 3 5s on the board.
    public static int bidNumOfDie;
    //integer that equals the player's bidden die
    // EX the '5' in the bid: 3 5s on the board.
    public static int bidDie;
    // The full player's bid that will be sent to the second activity
    public String playBid;
    // Boolean makes sure the user's bid is legal.
    public boolean checkUserBid = false;
    // creates a list of the player's dice
    public ArrayList<Integer> playerDice = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create references to data displaying widgets
        numOnDie = (EditText) findViewById(R.id.numOnDieEditText);
        numOfDie = (Spinner) findViewById(R.id.numSpinner);
        bid = (TextView) findViewById(R.id.onBoardTextView);
        winner = (TextView) findViewById(R.id.winnerTextView);
        roll = (Button) findViewById(R.id.rollButton);
        submit = (Button) findViewById(R.id.submitButton);
        die1 = (ImageView) findViewById(R.id.dieImageView1);
        die2 = (ImageView) findViewById(R.id.dieImageView2);
        die3 = (ImageView) findViewById(R.id.dieImageView3);
        die4 = (ImageView) findViewById(R.id.dieImageView4);
        die5 = (ImageView) findViewById(R.id.dieImageView5);

        // checks for savedInstanceState, should save the player's full bluff
        // and all the information from the rest of the game.
        String playBid, winnerText;
        int bidDie, bidNumOfDie, roundNum;
        if (savedInstanceState != null){
            playBid = savedInstanceState.getString(PLAYER_BID);
            winnerText = savedInstanceState.getString(WINNER);
            bidDie = savedInstanceState.getInt(BID_DIE);
            bidNumOfDie = savedInstanceState.getInt(BID_NUM_OF_DIE);
            roundNum = savedInstanceState.getInt(ROUND);

            winner.setText(winnerText + "won round");

            NumberFormat integer = NumberFormat.getIntegerInstance();
            numOfDie.setPrompt(integer.format(bidNumOfDie));
            numOnDie.setText(integer.format(bidDie));
            game.setPlayerBid(playBid);
            game.setRound(roundNum);
        }
        else {
            // start a new game
            game = new LiarDiceGame();
            winner.setText("");
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.numOfDie, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numOfDie.setAdapter(adapter);

        // Set listeners to buttons, editText, and spinner
        roll.setOnClickListener(this);
        submit.setOnClickListener(this);
        numOnDie.setOnEditorActionListener(this);
        numOfDie.setOnItemSelectedListener(this);

    }

    // saves the player's bid, the integers used in the player's bid,
    // the winner text, and the round number
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(PLAYER_BID, game.getPlayerBid());
        outState.putString(WINNER, game.getWin());
        outState.putInt(BID_DIE, bidDie);
        outState.putInt(BID_NUM_OF_DIE, bidNumOfDie);
        outState.putInt(ROUND, game.getRound());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String compCalledBluff = data.getStringExtra(COMP_BLUFF);
        String compBidding = data.getStringExtra(COMP_BID);
        String makeBluff = data.getStringExtra(CALL_BLUFF);
        if (compCalledBluff.equals("") && makeBluff.equals("")){
            game.setCompBid(compBidding);
            checkUserBid = true;
        }
        else if(compCalledBluff.equals("") && makeBluff.equals("true")){
            game.setCompBid(compBidding);
            checkForBluff("player");
            String winnerText = game.winner();
            winner.setText(winnerText);
        }
        else if(compBidding.equals("") && compCalledBluff.equals("true")){
            checkForBluff("comp");
            String winnerText = game.winner();
            winner.setText(winnerText);
        }
    }

    // Rolls the player's die and fills the playerDice array
    private void rollDie(int n) {
        int x, y;
        for (int i = 1; i <= n; i++) {
            x = game.rollDie();
            y = dieImage(x);
            setImage(y, i);
            playerDice.set((i - 1), x);
        }
    }

    public int dieImage(int numOnPic){
        int id = 0;
        switch (numOnPic) {
            case 1:
                id = R.drawable.die6side1;
                break;
            case 2:
                id = R.drawable.die6side2;
                break;
            case 3:
                id = R.drawable.die6side3;
                break;
            case 4:
                id = R.drawable.die6side4;
                break;
            case 5:
                id = R.drawable.die6side5;
                break;
            case 6:
                id = R.drawable.die6side6;
                break;
        }
        return id;
    }

    // Sets the image of the dice rolling
    public void setImage(int numOnDie, int whichDie){
        switch (whichDie){
            case 1:
                die1.setImageResource(numOnDie);
                break;
            case 2:
                die2.setImageResource(numOnDie);
                break;
            case 3:
                die3.setImageResource(numOnDie);
                break;
            case 4:
                die4.setImageResource(numOnDie);
                break;
            case 5:
                die5.setImageResource(numOnDie);
                break;
        }
    }

    public void hidePictures(int numPics){
        if (numPics > 0) {
            for (int i = numPics; i > 0; i--) {
                hideImage(i);
            }
        }
    }

    public void showHiddenPictures(int numPics){
        for (int i = 1; i <= numPics; i++){
            showImage(i);
        }
    }

    public void hideImage(int whichPic){
        switch (whichPic){
            case 1:
                die5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                die4.setVisibility(View.INVISIBLE);
                break;
            case 3:
                die3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                die2.setVisibility(View.INVISIBLE);
                break;
            case 5:
                die1.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void showImage(int whichPic){
        switch (whichPic){
            case 1:
                die5.setVisibility(View.VISIBLE);
                break;
            case 2:
                die4.setVisibility(View.VISIBLE);
                break;
            case 3:
                die3.setVisibility(View.VISIBLE);
                break;
            case 4:
                die2.setVisibility(View.VISIBLE);
                break;
            case 5:
                die1.setVisibility(View.VISIBLE);
                break;
        }
    }

    public boolean compareBid(int numOfDie, int numOnDie){
        int compNumOfDie = game.getCompBidNumOfDie();
        int compNumOnDie = game.getCompBidNumOnDie();

        if(compNumOfDie >= numOfDie && compNumOnDie >= numOnDie){
            return false;
        }
        else
            return true;
    }

    // Checks to see if someone bluffed
    public void checkForBluff(String whoCalled){
        game.setIsBluff(true);
        int x, y;
        int instance = 0;

        if (whoCalled == "comp"){
            x = bidNumOfDie;
            y = bidDie;
            if (x <= game.getBoardDie()){
                for(int i = 0; i < 5; i++){
                    if (y == playerDice.get(i) && y == game.getCompDieRolled().get(i)){
                        instance = instance + 2;
                    }
                    else if (y == playerDice.get(i) || y == game.getCompDieRolled().get(i)){
                        instance++;
                    }
                }
                if (instance >= x){
                    game.setCompDie(game.getCompDie() - 1);
                    game.setWin("Player");
                    checkUserBid = false;
                }
                else {
                    game.setPlayerDie(game.getPlayerDie() - 1);
                    game.setWin("Computer");
                    hidePictures(5 - game.getPlayerDie());
                    checkUserBid = false;
                }
            }
            else {
                game.setPlayerDie(game.getPlayerDie() - 1);
                game.setWin("Computer");
                hidePictures(5 - game.getPlayerDie());
                checkUserBid = false;
            }
        }
        else if (whoCalled == "player"){
            instance = 0;
            x = game.getCompBidNumOfDie();
            y = game.getCompBidNumOnDie();
            if( x <= game.getBoardDie()){
                for (int i = 0; i < 5; i++){
                    if (y == playerDice.get(i) && y == game.getCompDieRolled().get(i)){
                        instance = instance + 2;
                    }
                    else if (y == playerDice.get(i) || y == game.getCompDieRolled().get(i)){
                        instance++;
                    }
                }
                if (instance >= x){
                    game.setPlayerDie(game.getPlayerDie() - 1);
                    game.setWin("Computer");
                    hidePictures(5 - game.getPlayerDie());
                    checkUserBid = false;
                }
                else {
                    game.setCompDie(game.getCompDie() - 1);
                    game.setWin("Player");
                    checkUserBid = false;
                }
            }
            else {
                game.setCompDie(game.getCompDie() - 1);
                game.setWin("Player");
                checkUserBid = false;
            }
        }
    }

    @Override
    public void onClick(View view) {
        int n;
        n = game.getPlayerDie();
        if(view.getId() == R.id.rollButton){
            if(game.getRound() == 0 && game.getCompBid() == ""){
                rollDie(5);
                game.compRoll();
            }
            else if(game.getIsBluff() == true && game.getIsGameOver() == false){
                rollDie(n);
                game.compRoll();
                hidePictures( 5 - n);
                game.setIsBluff(false);
            }
            else if(game.getIsGameOver() == true){
                showHiddenPictures(5);
                game.resetGame();
                rollDie(5);
                game.compRoll();
                winner.setText("");
                Toast.makeText(this, "Starting new game.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId() == R.id.submitButton){
            if (bidDie == 0){
                Toast.makeText(this, "Please hit enter or done on your keyboard after typing a number",
                        Toast.LENGTH_SHORT).show();
            }
            else if(numOfDie.getSelectedItemPosition() > game.getBoardDie()){
                Toast.makeText(this, "There are only " + game.getBoardDie() + " dice left on the board.",
                        Toast.LENGTH_SHORT).show();
            }
            else if(checkUserBid){
                boolean trueFalse = compareBid(bidNumOfDie, bidDie);
                if(trueFalse){
                    playBid = bidNumOfDie + " " + bidDie + bid.getText().toString();

                    game.setPlayerBid(playBid);
                    // Toast.makeText(getActivity(), playBid, Toast.LENGTH_LONG).show();
                    //SecondActivity.continueGame(game);

                    // Sends information to the second activity
                    Intent intent = new Intent(this, SecondActivity.class);
                    intent.putExtra(BID_NUM_OF_DIE, bidNumOfDie);
                    intent.putExtra(BID_DIE, bidDie);
                    intent.putExtra(PLAYER_BID, playBid);
                    startActivityForResult(intent, REQUEST_1);
                }
                else{
                    Toast.makeText(this, "Computer bid was: " + game.getCompBid(),
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Please make one of your numbers larger than comp bid",
                            Toast.LENGTH_LONG).show();
                }
            }
            else {
                playBid = bidNumOfDie + " " + bidDie + bid.getText().toString();

                game.setPlayerBid(playBid);
                // Toast.makeText(getActivity(), playBid, Toast.LENGTH_LONG).show();
                //SecondActivity.continueGame(game);

                // Sends information to the second activity
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra(BID_NUM_OF_DIE, bidNumOfDie);
                intent.putExtra(BID_DIE, bidDie);
                intent.putExtra(PLAYER_BID, playBid);
                startActivityForResult(intent, REQUEST_1);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        // position starts at 0 and goes to 9
        // the number of bidden die starts at 1 and goes to 10
        bidNumOfDie = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Don't need to add code.
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            String numberOnDieString;
            // gets and sets the player's bid based on bidNumOfDie and bidDie
            numberOnDieString = numOnDie.getText().toString();
            bidDie = Integer.parseInt(numberOnDieString);
        }

        return false;
    }
}
