package io.keiji.shooting;

import android.graphics.Canvas;

public abstract class BaseObject {

    static final float MOVE_WEIGHT = 3.0f;

    float yPosition;
    float xPosition;

    public abstract void draw(Canvas canvas);

    public boolean isAvailable(int width, int height) {
        if (yPosition < 0 || xPosition < 0 || yPosition > height || xPosition > width) {
            return false;
        }
        return true;
    }

    public abstract void move();
}
