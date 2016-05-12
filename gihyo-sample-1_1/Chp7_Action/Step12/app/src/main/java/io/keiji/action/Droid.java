package io.keiji.action;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {

    private static final float GRAVITY = 0.8f;
    private static final float WEIGHT = GRAVITY * 60;

    private static final int COLLISION_MARGIN_LEFT = 35;
    private static final int COLLISION_MARGIN_RIGHT = 15;

    private final Paint paint = new Paint();

    private Bitmap bitmap;

    final Rect rect;

    public interface Callback {

        public int getDistanceFromGround(Droid droid);
    }

    private final Callback callback;

    private float acceleration = 0;

    public Droid(Bitmap bitmap, int left, int top, Callback callback) {
        int rectLeft = left + COLLISION_MARGIN_LEFT;
        int rectRight = left + bitmap.getWidth() - COLLISION_MARGIN_RIGHT;
        this.rect = new Rect(rectLeft, top, rectRight, top + bitmap.getHeight());

        this.bitmap = bitmap;
        this.callback = callback;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left - COLLISION_MARGIN_LEFT, rect.top, paint);
    }

    public void jump(float time) {
        acceleration = time * WEIGHT;
    }

    public void move() {

        acceleration -= GRAVITY;

        int distanceFromGround = callback.getDistanceFromGround(this);
        if (acceleration < 0 && acceleration < -distanceFromGround) {
            acceleration = -distanceFromGround;
        }

        rect.offset(0, -Math.round(acceleration));
    }

    public void shutdown() {
        acceleration = 0;
    }

}
