package com.example.oki.akihiro.camp_2016_spring;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    /*
     *
     *  変数の宣言
     *
     */

    int count;//カウンター
    TextView ansView;//テキストビュー


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initView();//初期化関係


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    //初期化処理
    private void initView() {

        count=0;//カウンターを０にする

        ansView = (TextView) findViewById(R.id.textView);//テキストビューの初期化

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick_plus(View view) {
        count++;//カウンター　＋！

        check_count();//カウンターの監視

        ansView.setText(String.valueOf(count));//表示
    }


    public void onClick_minus(View view) {
        count--;//カウンター　−１

        check_count();//カウンターの監視

        ansView.setText(String.valueOf(count));//表示


    }

    public void onClick_clear(View view) {
        count=0;//カウンターをリセット

        check_count();//カウンターの監視

        ansView.setText(String.valueOf(count));//表示
    }

    public void onClick_multiplication(View view) {
        count=count*count;//カウンター 二乗する

        check_count();//カウンターの監視

        ansView.setText(String.valueOf(count));//表示
    }

    private void check_count() {


        //カウンターが１０以上なら
        if( 10 <= count )
        {
            ansView.setTextColor(Color.RED);//テキストを赤色にする
        }
        //カウンターが−１０以下なら
        else if ( count <= -10 )
        {
            ansView.setTextColor(Color.BLUE);//テキストを青色にする
        }
        //カウンターが−９〜９なら
        else
        {
            ansView.setTextColor(Color.BLACK);//テキストを黒色にする
        }

    }

}
