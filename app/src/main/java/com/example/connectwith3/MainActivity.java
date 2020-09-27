package com.example.connectwith3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //0 = Player O and 1 = Player X
    int activePlayer = 0;

    //To check if the game is still active
    boolean isGameActive = true;

    //Count the number of marked cells to check if the game draws
    int gameCountCells = 0;

    //Store the already used cell
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //Winning cells
    int[][] winningCells = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        //Find which cell was tapped
        int tappedCell = Integer.parseInt(counter.getTag().toString());

        //Change the cell value only if it was not tapped
        if(gameState[tappedCell] == 2 && isGameActive){
            gameState[tappedCell] = activePlayer;
            gameCountCells++;

            //Initially it will be hidden
            counter.setAlpha(0f);

            if(activePlayer == 0){
                counter.setImageResource(R.drawable.o_image);
                activePlayer = 1;
            }
            else{
                counter.setImageResource(R.drawable.x_image);
                activePlayer = 0;
            }

            //Image will show up with some animation
            counter.setAlpha(1f);
        }

        for(int[] win : winningCells){
            if(gameState[win[0]] == gameState[win[1]] &&
                    gameState[win[1]] == gameState[win[2]] &&
                    gameState[win[0]] != 2)
            {

                //When someone wins the game
                isGameActive = false;

                String winner = "Player X";

                if(gameState[win[0]] == 0){
                    winner = "Player O";
                }

                TextView winnerMessage = findViewById(R.id.play_again_text);

                //Create the winning message
                winner = winner + " Won!";
                winnerMessage.setText( winner);

                LinearLayout winMessageLayout = findViewById(R.id.play_again_layout);
                winMessageLayout.setVisibility(View.VISIBLE);
            }
        }
        if(gameCountCells == 9 && isGameActive){
            TextView winnerMessage = findViewById(R.id.play_again_text);
            //Create the winning message
            String winner = "It's a Draw!";
            winnerMessage.setText( winner);

            LinearLayout winMessageLayout = findViewById(R.id.play_again_layout);
            winMessageLayout.setVisibility(View.VISIBLE);
        }
        System.out.println(gameCountCells);
    }

    public void playAgain(View view){
        isGameActive = true;
        gameCountCells = 0;

        LinearLayout winMessageLayout = findViewById(R.id.play_again_layout);
        winMessageLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        Arrays.fill(gameState, 2);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}