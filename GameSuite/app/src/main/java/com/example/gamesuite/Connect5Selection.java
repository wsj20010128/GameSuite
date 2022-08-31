package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Connect5Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5_selection);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // initialize the buttons
        Button returnCharacterSelectionButton = findViewById(R.id.gameDungeon_button_character_selection_return);
        Button dungeonStartButton = findViewById(R.id.dungeon_start_button);

        // set the onClickListener for the buttons
        returnCharacterSelectionButton.setOnClickListener(returnDungeonStartPage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5Selection.this, Connect5StartPage.class);
            finish();
            startActivity(intent);
        });

        dungeonStartButton.setOnClickListener(startDungeon -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5Selection.this, Connect5.class);
            finish();
            startActivity(intent);
        });
    }
}