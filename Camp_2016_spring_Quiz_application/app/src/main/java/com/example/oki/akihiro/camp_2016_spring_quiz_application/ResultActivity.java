package com.example.oki.akihiro.camp_2016_spring_quiz_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {


    //宣言
    TextView Result1,Result2,Result3,Result4,Result5,Result6,Result7;


    //プレイヤーのデータを保存する配列
    String player_data[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        //クイズアクティビティからプレイヤーデータを取得する。
        Intent getIntent = getIntent();
        Bundle bundle=getIntent.getExtras();
        player_data = bundle.getStringArray("PlayerData");

        //テキストビューを利用できるようにする。
        Result1 = (TextView)this.findViewById(R.id.Result1);
        Result2 = (TextView)this.findViewById(R.id.Result2);
        Result3 = (TextView)this.findViewById(R.id.Result3);
        Result4 = (TextView)this.findViewById(R.id.Result4);
        Result5 = (TextView)this.findViewById(R.id.Result5);
        Result6 = (TextView)this.findViewById(R.id.Result6);
        Result7 = (TextView)this.findViewById(R.id.Result7);


        //プレイヤーデータに保存されている正解か、不正解かを表示する。
        Result1.setText(player_data[1]);
        Result2.setText(player_data[2]);
        Result3.setText(player_data[3]);
        Result4.setText(player_data[4]);
        Result5.setText(player_data[5]);
        Result6.setText(player_data[6]);
        Result7.setText(player_data[7]);

    }



    //リザルト画面からタイトルへ遷移させる
    public void onClickReturnTitle(View view) {

        switch (view.getId()){
            case R.id.ReturnTitle:
                //インテントにスタートアクティビティをセットする
                Intent intent = new Intent(this, StartActivity.class);

                //別アクティビティへ移動
                startActivity(intent);
                break;
        }
    }
}
