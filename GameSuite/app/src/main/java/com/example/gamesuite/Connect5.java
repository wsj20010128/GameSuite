package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Connect5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5);
        Button returnStartPageButton = findViewById(R.id.gameDungeon_button_start_page_return);
        returnStartPageButton.setOnClickListener(game2048InGameReturnStartPage -> {
            Intent intent = new Intent(Connect5.this, MainScreenGUI.class);
//            playSound();
            finish();
            startActivity(intent);
        });
        Button startButton1 = findViewById(R.id.dungeonStartGamebtn);
        startButton1.setOnClickListener(startMemoryGame -> {
//            playSound();
            Intent intent = new Intent(Connect5.this, Connect5Selection.class);
            finish();
            startActivity(intent);
        });
    }
}