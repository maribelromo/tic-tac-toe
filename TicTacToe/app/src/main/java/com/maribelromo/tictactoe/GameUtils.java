package com.maribelromo.tictactoe;


/**
 * Created by maribel on 2017-01-14.
 *
 * Provides helper methods to play a Tic Tac Toe game.
 *
 */

public class GameUtils {

    private GameUtils(){}

    /**
     * Checks if the given row of the board is filled with the provided character.
     */
    private static boolean isRowFilled(int row, char[][] board, char character){
        if (row < 0 || row > board.length)
            return false;

        for (int i=0; i< board[row].length; i++){
            if (board[row][i] != character){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given column of the board is filled with the provided character.
     */
    private static boolean isColumnFilled(int column, char[][] board, char character){
        if (column < 0 || column > board.length)
            return false;

        for (int i=0; i< board.length; i++){
            if (board[i][column] != character){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the left diagonal of the board is filled with the provided character.
     *
     * The left diagonal starts at [0,0] and ends at [board.length-1][board.length-1].
     */
    private static boolean isLeftDiagonalFilled(char[][] board, char character) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != character) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the right diagonal of the board is filled with the provided character.
     *
     * The right diagonal starts at [0,length-1] and ends at [length-1][0].
     */
    private static boolean isRightDiagonalFilled(char[][] board, char character) {
        for (int i=0 ; i < board.length; i++){
            if (board[i][board.length-1-i] != character){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the provided move [row,column] is a winning move for the board.
     *
     * @return true if one of the following:
     *      1. the given row is filled with the provided character
     *      2. the given column is filled with the provided character
     *      3. one of the diagonals is filled with the provided character
     */
    public static boolean isWinningMove(char[][] board, int row, int column, char character) {
        return GameUtils.isRowFilled(row, board, character)
                || GameUtils.isColumnFilled(column, board, character)
                || GameUtils.isLeftDiagonalFilled(board, character)
                || GameUtils.isRightDiagonalFilled(board, character);
    }

    /**
     * Checks if the provided board is full.
     *
     * @return true if all the squares of the board contain a character.
     */
    public static boolean isBoardFull(char[][] board) {
        for (int i=0 ; i < board.length; i++){
            for (int j=0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }
}
