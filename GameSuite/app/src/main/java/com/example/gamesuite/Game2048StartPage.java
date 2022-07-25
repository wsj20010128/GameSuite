package com.example.gamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Activity for the Game 2048.
 *
 * @author Zixiang Xu
 * @version 2.0
 */

public class Game2048StartPage extends AppCompatActivity {

    /**
     * Create the activity of game 2048 start page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_game_start_page);

        // Button setup
        Button startButton1 = findViewById(R.id.game2048_start_button);
        Button returnButton = findViewById(R.id.game2048_button_start_page_return);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Button click setup
        startButton1.setOnClickListener(startMemoryGame -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Game2048StartPage.this, Game2048.class);
            finish();
            startActivity(intent);
        });
        returnButton.setOnClickListener(returnHomePage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Game2048StartPage.this, MainScreenGUI.class);
            finish();
            startActivity(intent);
        });
    }
}