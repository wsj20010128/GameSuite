package com.example.gamesuite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Chimp Game Class.
 *
 * @author Shijie Wang, Zixiang Xu, Zhisen An, Kaixiang Cui, Guanfeng Chen
 * @version 1.0
 */

public class ChimpGame extends AppCompatActivity implements View.OnClickListener {
    private static int num = 4;
    private static int strike = 0;
    private int click = 0;
    private final Button[] buttons = new Button[20];
    private final Map<Integer, Integer> winningMap = new HashMap<>();

    /**
     * Create the activity of chimp game page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chimp_game);

        // Initialize sound pool
        SoundEffect.initSound(this);
        // Button setup
        Button returnButton = findViewById(R.id.button_chimp_game_return);
        Button helpButton = findViewById(R.id.button_chimp_game_help);
        // Button click setup
        returnButton.setOnClickListener(backChimpStartPage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(ChimpGame.this, ChimpGameStartPage.class);
            finish();
            startActivity(intent);
        });
        helpButton.setOnClickListener(help -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChimpGame.this);
            builder.setCancelable(true);
            builder.setMessage("Click on the buttons in order based on their number. Once you start, the rest will be " +
                    "whited out. Make sure you remember all of them before starting to click!");
            builder.show();
        });

        // Initialize buttons
        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        // Hashmap setup: key - buttonNumber, value - winningNumber
        while (winningMap.size() < num) {
            int key = new Random().nextInt(buttons.length);
            if (!winningMap.containsKey(key)) {
                buttons[key].setText(String.valueOf(winningMap.size() + 1));
                buttons[key].setTextColor(Color.WHITE);
                winningMap.put(key, winningMap.size() + 1);
            }
        }
    }

    @Override
    public void onClick(View view) {
        SoundEffect.playSound(0);
        if (((Button) view).getText().toString().equals("")) {
            Toast.makeText(ChimpGame.this, "Try buttons with number!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            click++;
        }

        // Get button ID
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int buttonNum = Integer.parseInt(buttonID.substring(4));
        Integer val = winningMap.get(buttonNum);

        if (click == 1 && ((val == null) ? 0 : val) == 1) {
            for (Map.Entry<Integer, Integer> entry : winningMap.entrySet()) {
                if (entry.getValue() == 1) {
                    buttons[entry.getKey()].setTextColor(Color.parseColor("#00000000"));
                } else {
                    buttons[entry.getKey()].setTextColor(Color.WHITE);
                    buttons[entry.getKey()].setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                }
            }
        } else {
            if (click != ((val == null) ? 0 : val)) {
                strike++;
                if (strike == 3) {
                    setContentView(R.layout.activity_chimp_game_score_page);
                    Intent intent = new Intent(ChimpGame.this, ChimpGameScorePage.class);
                    finish();
                    startActivity(intent);
                } else {
                    chimpGameEndPage();
                }
            } else {
                buttons[buttonNum].setTextColor(Color.parseColor("#00000000"));
                buttons[buttonNum].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00000000")));
                if (click == num) {
                    num++;
                    strike = 0;
                    chimpGameEndPage();
                }
            }
        }
    }

    /**
     * End page of chimp game.
     */
    private void chimpGameEndPage() {
        setContentView(R.layout.activity_chimp_game_end_page);
        Intent intent = new Intent(ChimpGame.this, ChimpGameEndPage.class);
        finish();
        startActivity(intent);
    }

    /**
     * Get the number of strike.
     *
     * @return the number of strike
     */
    public static int getNum() {
        return num;
    }

    /**
     * Get the number of strike.
     *
     * @return the number of strike
     */
    public static int getStrike() {
        return strike;
    }

    /**
     * Set the initial number.
     *
     * @param num the initial number
     */
    public static void setNum(int num) {
        ChimpGame.num = num;
    }

    /**
     * Set the number of strike.
     *
     * @param strike the number of strike
     */
    public static void setStrike(int strike) {
        ChimpGame.strike = strike;
    }
}