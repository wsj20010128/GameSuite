package com.example.gamesuite;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

import androidx.annotation.Nullable;

import com.example.gamesuite.R;
import com.example.gamesuite.MainScreenGUI;

import java.util.ArrayList;
import java.util.Random;

public class MyView extends View{

    private Context mContext;

    private Paint mPaint;  // draw board
    private Bitmap wPieces;
    private Bitmap bPieces;
    
    private ArrayList<Point> wPoints;
    private ArrayList<Point> bPoints;

    private int mPanelWith;
    private float mLineHeight;
    private static final int MAX_LINE = 10; //spots on the board
    private float radioPiece = 1.0f * 3 / 4;

    private IsChessWin mIsChessWin;
    private boolean gameOverMethod; // game over var
    private boolean wChessStart = true; // which side goes
    private boolean chronoshiftUsed = false; // ability used
    private boolean grandStarfallUsed = false; // ability used
    private ArrayList<Point> grandStarfallPoints = new ArrayList<>(2); // positions of pieces
    private static String res;
    public Activity mActivity;

    public MyView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    
    //draws game board
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        //pieces images
        wPieces = BitmapFactory.decodeResource(getResources(), R.mipmap.chess01);
        bPieces = BitmapFactory.decodeResource(getResources(), R.mipmap.chess02);
        // stores piece positions
        wPoints = new ArrayList<>();
        bPoints = new ArrayList<>();
    }
    
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWith = w; // width
        mLineHeight = mPanelWith * 1.0f / MAX_LINE;
        int piecesWidth = (int) (mLineHeight * radioPiece);
        wPieces = Bitmap.createScaledBitmap(wPieces, piecesWidth, piecesWidth, true);
        bPieces = Bitmap.createScaledBitmap(bPieces, piecesWidth, piecesWidth, true);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        mIsChessWin = new IsChessWin(mContext);
        gameOverMethod = mIsChessWin.isGameOverMethod(wPoints, bPoints);
        if (gameOverMethod) {
            finishGame();
        }
    }

    /**
     * draw board
     *
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {
        int w = mPanelWith;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    /**
     * draw pieces upon placed position
     *
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < wPoints.size(); i++) {
            Point point = wPoints.get(i);
            canvas.drawBitmap(wPieces, (point.x + 1.0f / 2 - radioPiece / 2) * mLineHeight,
                    (point.y + (1 - radioPiece) / 2) * mLineHeight, null);
        }
        // 黑棋
        for (int i = 0; i < bPoints.size(); i++) {
            Point point = bPoints.get(i);
            canvas.drawBitmap(bPieces, (point.x + (1 - radioPiece) / 2) * mLineHeight,
                    (point.y + (1 - radioPiece) / 2) * mLineHeight, null);
        }
    }

    /**
     * Continue to end screen
     */
    private void finishGame() {
        res = mIsChessWin.whiteWinFlag() ? "Tiger Win！" : "Phoenix Win！";
        Activity DungeonActivity = (Activity) mContext;
        DungeonActivity.setContentView(R.layout.activity_connect5_end_page);
        DungeonActivity.finish();
        DungeonActivity.startActivity(new Intent(DungeonActivity, Connect5EndPage.class));
        // Restart();
    }

    public void Restart() {
        wPoints.clear();
        bPoints.clear();
        gameOverMethod = false;
        invalidate();
    }

    /**
     * Get placed piece position
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameOverMethod = mIsChessWin.isGameOverMethod(wPoints, bPoints);
        if (gameOverMethod) {
            finishGame();
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point point = new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
            if (wPoints.contains(point) || bPoints.contains(point)) {
                return false;
            }
            if (wChessStart) {
                wPoints.add(point);
            } else {
                bPoints.add(point);
            }
            wChessStart = !wChessStart;
            invalidate();
            return true;
        }
        return true;
    }

    public void chronoshift() {
        if (grandStarfallUsed && grandStarfallPoints.size() == 2) {
            wPoints.add(grandStarfallPoints.get(0));
            wPoints.add(grandStarfallPoints.get(1));
        }
        if (wChessStart && wPoints.size() >= 1 && bPoints.size() >= 1
                && !chronoshiftUsed && !grandStarfallUsed) {
            wPoints.remove(wPoints.size() - 1);
            bPoints.remove(bPoints.size() - 1);
            chronoshiftUsed = true;
        }
        invalidate();
    }

    public void grandStarfall() {
        if (!wChessStart && wPoints.size() >= 2 && !grandStarfallUsed) {
            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());
            Point point1 = wPoints.remove(rand.nextInt(wPoints.size()));
            Point point2 = wPoints.remove(rand.nextInt(wPoints.size()));
            grandStarfallPoints.add(point1);
            grandStarfallPoints.add(point2);
            grandStarfallUsed = true;
            wChessStart = !wChessStart;
        }
        invalidate();
    }

    public static String getRes() {
        return res;
    }
}
