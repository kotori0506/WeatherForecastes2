package io.keiji.labyrinth;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Map implements Ball.OnMoveListener {

    private int blockSize;

    private int horizontalBlockNum;
    private int verticalBlockNum;

    private Block[][] block;

    public Map(int w, int h, int bs) {
        blockSize = bs;
        horizontalBlockNum = w / blockSize;
        verticalBlockNum = h / blockSize;

        if (horizontalBlockNum % 2 == 0) {
            horizontalBlockNum--;
        }
        if (verticalBlockNum % 2 == 0) {
            verticalBlockNum--;
        }

        createMap();
    }

    private void createMap() {

        int[][] map = LabyrinthGenerator.getMap(255, horizontalBlockNum, verticalBlockNum);

        block = new Block[verticalBlockNum][horizontalBlockNum];
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                int type = map[y][x];
                int left = x * blockSize + 1;
                int top = y * blockSize + 1;
                int right = left + blockSize - 2;
                int bottom = top + blockSize - 2;
                block[y][x] = new Block(type, left, top, right, bottom);
            }
        }
    }

    void drawMap(Canvas canvas) {
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                block[y][x].draw(canvas);
            }
        }
    }

    @Override
    public boolean canMove(int left, int top, int right, int bottom) {

        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                if (block[y][x].type == Block.TYPE_WALL
                        && block[y][x].rect.intersects(left, top, right, bottom)) {
                    return false;
                }
            }
        }
        return true;
    }

    static class Block {

        private static final int TYPE_FLOOR = 0;
        private static final int TYPE_WALL = 1;

        private static final Paint PAINT_FLOOR = new Paint();
        private static final Paint PAINT_WALL = new Paint();

        static {
            PAINT_FLOOR.setColor(Color.GRAY);
            PAINT_WALL.setColor(Color.BLACK);
        }

        private final int type;

        final Rect rect;

        private Block(int type, int left, int top, int right, int bottom) {
            this.type = type;
            rect = new Rect(left, top, right, bottom);
        }

        private Paint getPaint() {
            switch (type) {
                case TYPE_FLOOR:
                    return PAINT_FLOOR;
                case TYPE_WALL:
                    return PAINT_WALL;

            }
            return null;
        }

        private void draw(Canvas canvas) {
            canvas.drawRect(rect, getPaint());

        }
    }
}
