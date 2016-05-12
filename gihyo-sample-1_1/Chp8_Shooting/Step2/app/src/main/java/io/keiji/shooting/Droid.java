package io.keiji.shooting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Droid {

    private final Paint paint = new Paint();

    public final Bitmap bitmap;
    public final Rect rect;

    public Droid(Bitmap bitmap, int width, int height) {
        this.bitmap = bitmap;

        // 画面中央の位置
        int left = (width - bitmap.getWidth()) / 2;
        int top = (height - bitmap.getHeight()) / 2;
        int right = left + bitmap.getWidth();
        int bottom = top + bitmap.getHeight();
        rect = new Rect(left, top, right, bottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }
}
