package com.example.gamesuite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.gamesuite.R;

public class Connect5 extends AppCompatActivity {

    private MyView chessView;
    private Button returnDungeonCharacterSelectionButton;
    private ImageButton DungeonChess1Button;
    private ImageButton DungeonChess2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5);
        chessView = findViewById(R.id.gameDungeon_view_chess);
        Button helpButton = findViewById(R.id.button_connect5_game_help);
        returnDungeonCharacterSelectionButton = findViewById(R.id.gameDungeon_button_character_selection_return);
        returnDungeonCharacterSelectionButton.setOnClickListener(returnDungeonCharacterSelection -> {
            Intent intent = new Intent(Connect5.this, Connect5Selection.class);
            finish();
            startActivity(intent);
        });

        DungeonChess1Button = findViewById(R.id.skill_button1);
        DungeonChess1Button.setOnClickListener(DungeonChess1 -> {
            chessView.chronoshift();
        });
        DungeonChess2Button = findViewById(R.id.skill_button2);
        DungeonChess2Button.setOnClickListener(DungeonChess2 -> {
            chessView.grandStarfall();
        });

        helpButton.setOnClickListener(help -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Connect5.this);
            builder.setCancelable(true);
            builder.setMessage("Take turns placing pieces on the board. The first person that gets 5 in a row wins!" +
                    " \nReminder: Red Piece has an ability to rewind the board to the previous turn." +
                    " Green Piece has an ability to randomly remove 2 enemy pieces from the board. Press the piece under the character to perform the ability!");
            builder.show();
        });
    }
}