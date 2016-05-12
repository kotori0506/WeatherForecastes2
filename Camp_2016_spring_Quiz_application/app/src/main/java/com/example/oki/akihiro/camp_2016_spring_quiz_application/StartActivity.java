package com.example.oki.akihiro.camp_2016_spring_quiz_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class StartActivity extends AppCompatActivity {


    /*
        プレイヤーデータのセッティング

        0:問題番号　1:正解か　2:正解か　3:正解か　4:正解か　5:正解か　6:正解か　7:正解か 8:解いた数
        */

    String player_data[]={"0","0","0","0","0","0","0","0","1"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }



    //スタート画面から画面を遷移させる
    public void onClickStart(View view) {
        switch (view.getId()){
            case R.id.start:
                //インテントの生成
                Intent intent = new Intent(this, MainActivity.class);

                //バンドルの生成
                Bundle bundle = new Bundle();
                Bundle bundle_count = new Bundle();


                //乱数の取得を取得して次の問題を決める
                Random random=new Random();
                int nestQuizNum=random.nextInt(7)+1;
                player_data[0]=String.valueOf(nestQuizNum);


                //バンドルにプレイヤーデータを渡す。
                bundle.putStringArray("PlayerData",player_data);

                //カウントダンを始めるデータを渡す。
                bundle_count.putInt("Count",30000);

                //インテントにバンドルを持たせる
                intent.putExtras(bundle);
                intent.putExtras(bundle_count);

                //別アクティビティへ移動
                startActivity(intent);
                break;
        }
    }
}
