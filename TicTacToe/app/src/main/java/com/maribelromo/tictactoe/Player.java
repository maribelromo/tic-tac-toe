package com.maribelromo.tictactoe;

/**
 * Created by maribel on 2017-01-14.
 *
 * Represents a player in a Tic Tac Toe game:
 *
 *  1. An id of 1 or 2.
 *  2. A character to play in the board.
 *  3. A score in the game.
 *
 */

public class Player {
    private Id mId;
    private char mCharacter;
    private int mScore;

    public static final char X = 'X';
    public static final char O = 'O';

    public enum Id {
        ONE,
        TWO;

        @Override
        public String toString() {
            return String.valueOf(ordinal() + 1);
        }
    }

    public Player (Player.Id id, char character, int score){
        mId = id;
        mCharacter = character;
        mScore = score;
    }

    public Id getId (){
        return mId;
    }

    public char getCharacter(){
        return mCharacter;
    }

    public int getScore(){
        return mScore;
    }

    public void setScore(int score){
        this.mScore = score;
    }
}
