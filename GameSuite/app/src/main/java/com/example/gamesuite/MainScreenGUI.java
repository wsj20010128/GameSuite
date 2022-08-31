package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Main Screen GUI
 *
 * @author Zhisen An, Kaixiang Cui, Zixiang Xu
 * @version 2.0
 */
public class MainScreenGUI extends AppCompatActivity {

    /**
     * Create the activity of main screen page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_gui);

        // Initialize the click sound
        SoundEffect.initSound(this);

        // Initialize buttons
        ImageButton game01 = findViewById(R.id.theMemoryGameBtn);
        ImageButton game02 = findViewById(R.id.the2048GameBtn);
        ImageButton game03 = findViewById(R.id.theConnect5GameBtn);

        Button exitBtn = findViewById(R.id.exitBtn);
        ImageButton teamInfoBtn = findViewById(R.id.teamBtn);

        // Button events setup
        game01.setOnClickListener(memory -> openGame(1));
        game02.setOnClickListener(game2048 -> openGame(2));
        game03.setOnClickListener(dungeon -> openGame(3));
        exitBtn.setOnClickListener(exist -> exitApp());
        teamInfoBtn.setOnClickListener(teamInfo -> openTeamInfo());
    }

    /**
     * Switch game based on signal:
     * 01 - Memory
     * 02 - 2048
     * 03 - Connect 5
     *
     * @param gameSwitch game switch signal
     */
    public void openGame(int gameSwitch) {

        // Play sound effect
        SoundEffect.playSound(0);

        // Switch game starter
        Intent intent = new Intent(this, TeamInfo.class);
        switch(gameSwitch) {
            case 1:
                intent = new Intent(this, ChimpGameStartPage.class);
                break;
            case 2:
                intent = new Intent(this, Game2048StartPage.class);
                break;
            case 3:
                intent = new Intent(this, Connect5StartPage.class);
        }

        // Close current window
        finish();

        // Start Game window
        startActivity(intent);
    }

    /**
     * Team Info Button Click
     */
    public void openTeamInfo() {
        SoundEffect.playSound(0);
        Intent intent = new Intent(this, TeamInfo.class);
        finish();
        startActivity(intent);
    }

    /**
     * Exit Button Click
     */
    public void exitApp(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        finish();
        startActivity(intent);
    }
}