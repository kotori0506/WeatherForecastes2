package io.keiji.shooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bullet extends BaseObject {

    private final Paint paint = new Paint();

    private static final float SIZE = 15f;

    public final float alignX;

    Bullet(float alignX, Rect rect) {
        this.alignX = alignX;
        yPosition = rect.centerY();
        xPosition = rect.centerX();

        paint.setColor(Color.RED);
    }

    @Override
    public void move() {
        yPosition -= 1 * MOVE_WEIGHT;
        xPosition += alignX * MOVE_WEIGHT;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(xPosition, yPosition, SIZE, paint);
    }
}
