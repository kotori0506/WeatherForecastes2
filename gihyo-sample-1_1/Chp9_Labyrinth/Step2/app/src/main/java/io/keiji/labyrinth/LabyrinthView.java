package io.keiji.labyrinth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LabyrinthView extends SurfaceView implements SurfaceHolder.Callback {

    private static final Paint PAINT = new Paint();

    private Bitmap ballBitmap;

    public LabyrinthView(Context context) {
        super(context);

        getHolder().addCallback(this);

        // ボールのBitmapをロード
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
    }


    private DrawThread drawThread;

    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {

            while (!isFinished) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    drawLabyrinth(canvas);
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void startDrawThread() {
        stopDrawThread();

        drawThread = new DrawThread();
        drawThread.start();
    }

    public boolean stopDrawThread() {
        if (drawThread == null) {
            return false;
        }

        drawThread.isFinished = true;
        drawThread = null;
        return true;
    }

    public void drawLabyrinth(Canvas canvas) {
        canvas.drawBitmap(ballBitmap, 50, 50, PAINT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
    }
}
