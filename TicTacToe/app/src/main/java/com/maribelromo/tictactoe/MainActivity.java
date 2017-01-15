package com.maribelromo.tictactoe;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Game.EventListener {

    private Game mGame;

    private static final int NUM_BUTTONS = Game.BOARD_SIZE * Game.BOARD_SIZE;
    private ArrayList<Button> mTableButtons;

    private TextView mPlayerScore1;
    private ImageView mPlayerIndicator1;

    private TextView mPlayerScore2;
    private ImageView mPlayerIndicator2;

    private TextView mDrawsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGame = new Game();
        mGame.setGameListener(this);

        initializeUI();
    }

    private void initializeUI(){
        setContentView(R.layout.activity_main);

        mTableButtons = new ArrayList<>(NUM_BUTTONS);
        int id;
        for (int i = 0; i < NUM_BUTTONS; i++) {
            id = getResources().getIdentifier("button" + i, "id", getPackageName());
            mTableButtons.add((Button) findViewById(id));
        }

        mPlayerScore1 = (TextView) findViewById(R.id.playerScore1);
        mPlayerIndicator1 = (ImageView) findViewById(R.id.playerIndicator1);

        mPlayerScore2 = (TextView) findViewById(R.id.playerScore2);
        mPlayerIndicator2 = (ImageView) findViewById(R.id.playerIndicator2);

        mDrawsCount = (TextView) findViewById(R.id.drawsCount);
    }

    private void clearBoard(){
        for (Button b: mTableButtons){
            b.setText("");
        }
    }

    private int getPlayerColor (Player.Id id){
        int color = (id == Player.Id.ONE ? R.color.playerColor1 : R.color.playerColor2);
        return ContextCompat.getColor(this, color);
    }

    private void showToast (String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    /**
     * Click listener for the UI buttons in the board.
     */
    public void onBoardButtonClick(View v){
        Button button = (Button)v;

        // Grab the player who has the current turn
        Player currentPlayer = mGame.getCurrentPlayer();

        // Grab the row and column associated with this button.
        String row = (String) button.getTag(R.integer.row);
        String column = (String) button.getTag(R.integer.column);

        // Play the clicked row and column.
        boolean success = mGame.play(Integer.valueOf(row), Integer.valueOf(column));

        // Update the UI to show the successful play.
        if (success) {
            String character = String.valueOf(currentPlayer.getCharacter());
            button.setText(character);

            Player.Id id = currentPlayer.getId();
            button.setTextColor(getPlayerColor(id));
        }
    }

    public void onNewGameButtonClicked(View v) {
        mGame.restartGame();
    }

    public void onGameWin(Player player){
        Player.Id id = player.getId();
        String score = String.valueOf(player.getScore());

        // Update the player's score in the UI
        if (id == Player.Id.ONE){
            mPlayerScore1.setText(score);
        } else {
            mPlayerScore2.setText(score);
        }

        showToast("Player #" + id + " wins!");
    }

    public void onGameDraw(int drawCount){
        String draws = String.valueOf(drawCount);
        mDrawsCount.setText(draws);

        showToast("Game over. No winner!");
    }

    public void onCurrentPlayerChanged(Player player){

        // Update the current player in the UI
        if (player.getId() == Player.Id.ONE){
            mPlayerIndicator1.setVisibility(View.VISIBLE);
            mPlayerIndicator2.setVisibility(View.GONE);
        } else {
            mPlayerIndicator2.setVisibility(View.VISIBLE);
            mPlayerIndicator1.setVisibility(View.GONE);
        }
    }

    public void onGameRestart(){
        clearBoard();
    }
}
