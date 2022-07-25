package com.example.gamesuite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Team Info GUI
 *
 * @author Zhisen An, Kaixiang Cui, Zixiang Xu
 * @version 2.0
 */
public class TeamInfo extends AppCompatActivity {

    /**
     * Create the activity of team info page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Setup the return home button
        Button homeBtn = findViewById(R.id.ButtonHome);
        homeBtn.setOnClickListener(home -> openMain());
    }

    /**
     * Return home button setup
     */
    public void openMain() {
        SoundEffect.playSound(0);
        Intent k = new Intent(TeamInfo.this, MainScreenGUI.class);
        finish();
        startActivity(k);
    }
}