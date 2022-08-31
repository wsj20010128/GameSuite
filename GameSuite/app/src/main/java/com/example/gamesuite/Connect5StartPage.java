package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Connect5StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5_start_page);

        // Initialize sound pool
        SoundEffect.initSound(this);

        Button returnStartPageButton = findViewById(R.id.gameDungeon_button_start_page_return);
        returnStartPageButton.setOnClickListener(game2048InGameReturnStartPage -> {
            Intent intent = new Intent(Connect5StartPage.this, MainScreenGUI.class);
            SoundEffect.playSound(0);
            finish();
            startActivity(intent);
        });
        Button startButton1 = findViewById(R.id.dungeonStartGameBtn);
        startButton1.setOnClickListener(startMemoryGame -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Connect5StartPage.this, Connect5Selection.class);
            finish();
            startActivity(intent);
        });
    }
}