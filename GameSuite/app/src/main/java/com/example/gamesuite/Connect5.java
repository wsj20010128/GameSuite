package com.example.gamesuite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class Connect5 extends AppCompatActivity {

    private MyView chessView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5);

        // Initialize sound pool
        SoundEffect.initSound(this);

        chessView = findViewById(R.id.gameDungeon_view_chess);
        Button helpButton = findViewById(R.id.button_connect5_game_help);
        Button returnDungeonCharacterSelectionButton = findViewById(R.id.gameDungeon_button_character_selection_return);
        returnDungeonCharacterSelectionButton.setOnClickListener(returnDungeonCharacterSelection -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5.this, Connect5Selection.class);
            finish();
            startActivity(intent);
        });

        ImageButton dungeonChess1Button = findViewById(R.id.skill_button1);
        dungeonChess1Button.setOnClickListener(DungeonChess1 -> {
            SoundEffect.playSound(1);
            chessView.chronoshift();
        });
        ImageButton dungeonChess2Button = findViewById(R.id.skill_button2);
        dungeonChess2Button.setOnClickListener(DungeonChess2 -> {
            SoundEffect.playSound(1);
            chessView.grandStarfall();
        });

        helpButton.setOnClickListener(help -> {
            SoundEffect.playSound(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(Connect5.this);
            builder.setCancelable(true);
            builder.setPositiveButton("OK", (dialog, id) -> SoundEffect.playSound(0));
            builder.setMessage("Take turns placing pieces on the board. The first person that gets 5 in a row wins!" +
                    " \nReminder: Red Piece has an ability to rewind the board to the previous turn." +
                    " Green Piece has an ability to randomly remove 2 enemy pieces from the board. Press the piece under the character to perform the ability!");
            builder.show();
        });
    }
}