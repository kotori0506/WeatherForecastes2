package com.example.oki.akihiro.camp_2016_spring_quiz_application;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //宣言

    //テキストビュー
    TextView Ans_now;
    TextView Question,Question_text;
    TextView Ans1,Ans2,Ans3,Ans4;
    TextView count;

    //CSVで利用する文字列
    String[] csv;

    //問題番号
    int Question_nmb;

    //項数
    int terms=7;

    //プレイヤーのデータを保存する配列
    String player_data[];

    //乱数の情報を保存する配列
    int temp_data[]={0,0,0,0};

    //保存先を示すパス
    String path = "/data/data/com.example.oki.akihiro.camp_2016_spring_quiz_application/dst.csv";


    /**         書き込む内容（CSV形式）
     *
     * 0：問題番号　1：問題文１　2：回答１　3：回答２　4：回答３　5：回答４ 6:答えの番号
     *
     */
    String data[]={
            "第1問","日本の面積はいくつ？","377,900 km²","378,900 km²","377,600 km²","367,900 km²","1",
            "第2問","大木の持っているマウスパットの大きさは？","40cm*40cm","30cm*30cm","20cm*20cm","50cm*50cm","1",
            "第3問","大木の持っているマウスの数は","5個","4個","3個","6個","1",
            "第4問","マイクロソフトができた年","1975年","1976年","1977年","1974年","1",
            "第5問","アップルができた年","1976年","1977年","1975年","1974年","1",
            "第6問","スーパーコンピューター京の年間の維持費","80億円","70億円","60億円","90億円","1",
            "第7問","大木のメインPCの画面の数は","2枚","3枚","1枚","4枚","1",
    };



    //答えの番号
    int Answer;
    //プレイヤーが選んだ選択肢番号
    int Player_Answer=0;

    //カウントダウンで使用する変数
    long get_count;
    int get_count_int;


    //カウントダウンシステム
    public class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        //カウントダウンが終わったら時の処理
        @Override
        public void onFinish() {
            //時間切れになったら最終結果に行く
            finish_mysystem();
        }

        //残り時間の表示
        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            count.setText(Long.toString(millisUntilFinished / 1000 % 60));

            //カウントしている時間の情報を保存する。
            String count_view=count.getText().toString();
            get_count=Integer.parseInt(count_view);

            count.setText("残りの秒数は" + Long.toString(millisUntilFinished / 1000 % 60) + "秒です。" );
        }
    }

    //カウントダウンが終了したら最終結果アクティビティに移動する
    public void finish_mysystem() {

        //時間オーバーを表示する
        for (int i=1;i<8;i++)
        {
            player_data[i]="時間オーバー";
        }

        //インテントの宣言
        Intent intent_f;
        //最終結果を表示するアクティビティにインテントをセットする
        intent_f = new Intent(this, ResultActivity.class);


        //バンドルの生成
        Bundle bundle = new Bundle();

        //バンドルにプレイヤーデータを渡す。
        bundle.putStringArray("PlayerData", player_data);

        //インテントにバンドルを持たせる
        intent_f.putExtras(bundle);


        //別アクティビティへ移動
        startActivity(intent_f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //カウントダウンの時間を取得する。
        Intent c_getIntent = getIntent();
        Bundle bundle_count=c_getIntent.getExtras();
        get_count_int = bundle_count.getInt("Count");

        // インスタンスの生成
        MyCountDownTimer cdt = new MyCountDownTimer(get_count_int, 1000);
        cdt.start();


        //テキストビューを利用できるようにする。
        Ans_now = (TextView)this.findViewById(R.id.Answer);
        Question = (TextView)this.findViewById(R.id.Question_nmb);
        Question_text = (TextView)this.findViewById(R.id.Question_text);
        Ans1= (TextView)this.findViewById(R.id.Ans1);
        Ans2= (TextView)this.findViewById(R.id.Ans2);
        Ans3= (TextView)this.findViewById(R.id.Ans3);
        Ans4= (TextView)this.findViewById(R.id.Ans4);
        count=(TextView)this.findViewById(R.id.Count);



        //csvファイルに書き込む
        writeCsvFile();
        //csvファイルを読み込む。
        readCsvFile();


        //別アクティビティから問題番号を取得する。
        Intent getIntent = getIntent();
        Bundle bundle=getIntent.getExtras();
        player_data = bundle.getStringArray("PlayerData");
        Question_nmb = Integer.valueOf(player_data[0]);



        //問題番号によって問題をセット。
        Question.setText(csv[((Integer.valueOf(player_data[8]) - 1) * terms)]);
        Question_text.setText(csv[(Question_nmb-1) * terms + 1]);

        //使用している選択肢フラグをリセットする
        temp_data[0]=0;temp_data[1]=0;temp_data[2]=0;temp_data[3]=0;
        int mTemp;
        //選択肢を乱数より設定する。
        mTemp=rand_system()+2;//乱数の取得
        Ans1.setText(csv[(Question_nmb-1)*terms+mTemp]);//乱数通りに選択肢を用意する
        if (mTemp==2) {
            Answer=1;//答えがこの選択肢なら選択肢番号を答えに代入する
        }

        mTemp=rand_system()+2;//乱数の取得
        Ans2.setText(csv[(Question_nmb-1)*terms+mTemp]);//乱数通りに選択肢を用意する
        if (mTemp==2) {
            Answer=2;//答えがこの選択肢なら選択肢番号を答えに代入する
        }

        mTemp=rand_system()+2;//乱数の取得
        Ans3.setText(csv[(Question_nmb-1)*terms+mTemp]);//乱数通りに選択肢を用意する
        if (mTemp==2) {
            Answer=3;//答えがこの選択肢なら選択肢番号を答えに代入する
        }

        mTemp=rand_system()+2;//乱数の取得
        Ans4.setText(csv[(Question_nmb-1)*terms+mTemp]);//乱数通りに選択肢を用意する
        if (mTemp==2) {
            Answer=4;//答えがこの選択肢なら選択肢番号を答えに代入する
        }

    }

    //配列０の要素番号をランダムんに返す(0から4)
    public int rand_system() {
        int temp;
        while(true) {
            //乱数の取得を取得する
            Random random = new Random();
            temp = random.nextInt(4);

            if (temp_data[temp] == 0)
            {
                //配列に使ったことを保存する
                temp_data[temp]=1;


                break;
            }
        }

        //使用できる数値を返す
        return temp;
    }

    //CSVファイルの生成
    private void writeCsvFile() {

        try {
            //書き込むファイルの保存先を探して保存する
            FileOutputStream output = new FileOutputStream(path);
            OutputStreamWriter m_writer=new OutputStreamWriter(output, "UTF-8");
            CSVWriter writer = new CSVWriter(m_writer);

            //書き込み。
            writer.writeNext(data);

            //クローズ
            writer.close();
            m_writer.close();
            output.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block


            e.printStackTrace();
        }

    }


    //CSVファイルを読み込む
    private void readCsvFile() {
        try {
            InputStream input = new FileInputStream(path);
            InputStreamReader m_reader=new InputStreamReader(input, "UTF-8");
            CSVReader reader = new CSVReader(m_reader,',','"',0);
            csv = reader.readNext();



            reader.close();
            m_reader.close();
            input.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }


    }




    //回答ボタンを押して現在選んでる回答を表示する
    public void onClickAns(View view) {

        //タグを読み取る
        String tag = (String) view.getTag();

        //現在選択している回答の更新
        if (tag.equals("Ans1"))
        {
            //現在選択している解答を表示する
            Ans_now.setText("今あなたの選んでいる答えは　"+Ans1.getText());

            //現在選んでいる選択肢番号を管理する
            Player_Answer=1;
        }
        else if (tag.equals("Ans2"))
        {
            //現在選択している解答を表示する
            Ans_now.setText("今あなたの選んでいる答えは　"+Ans2.getText());

            //現在選んでいる選択肢番号を管理する
            Player_Answer=2;
        }
        else if (tag.equals("Ans3"))
        {
            //現在選択している解答を表示する
            Ans_now.setText("今あなたの選んでいる答えは　"+Ans3.getText());

            //現在選んでいる選択肢番号を管理する
            Player_Answer=3;
        }
        else if (tag.equals("Ans4"))
        {
            //現在選択している解答を表示する
            Ans_now.setText("今あなたの選んでいる答えは　"+Ans4.getText());

            //現在選んでいる選択肢番号を管理する
            Player_Answer=4;
        }


    }


    //答え合わせをして次の問題へ行く
    public void onClickNextQuiz(View view) {

        switch (view.getId()){
            case R.id.NextQuiz:


                //答え合わせをしてプレイヤーデータに正解かどうかを代入する
                if (Player_Answer==Answer) {
                    player_data[Integer.valueOf(player_data[0])] = "正解";
                }
                else{
                    player_data[Integer.valueOf(player_data[0])] = "不正解";
                }

                //インテントの生成
                Intent intent = new Intent(this, QuizResultActivity.class);

                //バンドルの生成
                Bundle bundle = new Bundle();
                Bundle bundle_count = new Bundle();

                //バンドルにプレイヤーデータを渡す。
                bundle.putStringArray("PlayerData", player_data);

                //カウントダンを始めるデータを渡す。
                get_count_int= (int) get_count;
                bundle_count.putInt("Count", get_count_int);

                //インテントにバンドルを持たせる
                intent.putExtras(bundle);
                intent.putExtras(bundle_count);

                //別アクティビティへ移動
                startActivity(intent);
                break;
        }
    }
}
