package io.keiji.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Droid.Callback {

    private static final int START_GROUND_HEIGHT = 50;
    private static final int GROUND_MOVE_TO_LEFT = 10;

    private static final int MAX_TOUCH_TIME = 500; // msec

    private Droid droid;
    private Ground ground;

    private long touchDownStartTime;

    public GameView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (droid == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droid = new Droid(bitmap, 0, 0, this);
        }

        if (ground == null) {
            ground = new Ground(0, height - START_GROUND_HEIGHT, width, height);
        }

        ground.move(GROUND_MOVE_TO_LEFT);
        droid.move();

        droid.draw(canvas);
        ground.draw(canvas);

        invalidate();
    }

    @Override
    public int getDistanceFromGround(Droid droid) {
        boolean horizontal = !(droid.rect.left >= ground.rect.right || droid.rect.right <= ground.rect.left);

        if (!horizontal) {
            return Integer.MAX_VALUE;
        }

        return ground.rect.top - droid.rect.bottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownStartTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_UP:
                jumpDroid();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void jumpDroid() {
        float time = System.currentTimeMillis() - touchDownStartTime;
        touchDownStartTime = 0;

        if (getDistanceFromGround(droid) != 0) {
            return;
        }

        if (time > MAX_TOUCH_TIME) {
            time = MAX_TOUCH_TIME;
        }

        droid.jump(time / MAX_TOUCH_TIME);
    }
}
