package io.keiji.shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final int MISSILE_LAUNCH_WEIGHT = 50;
    private static final long FPS = 60;

    private static final float SCORE_TEXT_SIZE = 80.0f;

    private static final long VIBRATION_LENGTH_HIT_MISSILE = 100;
    private static final long VIBRATION_LENGTH_HIT_DROID = 1000;

    private static final long SCORE_LEVEL = 100;

    private Droid droid;
    private City city;
    private final List<BaseObject> missileList = new ArrayList<BaseObject>();
    private final List<BaseObject> bulletList = new ArrayList<BaseObject>();

    private final Paint paintScore = new Paint();

    private long score;

    private final Random rand = new Random(System.currentTimeMillis());

    public interface Callback {
        public void onGameOver(long score);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private Handler handler;

    private final Vibrator vibrator;

    public GameView(Context context) {
        super(context);

        handler = new Handler();

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        paintScore.setColor(Color.BLACK);
        paintScore.setTextSize(SCORE_TEXT_SIZE);
        paintScore.setAntiAlias(true);

        getHolder().addCallback(this);
    }

    private void drawObject(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawColor(Color.WHITE);

        if (droid == null) {
            Bitmap droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droid = new Droid(droidBitmap, width, height);
        }

        if (city == null) {
            city = new City(width, height);
        }

        drawObjectList(canvas, missileList, width, height);

        drawObjectList(canvas, bulletList, width, height);

        for (int i = 0; i < missileList.size(); i++) {
            BaseObject missile = missileList.get(i);

            for (int j = 0; j < bulletList.size(); j++) {
                BaseObject bullet = bulletList.get(j);

                if (bullet.isHit(missile)) {
                    missile.hit();
                    bullet.hit();

                    vibrator.vibrate(VIBRATION_LENGTH_HIT_MISSILE);

                    score += 10;
                }
            }
        }

        for (int i = 0; i < missileList.size(); i++) {
            BaseObject missile = missileList.get(i);

            if (!missile.isAvailable(width, height)) {
                continue;
            }

            boolean gameOver = false;

            if (droid.isHit(missile)) {
                missile.hit();
                droid.hit();
                gameOver = true;
            } else if (city.isHit(missile)) {
                missile.hit();
                city.hit();
                gameOver = true;
            }

            if (gameOver) {
                vibrator.vibrate(VIBRATION_LENGTH_HIT_DROID);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onGameOver(score);
                    }
                });

                break;
            }
        }

        // ミサイルの発射
        if (rand.nextInt(MISSILE_LAUNCH_WEIGHT) == 0) {
            long count = score / SCORE_LEVEL + 1;
            for (int i = 0; i < count; i++) {
                launchMissile();
            }
        }

        city.draw(canvas);

        droid.draw(canvas);

        // スコアの表示
        canvas.drawText("Score: " + score, 0, SCORE_TEXT_SIZE, paintScore);
    }

    private static void drawObjectList(Canvas canvas, List<BaseObject> objectList, int width, int height) {
        for (int i = 0; i < objectList.size(); i++) {
            BaseObject object = objectList.get(i);
            if (object.isAvailable(width, height)) {
                object.move();
                object.draw(canvas);
            } else {
                objectList.remove(object);
                i--;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fire(event.getY(), event.getX());
                break;
        }

        return super.onTouchEvent(event);
    }

    private void fire(float y, float x) {
        float alignX = (x - droid.rect.centerX()) / Math.abs(y - droid.rect.centerY());

        Bullet bullet = new Bullet(alignX, droid.rect);
        bulletList.add(0, bullet);
    }

    private void launchMissile() {
        int fromX = rand.nextInt(getWidth());
        int toX = rand.nextInt(getWidth());

        float alignX = (toX - fromX) / (float) getHeight();
        Missile missile = new Missile(fromX, alignX);
        missileList.add(missile);
    }

    private DrawThread drawThread;

    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {
            super.run();

            SurfaceHolder holder = getHolder();
            while (!isFinished) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawObject(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(1000 / FPS);
                } catch (InterruptedException e) {
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
