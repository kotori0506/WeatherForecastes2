package com.example.oki.akihiro.camp_2016_spring_quiz_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class QuizResultActivity extends AppCompatActivity {


    //宣言

    //テキストビュー
    TextView JudgmentView;

    //プレイヤーのデータを保存する配列
    String player_data[];

    //カウントダウンの時間を引き継ぐための変数
    int count_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);



        //クイズアクティビティからプレイヤーデータを取得する。
        Intent getIntent = getIntent();
        Bundle bundle=getIntent.getExtras();
        player_data = bundle.getStringArray("PlayerData");

        //正解か不正解かを表示するテキストビューの準備
        JudgmentView= (TextView)this.findViewById(R.id.JudgmentAnswer);


        //選択した答えが正解かどうかを表示する
        JudgmentView.setText(player_data[Integer.valueOf(player_data[0])]);


        //カウントダウンの時間を取得する（引き継ぎ）
        Intent c_getIntent = getIntent();
        Bundle bundle_count=c_getIntent.getExtras();
        count_get = bundle_count.getInt("Count");


    }


    //次の問題もしくは、最終結果
    public void onClickNext(View view) {
        switch (view.getId()){
            case R.id.Next:
                //解いた問題数によってインテントの生成
                Intent intent;


                //７問解いたら結果画面へ
                if(Integer.valueOf(player_data[8])==7)
                {
                    //最終結果を表示するアクティビティにインテントをセットする
                    intent = new Intent(this, ResultActivity.class);

                }
                //次の問題へ
                else {
                    //次の問題へアクティビティにインテントをセットする
                    intent = new Intent(this, MainActivity.class);
                    int temp=Integer.valueOf(player_data[8]);
                    //解いた問題数を増やす。
                    temp++;
                    player_data[8]=String.valueOf(temp);


                    //問題を更新する
                    while(true)
                    {
                        //乱数の取得を取得して次の問題を決める
                        Random random=new Random();
                        int nestQuizNum=random.nextInt(7)+1;
                        if (player_data[nestQuizNum].equals("0"))
                        {
                            player_data[0]=String.valueOf(nestQuizNum);
                            break;
                        }


                    }

                }

                //バンドルの生成
                Bundle bundle = new Bundle();
                Bundle bundle_count = new Bundle();

                //バンドルに問題を設定させる
                bundle.putStringArray("PlayerData", player_data);

                //カウントダンを始めるデータを渡す。
                //bundle_count.putInt("Count", count_get);
                count_get=count_get*1000;
                bundle_count.putInt("Count", count_get);

                //インテントにバンドルを持たせる
                intent.putExtras(bundle);
                intent.putExtras(bundle_count);




                //別アクティビティへ移動
                startActivity(intent);
                break;
        }

    }
}
