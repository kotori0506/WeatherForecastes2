package io.keiji.labyrinth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity implements LabyrinthView.Callback {

    private LabyrinthView labyrinthView;

    private int seed = 0;

    boolean isFinished = false;

    private static final String EXTRA_KEY_SEED = "key_seed";

    private static Intent newIntent(Context context, int seed) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_KEY_SEED, seed);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        seed = getIntent().getIntExtra(EXTRA_KEY_SEED, 0);

        labyrinthView = new LabyrinthView(this);
        labyrinthView.setSeed(seed);
        labyrinthView.setCallback(this);
        setContentView(labyrinthView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGoal() {
        if (isFinished) {
            return;
        }
        isFinished = true;

        Toast.makeText(this, "Goal!!", Toast.LENGTH_SHORT).show();

        labyrinthView.stopSensor();
        labyrinthView.stopDrawThread();

        nextStage();

        finish();
    }

    private void nextStage() {
        Intent intent = MainActivity.newIntent(this, seed + 1);
        startActivity(intent);
    }

}
