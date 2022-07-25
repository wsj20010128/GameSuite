package com.example.gamesuite;

import static android.view.Gravity.CENTER;
import static android.view.View.VISIBLE;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 * 2048 game body
 *
 * @author Zixiang Xu
 * @version 2.0
 */
public class Game2048 extends AppCompatActivity{

    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
    // Global Parameters

    // Time duration of each animation
    private final int DURATION = 150;
    // Parent board from the activity
    //     -- for all TextView controlled by the current java program
    private RelativeLayout motionBoard;
    // List of animators for scaling     - play together
    private LinkedList<Animator> scaleList = new LinkedList<>();
    // List of animators for translation - play together
    private LinkedList<Animator> translationList = new LinkedList<>();
    // Removing list of TextView after a scaling animation
    private LinkedList<TextView> scaleWasteEnd = new LinkedList<>();
    // Removing list of TextView before a translation animation
    private LinkedList<TextView> translationWasteStart = new LinkedList<>();
    // Removing list of TextView after a translation animation
    private LinkedList<TextView> translationWasteEnd = new LinkedList<>();
    // Creation List of TextView with given index, value & pattern after a translation animation
    // Key = row * 4 + col
    // Value[0] = value of the TextView
    // Value[1] = pattern:
    //     0 == Direct Add with no further remove
    //     1 == Add that will be removed after a scaling animation
    private Map<Integer, Integer[]> translationDstData = new HashMap<>();

    // Value of grids - 4x4
    private final int[][] values = new int[4][4];
    // TextView of grids - 4x4
    private final TextView[][] grids = new TextView[4][4];
    // Total score earned by the player
    // Add 2 * mergeValue for each merge
    private int score = 0;
    // Total number of grids currently on the grid board
    private int gridCount = 0;

    // Buttons of click - Up/Left/Right/Down
    private final Button[] clicks = new Button[4];
    // Click controller 01 (main controller) of buttons:
    //     -- deny the click permission when animations are still running
    private boolean clickControl01 = false;
    // Click controller 02 (help controller) of buttons:
    //     -- deny all the click controller 01 before the randomGrid()
    //     -- deny the clicks to wait until the last scaling after randomGrid() for each round
    private boolean clickControl02 = false;



    //**********************************************************************************************
    //**********************************************************************************************
    //**********************************************************************************************
    // Methods

    /**
     * Create the activity of game 2048 page.
     *
     * @param savedInstanceState current state of current manifest
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_game);

        // Initialize sound pool
        SoundEffect.initSound(this);

        // Set the relative layout of motion board
        motionBoard = findViewById(R.id.game2048_motion_board);

        // Initialize buttons
        clicks[0] = findViewById(R.id.game2048_btnLeft);
        clicks[1] = findViewById(R.id.game2048_btnRight);
        clicks[2] = findViewById(R.id.game2048_btnUp);
        clicks[3] = findViewById(R.id.game2048_btnDown);
        Button returnStartPageButton = findViewById(R.id.game2048_button_in_game_return);

        // Set click listener for 4 types of clicks
        clicks[0].setOnClickListener(clickLeft  -> clickButton(1));
        clicks[1].setOnClickListener(clickRight -> clickButton(2));
        clicks[2].setOnClickListener(clickUp    -> clickButton(3));
        clicks[3].setOnClickListener(clickDown  -> clickButton(4));
        returnStartPageButton.setOnClickListener(game2048InGameReturnStartPage -> {
            SoundEffect.playSound(0);
            Intent intent = new Intent(Game2048.this, Game2048StartPage.class);
            finish();
            startActivity(intent);
        });

        // Initial random form grids
        randomGrid(true);
    }

    /**
     * Each round move
     *
     * @param move the direction signal of move
     */
    private void clickButton(int move) {

        // Stop when cannot be clicked
        if (!clickControl01) {
            return;
        }

        // Close clickable buttons
        clickControl01 = false;

        // Play click sound of 2048
        SoundEffect.playSound(1);

        // Initialize the queue for data of merging grids
        Queue<Integer[]> queue;

        // Determine the move
        switch (move) {

            // Move left
            case 1:
                queue = gridMove(0, 4, 1, true);
                break;

            // Move right
            case 2:
                queue = gridMove(3, -1, -1, true);
                break;

            // Move up
            case 3:
                queue = gridMove(0, 4, 1, false);
                break;

            // Move down
            default:
                queue = gridMove(3, -1, -1, false);
        }

        // Play the animationSet
        playTranslation(queue);
    }

    /**
     * Determine if the player has won the game
     *
     * @return true if 2048 exists at at least one grid, otherwise false
     */
    private boolean isWin() {

        // Return true if 2048 exists at at least one grid
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (values[row][col] == 2048) {
                    return true;
                }
            }
        }

        // Otherwise, return false
        return false;
    }

    /**
     * Determine if the player has won the game
     *
     * @return true if all grids are full
     */
    private boolean isLost() {

        // Return if the total number of grids in the grid board has overwhelmed
        return gridCount >= 16;
    }

    private Queue<Integer[]> gridMove(final int valStart, final int valEnd, final int valStep,
                                      final boolean isHorizontal) {

        // Initialize queue for storing merging grids index & values
        Queue<Integer[]> queue = new LinkedList<>();

        // Initialize moving merging grid
        boolean[][] isGridMove = new boolean[4][4];

        for (int val1 = 0; val1 < 4; val1 ++) {
            int emptyCount = 0;
            int[] lastOccurIndex = null;
            for (int val2 = valStart; val2 != valEnd; val2 += valStep) {

                //**********************************************************************************
                // Part 01: Pre-processing index

                // Initialize the row & col
                int row, col;

                // Get row & col based on move mode
                if (isHorizontal) {
                    row = val1;
                    col = val2;
                } else {
                    row = val2;
                    col = val1;
                }

                // Get the index of the empty grid for emptyCount
                int rowNew = (isHorizontal) ? row :
                        (valStep > 0) ? row - emptyCount : row + emptyCount;
                int colNew = (!isHorizontal) ? col :
                        (valStep > 0) ? col - emptyCount : col + emptyCount;

                //**********************************************************************************
                // Part 02: Processing

                // Current / Last Occur / Empty Count
                // Null      Null/Exist   >=0
                if (values[row][col] == 0) {
                    emptyCount++;
                } else {
                    if (lastOccurIndex == null) {

                        // Current / Last Occur / Empty Count
                        // Exist     Null         ==0
                        if (emptyCount == 0) {
                            grids[row][col].bringToFront();
                            lastOccurIndex = new int[] {row, col};

                        // Current / Last Occur / Empty Count
                        // Exist     Null         >0
                        } else {

                            // Translation destination formation
                            translationDstData.put(rowNew * 4 + colNew,
                                                   new Integer[]{values[row][col], 0});
                            translationWasteStart.add(grids[row][col]);

                            // Signal the grid as moved
                            isGridMove[rowNew][colNew] = true;

                            // Swap values
                            values[rowNew][colNew] = values[row][col];
                            values[row][col] = 0;

                            // Update last occur
                            lastOccurIndex = new int[] {rowNew, colNew};

                            // Storing indexes & values into queue for translation animations
                            gridNew(row, col, values[rowNew][colNew],
                                    2, new boolean[]{isHorizontal, (valStep > 0)}, emptyCount);

                            // Clear old reference
                            grids[row][col] = null;
                        }

                    // Current / Last Occur / Empty Count
                    // Exist     ==Current    >=0
                    } else if (values[lastOccurIndex[0]][lastOccurIndex[1]] == values[row][col]) {

                        // Decrease the total number of grids in the grid board
                        gridCount--;

                        // Translation destination formation
                        translationWasteStart.add(grids[row][col]);

                        // Eliminate the remnant textView after animations
                        // for moved & non-moved grids
                        if (isGridMove[lastOccurIndex[0]][lastOccurIndex[1]]) {
                            translationDstData.put(lastOccurIndex[0] * 4 + lastOccurIndex[1],
                                    new Integer[]{values[row][col], 1});
                        } else {
                            scaleWasteEnd.add(grids[lastOccurIndex[0]][lastOccurIndex[1]]);
                        }

                        // Increase the total score earned by the player
                        score += values[row][col] * 2;

                        // Set && add Animator if empty count >= 0
                        gridNew(row, col, values[row][col],
                                2, new boolean[]{isHorizontal, (valStep > 0)}, 1 + emptyCount);
                        grids[row][col] = null;

                        // Update values
                        values[row][col] = 0;

                        // Set new scaleAnimation and add into animationList
                        queue.add(new Integer[] {lastOccurIndex[0],
                                                 lastOccurIndex[1],
                                                 values[lastOccurIndex[0]][lastOccurIndex[1]] * 2});

                        // Clear the last occur signal
                        lastOccurIndex = null;

                        // Set the empty count
                        emptyCount++;
                    } else {

                        // Current / Last Occur / Empty Count
                        // Exist     !=Current    ==0
                        if (emptyCount == 0) {
                            grids[row][col].bringToFront();
                            lastOccurIndex = new int[] {row, col};

                        // Current / Last Occur / Empty Count
                        // Exist     !=Current    >0
                        } else {

                            // Translation destination formation
                            translationDstData.put(rowNew * 4 + colNew,
                                                   new Integer[]{values[row][col], 0});
                            translationWasteStart.add(grids[row][col]);

                            // Signal the grid as moved
                            isGridMove[rowNew][colNew] = true;

                            // Swap values
                            values[rowNew][colNew] = values[row][col];
                            values[row][col] = 0;

                            // Update last occur
                            lastOccurIndex = new int[] {rowNew, colNew};

                            // Set && add Animator
                            gridNew(row, col, values[rowNew][colNew],
                                    2, new boolean[]{isHorizontal, (valStep > 0)}, emptyCount);

                            // Clear old reference
                            grids[row][col] = null;
                        }
                    }
                }
            }
        }
        motionBoard.invalidate();

        // Return the queue
        return queue;
    }

    /**
     * Randomly form 1-2 grids of 2/4
     */
    private void randomGrid(final boolean start) {

        // Reset randomClickControl
        clickControl02 = true;

        // Traverse through the grid board until reach the location of a random index
        Random rand = new Random();
        for (int time = 0; time < ((start) ? 2 : 1); time++) {
            boolean isAdded = false;
            do {
                int randIndex = rand.nextInt(16);
                int row = randIndex / 4;
                int col = (row == 0) ? randIndex : randIndex % (row * 4);
                if (values[row][col] == 0) {
                    int randValue = rand.nextInt(2) * 2 + 2;
                    values[row][col] = randValue;
                    gridNew(row, col, randValue, 1, new boolean[1], 0);
                    isAdded = true;
                    gridCount++;
                }
            } while (!isAdded);
        }

        // Play animation
        playScale(false);
    }

    /**
     * Create a new grid text view with randomly formed value & index
     *
     * @param row random row index given
     * @param col random col index given
     * @param value random value given
     */
    private void gridNew(final int row, final int col, final int value,
                         final int animation, final boolean[] param1, final int param2) {

        // Update values in values[][]
        values[row][col] = value;

        // Initialize text size based on input value of the grid
        // Unit: sp
        int textSize;
        if (value < 128) {
            textSize = 30;
        } else if (value < 1024) {
            textSize = 25;
        } else {
            textSize = 18;
        }

        // Initialize layout parameters for the text view
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        (int) dpToPx(62),
                        (int) dpToPx(62));
        layoutParams.setMargins(0, 0, 0, 0);

        // Initialize the background resource ID
        int resourceID =
                getResources().getIdentifier(
                        "game2048_grid_" + value,
                        "drawable",
                        getPackageName());

        // Setup the textView of a grid
        grids[row][col] = new TextView(this);
        grids[row][col].setLayoutParams(layoutParams);
        grids[row][col].setTranslationX(dpToPx(4 + 70 * col));
        grids[row][col].setTranslationY(dpToPx(4 + 70 * row));
        grids[row][col].setTextColor(Color.WHITE);
        grids[row][col].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        grids[row][col].setBackgroundResource(resourceID);
        grids[row][col].setClickable(false);
        grids[row][col].setGravity(CENTER);
        grids[row][col].setVisibility(VISIBLE);
        grids[row][col].bringToFront();

        // Set the grid value
        if (value != 0) {
            grids[row][col].setText(String.valueOf(value));
        } else {
            grids[row][col].setText("");
        }

        // Set & add animator
        if (animation == 1) {
            ObjectAnimator scaleAnimateX = ObjectAnimator.ofFloat(
                    grids[row][col], "scaleX", 0, 1);
            ObjectAnimator scaleAnimateY = ObjectAnimator.ofFloat(
                    grids[row][col], "scaleY", 0, 1);
            scaleAnimateX.setAutoCancel(true);
            scaleAnimateY.setAutoCancel(true);
            scaleList.add(scaleAnimateX);
            scaleList.add(scaleAnimateY);
        } else if (animation == 2) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(grids[row][col],
                    (param1[0]) ? "translationX" : "translationY",
                    (param1[0]) ? grids[row][col].getX() : grids[row][col].getY(),
                    ((param1[0]) ? grids[row][col].getX() : grids[row][col].getY()) +
                    spToPx(70 * param2 * ((param1[1]) ? -1 : 1)));
            animator.setAutoCancel(true);
            translationList.add(animator);
            translationWasteEnd.add(grids[row][col]);
            values[row][col] = 0;
        } else if (animation == 3) {
            scaleWasteEnd.add(grids[row][col]);
        }

        // Add the grid into the view
        motionBoard.addView(grids[row][col]);
    }


    /**
     * Play a series of scaling together
     *
     * @param isNotRandom this run is not for the random grid formation
     */
    private void playScale(final boolean isNotRandom) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.playTogether(scaleList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                motionBoard.invalidate();
                motionBoard.requestLayout();
                scaleList = new LinkedList<>();
                if (!scaleWasteEnd.isEmpty()) {
                    for (TextView grid : scaleWasteEnd) {
                        motionBoard.removeView(grid);
                    }
                    scaleWasteEnd = new LinkedList<>();
                }
                if (isNotRandom) {
                    restRound();
                }

                // Open clickable after the RandomGrid()
                if (clickControl02) {
                    clickControl01 = true;
                }
            }
        });
        animatorSet.start();
    }

    /**
     * Play a series of translations together
     */
    private void playTranslation(Queue<Integer[]> queue) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(translationList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!translationWasteStart.isEmpty()) {
                    for (TextView grid : translationWasteStart) {
                        motionBoard.removeView(grid);
                    }
                    translationWasteStart = new LinkedList<>();
                }
            }
            @Override
            public void onAnimationEnd(Animator animation) {

                // Form grids after translations
                if (!translationDstData.isEmpty()) {
                    for (Map.Entry<Integer, Integer[]> pair : translationDstData.entrySet()) {
                        int row = pair.getKey() / 4;
                        int col = (row == 0) ? pair.getKey() : pair.getKey() % (row * 4);
                        Integer[] data = pair.getValue();
                        gridNew(row, col, data[0], data[1] * 3, new boolean[2], 1);
                    }
                    translationDstData = new HashMap<>();
                }

                // Clear wasted translation
                if (!translationWasteEnd.isEmpty()) {
                    for (TextView grid : translationWasteEnd) {
                        motionBoard.removeView(grid);
                    }
                    translationWasteEnd = new LinkedList<>();
                }

                motionBoard.invalidate();
                motionBoard.requestLayout();
                translationList = new LinkedList<>();
                if (queue.isEmpty()) {
                    restRound();
                } else {
                    // Reanimate all merging grids
                    for (Integer[] data : queue) {
                        gridNew(data[0], data[1], data[2], 1, new boolean[1], 0);
                    }
                    playScale(true);
                }
            }
        });
        animatorSet.start();
    }

    /**
     * Perform the rest of a single round
     */
    private void restRound() {

        // Continue to the winner page if the player has won
        if (isWin()) {
            Game2048EndPage.setMessage("Congratulations!\nYou reaches 2048!");
            Game2048EndPage.setScore(score);
            Intent intent = new Intent(Game2048.this, Game2048EndPage.class);
            finish();
            startActivity(intent);
        }

        // Randomly form the values for next round
        randomGrid(false);

        // Continue to the loser page if the player has lost
        if (isLost()) {
            Game2048EndPage.setMessage("Oh oh...\nThe board are full, you lost!");
            Game2048EndPage.setScore(score);
            Intent intent = new Intent(Game2048.this, Game2048EndPage.class);
            finish();
            startActivity(intent);
        }
    }

    /**
     * Convert unit dp to pixel
     *
     * @param dpValue the input dp value
     * @return the converted pixel value
     */
    private float dpToPx(final int dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    /**
     * Convert unit sp to pixel
     *
     * @param spValue the input sp value
     * @return the converted pixel value
     */
    public float spToPx(final int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }
}
