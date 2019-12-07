package com.project.liarsdice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

// Import listeners
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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

import android.app.Fragment;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.NumberFormat;

public class FirstFragment extends Fragment
implements OnClickListener, OnEditorActionListener,
        OnItemSelectedListener{

    // constants to save values
    protected static final String WINNER = "winner";
    protected static final String ROUND = "round_one";
    // constants to be sent to second fragment/activity & saved
    protected static final String PLAYER_BID = "playerBid";
    protected static final String BID_NUM_OF_DIE = "number of bid die";
    protected static final String BID_DIE = "bid die";
    protected static final int REQUEST_1 = 1;

    // global variables for the widgets
    public LiarDiceGame game;
    protected EditText numOnDie;
    protected TextView bid, winner;
    private Button roll, submit;
    private ImageView die1, die2, die3, die4, die5;
    protected Spinner numOfDie;
    private MainActivity activity;

    // integer that equals the player's number of bidden die
    // EX the '3' in the bid: 3 5s on the board.
    public int bidNumOfDie;
    //integer that equals the player's bidden die
    // EX the '5' in the bid: 3 5s on the board.
    public int bidDie;
    // The full player's bid that will be sent to the second activity
    public String playBid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // sets the view for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout for fragment
        View view = inflater.inflate(R.layout.first_fragment, container,
                false);

        // return view for layout
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (MainActivity)getActivity();

        // Create references to data displaying widgets
        numOnDie = (EditText) activity.findViewById(R.id.numOnDieEditText);
        numOfDie = (Spinner) activity.findViewById(R.id.numSpinner);
        bid = (TextView) activity.findViewById(R.id.onBoardTextView);
        winner = (TextView) activity.findViewById(R.id.winnerTextView);
        roll = (Button) activity.findViewById(R.id.rollButton);
        submit = (Button) activity.findViewById(R.id.submitButton);
        die1 = (ImageView) activity.findViewById(R.id.dieImageView1);
        die2 = (ImageView) activity.findViewById(R.id.dieImageView2);
        die3 = (ImageView) activity.findViewById(R.id.dieImageView3);
        die4 = (ImageView) activity.findViewById(R.id.dieImageView4);
        die5 = (ImageView) activity.findViewById(R.id.dieImageView5);

        // checks for savedInstanceState, should save the player's full bluff
        // and all the information from the rest of the game.
        String playBid, winnerText;
        int bidDie, bidNumOfDie, roundNum;
        if (savedInstanceState != null){
            playBid = savedInstanceState.getString(FirstFragment.PLAYER_BID);
            winnerText = savedInstanceState.getString(FirstFragment.WINNER);
            bidDie = savedInstanceState.getInt(FirstFragment.BID_DIE);
            bidNumOfDie = savedInstanceState.getInt(FirstFragment.BID_NUM_OF_DIE);
            roundNum = savedInstanceState.getInt(FirstFragment.ROUND);

            winner.setText(winnerText);

            NumberFormat integer = NumberFormat.getIntegerInstance();
            numOfDie.setPromptId(bidNumOfDie);
            numOnDie.setText(integer.format(bidDie));
            game.setPlayerBid(playBid);
            game.setRound(roundNum);
        }
        else {
            // start a new game
            game = new LiarDiceGame();
            activity.setGame(game);
            winner.setText("");
        }

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
        outState.putString(WINNER, game.winner());
        outState.putInt(BID_DIE, bidDie);
        outState.putInt(BID_NUM_OF_DIE, bidNumOfDie);
        outState.putInt(ROUND, game.getRound());
        super.onSaveInstanceState(outState);
    }

    // TODO: get something back from the second activity (the computer's bid or bluff)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, data.getStringExtra(COMP_BID), Toast.LENGTH_SHORT).show();
    }

    private void rollDie(int n) {
        int x, y;
        for (int i = 1; i <= n; i++){
            x = game.rollDie();
            y = dieImage(x);
            setImage(y, i);
        }
    }

    public int dieImage(int numOnpic){
        int id = 0;
            switch (numOnpic) {
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

    // Checks to see if someone bluffed
    public void checkforBluff(String whoCalled, String bidChecked){
        //TODO: if computer called bluff, compare the bid of the user against available die
        // set isBluff to true
        // if the user did not bluff remove a die from the computer and check for gameOver
        // if the user did bluff remove a die from the user
        // if user called bluff, compare the bid of the computer against available die
        // set isBluff to true
        // if the computer did not bluff remove a die from the user and check for gameOver
        // if the computer did bluff, remove a die from the user
    }

    @Override
    public void onClick(View view) {
        int n;
        if(view.getId() == R.id.rollButton){
            if(game.getRound() == 0){
                rollDie(5);
            }
            else if(game.getIsBluff() == true){
                rollDie(game.getPlayerDie());
            }
            else if(game.getIsGameOver() == true){
                game.resetGame();
                activity.newGame(game);
                rollDie(5);
            }
        }
        else if(view.getId() == R.id.submitButton){
            playBid = bidNumOfDie + " " + bidDie + bid.getText().toString();

            game.setPlayerBid(playBid);
            // Toast.makeText(getActivity(), playBid, Toast.LENGTH_LONG).show();

            // Sends information to the second activity
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            intent.putExtra(BID_NUM_OF_DIE, bidNumOfDie);
            intent.putExtra(BID_DIE, bidDie);
            intent.putExtra(PLAYER_BID, playBid);
            startActivityForResult(intent, REQUEST_1);
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
