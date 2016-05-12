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

    private final Block[][] targetBlock = new Block[3][3];

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
        int verticalBlock = top / blockSize;
        int horizontalBlock = left / blockSize;

        // 検索対象のブロックを設定
        seTargetBlock(verticalBlock, horizontalBlock);

        int yLen = targetBlock.length;
        int xLen = targetBlock[0].length;

        for (int y = 0; y < yLen; y++) {
            for (int x = 0; x < xLen; x++) {
                if (targetBlock[y][x] == null) {
                    continue;
                }
                if (targetBlock[y][x].type == Block.TYPE_WALL
                        && targetBlock[y][x].rect.intersects(left, top, right, bottom)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void seTargetBlock(int verticalBlock, int horizontalBlock) {
        targetBlock[1][1] = getBlock(verticalBlock, horizontalBlock);

        targetBlock[0][0] = getBlock(verticalBlock - 1, horizontalBlock - 1);
        targetBlock[0][1] = getBlock(verticalBlock - 1, horizontalBlock);
        targetBlock[0][2] = getBlock(verticalBlock - 1, horizontalBlock + 1);

        targetBlock[1][0] = getBlock(verticalBlock, horizontalBlock - 1);
        targetBlock[1][2] = getBlock(verticalBlock, horizontalBlock + 1);

        targetBlock[2][0] = getBlock(verticalBlock + 1, horizontalBlock - 1);
        targetBlock[2][1] = getBlock(verticalBlock + 1, horizontalBlock);
        targetBlock[2][2] = getBlock(verticalBlock + 1, horizontalBlock + 1);
    }

    private Block getBlock(int y, int x) {
        if (y < 0 || x < 0 || y >= verticalBlockNum || x >= horizontalBlockNum) {
            return null;
        }
        return block[y][x];
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
