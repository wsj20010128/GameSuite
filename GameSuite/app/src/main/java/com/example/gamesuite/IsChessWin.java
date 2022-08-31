package com.example.gamesuite;

import android.graphics.Point;
import java.util.List;

public class IsChessWin {
    private boolean isGameOver = false; // game over
    private final int MAX_NUMBER = 5;         // 5 in a row
    private int current_num = 0;        // current in a row
    private boolean  isWhiteWinFlag;    // did one side win

    /**
     * Default constructor
     */
    public IsChessWin() {}

    /**
     * Check win
     *
     * @param x x position
     * @param y y position
     * @param points coordinate
     * @return true if the chess is won
     */
    private boolean isHorizontalFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains((new Point(x + i, y))))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains((new Point(x - i, y))))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;
        return false;
    }

    /**
     * Vertical 5 in a row
     *
     * @param x x position
     * @param y y position
     * @param points coordinate
     * @return true if there exists 5 points at a row
     */
    private boolean isVerticalFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x, y + i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;
        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x, y - i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;
        return false;
    }

    /**
     * Diagonal five in a row
     *
     * @param x x position
     * @param y y position
     * @param points coordinate
     * @return true if there exists 5 points at a diagonal
     */
    private boolean isSkewFive(int x, int y, List<Point> points) {
        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x + i, y + i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x - i, y - i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x + i, y - i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;

        for (int i = 0; i < MAX_NUMBER; i++)
            if (points.contains(new Point(x - i, y + i)))
                current_num++;
            else break;
        if (MAX_NUMBER == current_num) return true;
        current_num = 0;
        return false;
    }

    /**
     * Which 5 in a row was achieved
     *
     * @param points coordinate
     * @return true if there exists 5 points at a given row
     */
    private boolean isFiveConnect(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            if (isHorizontalFive(x, y, points))    return true;
            else if (isVerticalFive(x, y, points)) return true;
            else if (isSkewFive(x, y, points))     return true;
        }
        return false;
    }

    /**
     * Check if the left side won
     *
     * @param points coordinate
     * @return true if the left side won
     */
    private boolean isWhiteWin(List<Point> points) {
        return isFiveConnect(points);
    }

    /**
     * Check if the right side won
     *
     * @param points coordinate
     * @return true if the right side won
     */
    private boolean isBlackWin(List<Point> points) {
        return isFiveConnect(points);
    }

    /**
     * Check if the game is over
     *
     * @param whitePoints list of coordinates of white dots
     * @param blackPoints list of coordinates of black dots
     * @return true if one side has won
     */
    public boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints) {
        boolean whiteWin = isWhiteWin(whitePoints);
        boolean blackWin = isBlackWin(blackPoints);
        if (whiteWin || blackWin) {
            // return true;
            isGameOver = true;
            isWhiteWinFlag = whiteWin;
        }
        return isGameOver;
    }

    /**
     * Return the boolean of flag, checking if white is the winner
     *
     * @return true if white is the winner
     */
    public boolean whiteWinFlag() {
        return isWhiteWinFlag;
    }
}
