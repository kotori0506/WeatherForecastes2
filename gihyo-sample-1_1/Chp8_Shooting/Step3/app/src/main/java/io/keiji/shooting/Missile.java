package io.keiji.shooting;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Missile extends BaseObject {

    private final Paint paint = new Paint();

    private static final float SIZE = 10f;

    public final float alignX;

    Missile(int fromX, float alignX) {
        yPosition = 0;
        xPosition = fromX;
        this.alignX = alignX;

        paint.setColor(Color.BLUE);
    }

    @Override
    public void move() {
        yPosition += 1 * MOVE_WEIGHT;
        xPosition += alignX * MOVE_WEIGHT;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(xPosition, yPosition, SIZE, paint);
    }
}
