package com.project.liardice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LiarDiceGame {

    /****** Fields ******/
    private Random rand = new Random();
    private int round;
    private int playerDie;
    private int compDie;
    private int boardDie;
    private int compBidNumOfDie;
    private int compBidNumOnDie;
    private String compBid;
    private String playerBid;
    private String win;
    private Boolean isBluff;
    private Boolean isGameOver;
    private ArrayList<Integer> compDieRolled = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));

    /****** Constructors ******/
    public LiarDiceGame() {
        round = 0;
        playerDie = 5;
        compDie = 5;
        boardDie = 10;
        compBid = "";
        playerBid = "";
        isBluff = false;
        isGameOver = false;
    }

    public LiarDiceGame(int t, int d1, int d2, String cBid, String pBid, boolean bluff, boolean game) {
        round = t;
        playerDie = d1;
        compDie = d2;
        boardDie = d1 + d2;
        compBid = cBid;
        playerBid = pBid;
        isBluff = bluff;
        isGameOver = game;
    }

    /****** Getters and Setters ******/
    public void setPlayerBid(String bid) { playerBid = bid; }
    public String getPlayerBid() { return playerBid; }

    public void setCompBid(String bid) { compBid = bid; }
    public String getCompBid() { return compBid; }

    public void setCompBidNumOfDie(int dieNUm) { compBidNumOfDie = dieNUm; }
    public int getCompBidNumOfDie() { return compBidNumOfDie; }

    public void setCompBidNumOnDie(int dieNum) { compBidNumOnDie = dieNum; }
    public int getCompBidNumOnDie() { return compBidNumOnDie; }

    public void setIsBluff(Boolean bluff) { isBluff = bluff; }
    public Boolean getIsBluff() { return isBluff; }

    public void setIsGameOver(Boolean game) { isGameOver = game; }
    public Boolean getIsGameOver() { return isGameOver; }

    public void setPlayerDie(int num) { playerDie = num; }
    public int getPlayerDie() { return playerDie; }

    public void setCompDie(int num) { compDie = num; }
    public int getCompDie() { return compDie; }

    public void setWin(String name) { win = name; }
    public String getWin() { return win; }

    public void setCompDieRolled(ArrayList<Integer> list) { compDieRolled = list; }
    public ArrayList<Integer> getCompDieRolled() { return compDieRolled; }

    public int getBoardDie() {
        boardDie = compDie + playerDie;
        return boardDie;
    }

    public void setRound(int num) { round = num; }
    public int getRound() { return round; }


    /****** Public Methods ******/
    // Rolls the computer's die
    public void compRoll(){
        int x, z;
        // gets the number of dice the computer has
        x = getCompDie();
        // For loop that rolls the number of dice the computer has
        for (int i = 1; i <= x; i++){
            z = rollDie();
            compDieRolled.set((i - 1), z);
        }
    }

    // Generates a bid for the computer using components of the
    // player's bid: numOfDie and numOnDie
    public void compBid(int numOfDie, int numOnDie){
        int y;
        // gets the total number of dice
        y = getBoardDie();

        // checks to see if the player bid is valid and possible
        if(compDieRolled.contains(numOnDie) &&
                numOfDie < y &&
                numOnDie <= 6){
            // if it is then computer will not call bluff
            setIsBluff(false);
            // chooses random numbers for the computer's bid until one
            // or both of them are larger than the numbers in the user's bid
            if(numOnDie == 6){
                do {
                    setCompBidNumOfDie(rand.nextInt(y) + 1);
                    setCompBidNumOnDie(6);
                }while (compBidNumOfDie <= numOfDie &&
                        compBidNumOfDie > getBoardDie() &&
                        compBidNumOnDie > 0);
            }
            else {
                do {
                    setCompBidNumOfDie(rand.nextInt(y) + 1);
                    setCompBidNumOnDie(rand.nextInt(6) + 1);
                } while (compBidNumOfDie <= numOfDie ||
                        compBidNumOnDie <= numOnDie &&
                        compBidNumOfDie > getBoardDie());
            }
            // Sets the computers bid (ex: 2 1s on the board.)
            setCompBid(getCompBidNumOfDie() + " " + getCompBidNumOnDie() + "s on the board.");
        }
        else {
            // Otherwise computer calls bluff.
            setIsBluff(true);
            // and empties the bid
            setCompBid("");
        }
    }

    // Resets the game
    public void resetGame() {
        round = 0;
        compBid = "";
        playerBid = "";
        playerDie = 5;
        compDie = 5;
        boardDie = 10;
        isBluff = false;
        isGameOver = false;
    }

    // Rolls the die by creating a random number and returning the result
    public int rollDie() {
        int roll = rand.nextInt(6) + 1;
        return roll;
    }

    // increments the round
    public int nextRound() {
        // Checks to see if someone called bluff or if the game is over
        if (getIsBluff() == true ||
                getIsGameOver() == true)
            round++; // if so increment the round
        else
            return round; // else don't increment and keep playing
        return round;
    }

    // checks to see if the game is over
    public void gameOver() {
        // if both the number of player dice and the
        // number of computer dice are greater than 0
        if (getPlayerDie() > 0 &&
                getCompDie() > 0)
            setIsGameOver(false); // the game is not over
        else
            setIsGameOver(true); // else the game is over
    }

    // Returns the winning message for both rounds and the game.
    public String winner() {
        String winnerMessage = "";
        // calls game over to see if the game is over
        gameOver();
        // if the game is not over, then only a round was won
        if(getIsGameOver() == false) {
            // find out who won and declare them
            winnerMessage = getWin() + " won round " + getRound();
            nextRound();
        }
        else {
            // the game is over, so find out who won and declare it
            winnerMessage = getWin() + " won the game";
        }
        return winnerMessage;
    }

}
