package io.keiji.shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    private static final int MISSILE_LAUNCH_WEIGHT = 50;

    private Droid droid;
    private final List<BaseObject> missileList = new ArrayList<BaseObject>();

    private final Random rand = new Random(System.currentTimeMillis());

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (droid == null) {
            Bitmap droidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
            droid = new Droid(droidBitmap, width, height);
        }

        drawObjectList(canvas, missileList, width, height);

        // ミサイルの発射
        if (rand.nextInt(MISSILE_LAUNCH_WEIGHT) == 0) {
            launchMissile();
        }

        droid.draw(canvas);

        invalidate(); // 画面を更新
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

    private void launchMissile() {
        int fromX = rand.nextInt(getWidth());
        int toX = rand.nextInt(getWidth());

        float alignX = (toX - fromX) / (float) getHeight();
        Missile missile = new Missile(fromX, alignX);
        missileList.add(missile);
    }

}
