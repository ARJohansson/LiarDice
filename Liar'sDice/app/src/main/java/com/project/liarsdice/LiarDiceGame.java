package com.project.liarsdice;

import java.util.List;
import java.util.Random;

                        /****** Game Play for Application ******/
public class LiarDiceGame {

                        /****** Fields ******/
    private Random rand = new Random();
    private int round;
    private int playerDie;
    private int compDie;
    private int boardDie;
    private String compBid;
    private String compBluff;
    private String playerBid;
    private Boolean isBluff;
    private Boolean isGameOver;
    private List<Integer> compDieRolled;

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

    public LiarDiceGame(int t, int d, String cBid, String pBid) {
        round = t;
        playerDie = d;
        compDie = d;
        boardDie = d + d;
        compBid = cBid;
        playerBid = pBid;
        isBluff = false;
        isGameOver = false;
    }

                        /****** Getters and Setters ******/
    public void setPlayerBid(String bid) { playerBid = bid; }
    public String getPlayerBid() { return playerBid; }

    public String getCompBid() { return compBid; }

    public String getCompBluff() { return compBluff; }

    public void setIsBluff(Boolean bluff) { isBluff = bluff; }
    public Boolean getIsBluff() { return isBluff; }

    public void setIsGameOver(Boolean game) { isGameOver = game; }
    public Boolean getIsGameOver() { return isGameOver; }

    public void setPlayerDie(int num) { playerDie = num; }
    public int getPlayerDie() { return playerDie; }

    public void setCompDie(int num) { compDie = num; }
    public int getCompDie() { return compDie; }

    public int getBoardDie() { return compDie + playerDie; }

    public void setRound(int num) { round = num; }
    public int getRound() { return round; }


                    /****** Public Methods ******/
    // Generates a bid for the computer using components of the
    // player's bid: numOfDie and numOnDie
    public void compBid(int numOfDie, int numOnDie){
        int x, y, z;
        int bidNumOfDie;
        int bidNumOnDie;
        // gets the number of dice the computer has
        x = getCompDie();
        // gets the total number of dice
        y = getBoardDie();
        // For loop that rolls the number of dice the compute rhas
        for (int i = 1; i <= x; i++){
            z = rollDie();
            compDieRolled.add(z);
        }
        // TODO: Separate comp rolled die from bluff/bid

        // checks to see if the player bid is valid and possible
        if(compDieRolled.contains(numOnDie) &&
            numOfDie < y &&
            numOnDie <= 6){
            // if it is then computer will not call bluff
            compBluff = "";
            // chooses random numbers for the computer's bid until one
            // or both of them are larger than the numbers in the user's bid
            do {
                bidNumOfDie = rand.nextInt(y) + 1;
                bidNumOnDie = rand.nextInt(6) + 1;
            } while(bidNumOfDie < numOfDie ||
                    bidNumOnDie < numOnDie);
            // Sets the computers bid (ex: 2 1s on the board.)
            compBid = bidNumOfDie + " " + bidNumOnDie + "s on the board.";
        }
        else {
            // Otherwise computer calls bluff.
            compBluff = "You are bluffing.";
            setIsBluff(true);
            // and empties the bid
            compBid = "";
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
            if (getCompDie() > getPlayerDie()){
                winnerMessage = "Computer won round " + round + ".";
            }
            else if (getPlayerDie() > getCompDie()) {
                winnerMessage = "You won the round" + round + ".";
            }
        }
        else {
            // the game is over, so find out who won and declare it
            if(getCompDie() > getPlayerDie()) {
                winnerMessage = "Computer won the game!";
            }
            else if (getPlayerDie() > getCompDie()) {
                winnerMessage = "You won the game!";
            }
        }
        return winnerMessage;
    }

}
