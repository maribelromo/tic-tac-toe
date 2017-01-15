package com.maribelromo.tictactoe;

/**
 * Created by maribel on 2017-01-14.
 *
 * This class provides the logic to play multiple rounds of a 2-player human vs human Tic Tac Toe
 * game. The game will alternate the starting player for each round, with player 1 starting the
 * first round.
 *
 * To play the game, the caller can invoke play() with a row and column and register a listener to
 * receive game events.
 *
 * To clear the board and start a new round, the caller can call restartGame().
 *
 * The game will keep track of each player's score and the number of draws across all rounds.
 *
 */

public class Game {

    public static final int BOARD_SIZE = 3;

    private char[][] mBoard;

    private Player mPlayer1;
    private Player mPlayer2;

    private boolean mGameOver;

    // Keeps track of the number of draws
    private int mDrawsCount;

    // Player that initiated the current round
    private Player mFirstPlayer;

    // Player whose turn it is to play
    private Player mCurrentPlayer;

    private EventListener mListener;

    public interface EventListener {
        void onGameWin(Player winner);
        void onGameDraw(int drawCount);
        void onCurrentPlayerChanged(Player player);
        void onGameRestart();
    }

    public Game(){
        mBoard = new char[BOARD_SIZE][BOARD_SIZE];

        mPlayer1 = new Player(Player.Id.ONE, Player.X, 0);
        mPlayer2 = new Player(Player.Id.TWO, Player.O, 0);

        mFirstPlayer = mPlayer1;
        mCurrentPlayer = mFirstPlayer;

        mListener = null;

        mDrawsCount = 0;
        mGameOver = false;
    }

    public void setGameListener(EventListener listener){
        mListener = listener;
    }

    public Player getCurrentPlayer(){
        return mCurrentPlayer;
    }

    /**
     * Plays the specified move [row,col] for the current player and gives the next turn to
     * the other player.
     *
     * If the specified move wins the game or if the board is full, this round will end. The scores
     * will be updated accordingly and listeners will be notified of the outcome.
     *
     * @return true if the move succeeded
     *
     */
    public boolean play(int row, int col) {
        if (!isValidMove(row, col)){
            return false;
        }

        char currentPlayerChar = mCurrentPlayer.getCharacter();
        mBoard[row][col] = currentPlayerChar;

        boolean playerWon = GameUtils.isWinningMove(mBoard, row, col, currentPlayerChar);

        if (playerWon) {
            mCurrentPlayer.setScore(mCurrentPlayer.getScore() + 1);

            mGameOver = true;

            if (mListener != null) {
                mListener.onGameWin(mCurrentPlayer);
            }
        } else if (GameUtils.isBoardFull(mBoard)) {
            mGameOver = true;
            mDrawsCount++;

            if (mListener != null) {
                mListener.onGameDraw(mDrawsCount);
            }
        } else {
            mCurrentPlayer = (mCurrentPlayer == mPlayer1 ? mPlayer2 : mPlayer1);

            if (mListener != null) {
                mListener.onCurrentPlayerChanged(mCurrentPlayer);
            }
        }

        return true;
    }

    /**
     * Clears the board and starts a new round of the game
     */
    public void restartGame(){
        mGameOver = false;
        mBoard = new char[BOARD_SIZE][BOARD_SIZE];

        mFirstPlayer = (mFirstPlayer == mPlayer1 ? mPlayer2 : mPlayer1);
        mCurrentPlayer = mFirstPlayer;

        if (mListener != null){
            mListener.onGameRestart();
            mListener.onCurrentPlayerChanged(mCurrentPlayer);
        }
    }

    /**
     * Checks if the supplied move is valid, true if:
     *
     *  @return true if
     *      1. game is not over
     *      2. the row and column indexes are valid
     *      3. the square is not already occupied
     */
    private boolean isValidMove (int row, int col){
        boolean validRow = row > 0 || row < BOARD_SIZE;
        boolean validCol = col > 0 || col < BOARD_SIZE;
        boolean squareEmpty = mBoard[row][col] == 0;

        return !mGameOver && validRow && validCol && squareEmpty;
    }
}
