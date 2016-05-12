package io.keiji.shooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class City extends BaseObject {

    private static final int CITY_HEIGHT = 50;

    private final Paint paint = new Paint();

    public final Rect rect;

    public City(int width, int height) {

        paint.setColor(Color.LTGRAY);

        // 画面の下端全域
        int left = 0;
        int top = height - CITY_HEIGHT;
        int right = width;
        int bottom = height;
        rect = new Rect(left, top, right, bottom);

        yPosition = rect.centerY();
        xPosition = rect.centerX();
    }

    @Override
    public void draw(Canvas canvas) {
        if (status == STATUS_NORMAL) {
            canvas.drawRect(rect, paint);
        }
    }

    @Override
    public boolean isAvailable(int width, int height) {
        return true;
    }

    @Override
    public void move() {
    }

    @Override
    public Type getType() {
        return Type.City;
    }

    @Override
    public boolean isHit(BaseObject object) {
        if (object.getType() != Type.Missile) {
            return false;
        }

        return rect.contains(Math.round(object.xPosition), Math.round(object.yPosition));
    }
}
