package com.example.gamesuite;

import android.content.Context;
import android.graphics.Point;
import java.util.List;

public class IsChessWin {
    private boolean isGameOver = false; // 判断是否游戏结束
    private int MAX_NUMBER = 5;         // 设置5子连在一起胜利
    private int current_num = 0;        // 当前棋子连在一起数量
    private Context mContext;
    private boolean  isWhiteWinFlag;    // 是否白棋获胜

    public IsChessWin(Context context) {
        this.mContext = context;
    }
    /**
     * Check win
     *
     * @param x x坐标
     * @param y y坐标
     * @param points 坐标集合
     * @return true or false
     */
    private boolean isHorizontalFive(int x, int y, List<Point> points) {
        // 判断横向，向右，是否连成5子，points里面存的值为int类型，所以可以进行加一或减一运算
        for (int i = 0; i < MAX_NUMBER; i++) {
            if (points.contains((new Point(x + i, y)))) { // x轴递增，y不变
                current_num++;
            } else {
                break;
            }
        }
        if (MAX_NUMBER == current_num) {
            return true;
        }
        current_num = 0;

        // 判断横向向左是否为5子
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
     * 是否竖向五子连珠
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean isVerticalFive(int x, int y, List<Point> points) {
        // 向下五子连珠
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
        // 判断向上五子连珠
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
     * 判断斜线五子连珠
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean isSkewFive(int x, int y, List<Point> points) {
        // 判断向右往下是否五子连珠
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

        // 判断向左往上是否五子连珠
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

        // 判断向右往上是否五子连珠
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

        // 判断向左往下是否五子连珠
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
     * 判断属于哪一种五子连珠
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
     * 判断是否白棋获胜
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
     * 判断是否黑棋获胜
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
     * 判断游戏是否结束
     * @param whitePoints
     * @param blackPoints
     * @return 当白棋胜或者黑棋胜，返回true
     */
    public boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints) {
        boolean whiteWin = isWhiteWin(whitePoints);
        boolean blackWin = isBlackWin(blackPoints);
        if (whiteWin || blackWin) {
//            return true;
            isGameOver = true;
            isWhiteWinFlag = whiteWin;
        }
        return isGameOver;   // 最上面有定义默认值 isGameOver = false;
    }

    public boolean whiteWinFlag() {
        return isWhiteWinFlag;
    }
}
