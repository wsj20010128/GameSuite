package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gamesuite.Connect5Selection;
import com.example.gamesuite.MainScreenGUI;
import com.example.gamesuite.R;
import com.example.gamesuite.MainScreenGUI;

public class Connect5StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect5_start_page);
        Button returnStartPageButton = findViewById(R.id.gameDungeon_button_start_page_return);
        returnStartPageButton.setOnClickListener(game2048InGameReturnStartPage -> {
            Intent intent = new Intent(Connect5StartPage.this, MainScreenGUI.class);
//            playSound();
            finish();
            startActivity(intent);
        });
        Button startButton1 = findViewById(R.id.dungeonStartGamebtn);
        startButton1.setOnClickListener(startMemoryGame -> {
//            playSound();
            Intent intent = new Intent(Connect5StartPage.this, Connect5Selection.class);
            finish();
            startActivity(intent);
        });
    }
}