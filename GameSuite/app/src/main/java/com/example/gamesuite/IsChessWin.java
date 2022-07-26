package com.example.gamesuite;

import android.content.Context;
import android.graphics.Point;
import java.util.List;

public class IsChessWin {
    private boolean isGameOver = false; // game over
    private int MAX_NUMBER = 5;         // 5 in a row
    private int current_num = 0;        // current in a row
    private Context mContext;
    private boolean  isWhiteWinFlag;    // did one side win

    public IsChessWin(Context context) {
        this.mContext = context;
    }
    /**
     * Check win
     *
     * @param x x position
     * @param y y position
     * @param points coordinate
     * @return true or false
     */
    private boolean isHorizontalFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains((new Point(x + i, y)))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains((new Point(x - i, y)))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;
        return false;
    }

    /**
     * Vertical 5 in a row
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean isVerticalFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x, y + i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x, y - i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;
        return false;
    }

    /**
     * Diagonal five in a row
     *
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean isSkewFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x + i, y - i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;
        return false;
    }

    /**
     * Which 5 in a row was achieved
     *
     * @param points
     * @return
     */
    private boolean isFiveConnect(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            if (isHorizontalFive(x, y, points)) {
                return true;
            } else if (isVerticalFive(x, y, points)) {
                return true;
            } else if (isSkewFive(x, y, points)) {
                return true;
            }
        }
        return false;
    }

    /**
     * If left side won
     *
     * @param points
     * @return
     */
    private boolean isWhiteWin(List<Point> points) {
        if (isFiveConnect(points)) {
            return true;
        }
        return false;
    }

    /**
     * If right side won
     *
     * @param points
     * @return
     */
    private boolean isBlackWin(List<Point> points) {
        if (isFiveConnect(points)) {
            return true;
        }
        return false;
    }

    /**
     * Is the game over
     *
     * @param whitePoints
     * @param blackPoints
     * @return true if one side has won
     */
    public boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints) {
        boolean whiteWin = isWhiteWin(whitePoints);
        boolean blackWin = isBlackWin(blackPoints);
        if (whiteWin || blackWin) {
//            return true;
            isGameOver = true;
            isWhiteWinFlag = whiteWin;
        }
        return isGameOver;
    }

    public boolean whiteWinFlag() {
        return isWhiteWinFlag;
    }
}
