package io.keiji.action;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ground {
    private final Paint paint = new Paint();

    final Rect rect;

    public Ground(int left, int top, int right, int bottom) {
        this.rect = new Rect(left, top, right, bottom);

        paint.setColor(Color.rgb(153, 76, 0)); // 茶色
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    public void move(int moveToLeft) {
        rect.offset(-moveToLeft, 0);
    }
}
