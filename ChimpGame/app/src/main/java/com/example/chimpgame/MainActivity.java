package com.example.chimpgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Main Activity for the Chimp Game.
 * @author Shijie Wang
 * @userid swang953
 * @GTID 903762743
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    private Button startButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton1 = (Button) findViewById(R.id.start_button);
        startButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChimpGame.class);
                startActivity(intent);
            }
        });
    }
}