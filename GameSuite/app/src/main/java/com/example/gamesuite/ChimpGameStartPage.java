package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Main Activity for the Chimp Game.
 *
 * @author Shijie Wang, Zixiang Xu
 * @version 2.0
 */

public class ChimpGameStartPage extends AppCompatActivity {

    /**
     * Create the activity of chimp game start page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chimp_game_start_page);

        // Button setup
        Button startButton1 = findViewById(R.id.start_button);
        Button returnButton = findViewById(R.id.button_chimp_game_start_page_return);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Button click setup
        startButton1.setOnClickListener(startMemoryGame -> {
            ChimpGame.setNum(4);
            ChimpGame.setStrike(0);
            SoundEffect.playSound(0);
            Intent intent = new Intent(ChimpGameStartPage.this, ChimpGame.class);
            finish();
            startActivity(intent);
        });
        returnButton.setOnClickListener(returnHomePage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(ChimpGameStartPage.this, MainScreenGUI.class);
            finish();
            startActivity(intent);
        });
    }
}