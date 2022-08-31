package com.example.gamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GobangStartPage extends AppCompatActivity {

    /**
     * Create the activity of game 2048 start page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobang_start_page);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Button setup
        Button startButton1 = findViewById(R.id.game_gobang_start_button);
        Button returnButton = findViewById(R.id.game_gobang_button_start_page_return);

        // Button click setup
        startButton1.setOnClickListener(startGame -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(GobangStartPage.this, Gobang.class);
            finish();
            startActivity(intent);
        });
        returnButton.setOnClickListener(returnHomePage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(GobangStartPage.this, MainScreenGUI.class);
            finish();
            startActivity(intent);
        });
    }
}