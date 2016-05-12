package io.keiji.action;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class GameView extends View {

    private static final Paint PAINT = new Paint();

    private static final int START_GROUND_HEIGHT = 50;

    private Bitmap droidBitmap;
    private Ground ground;

    public GameView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (droidBitmap == null) {
            droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
        }

        if (ground == null) {
            ground = new Ground(0, height - START_GROUND_HEIGHT, width, height);
        }

        ground.draw(canvas);

        canvas.drawBitmap(droidBitmap, 0, 0, PAINT);
    }
}
