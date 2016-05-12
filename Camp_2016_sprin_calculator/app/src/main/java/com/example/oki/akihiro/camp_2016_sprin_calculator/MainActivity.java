package com.example.oki.akihiro.camp_2016_sprin_calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    /**
     * 表示用変数
     */
    TextView ans_textview;//テキストビュー
    String[] ans_strArr;//入力されたデータを保存する変数
    String mTemp;//一時的に保存する変数

    /**
     * 連続でのkey入力を禁止する
     */
    int mNoKey = 0;//連続で四則演算子を入力させない
    int mSkey = 0;//





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        initView();
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


    /**
     * テキストビューの初期化
     */
    private void initView() {
        ans_textview = (TextView) findViewById(R.id.TextView);



        Button btC = (Button) findViewById(R.id.btC);
        btC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ans_textview.setText("");//テキストビューを空にする
            }
        });

    }


    /**
     *
     * 数字キーが押された時の処理
     */
    public void onClickNum(View view) {

        String key = (String) view.getTag();//数字キーのタグを読み取ってテキストビューに代入

        ans_textview.setText(ans_textview.getText().toString() + key);
        mNoKey = 1;//数字を押したので次に四則演算子を押せるようにする。
        mSkey=1;//数字を押したので次に小数点を押せるようにする。

    }

    /**
     *
     * 小数点のドットが押された時の処理
     */

    public void onClickKeyDot(View view) {

        if (mSkey == 1) {
            String key = (String) view.getTag();//小数点キーのタグを読み取ってテキストビューに代入
            ans_textview.setText(ans_textview.getText().toString() + key); // 1 3 5 7
            mSkey = 0;//数字が押されるまで、小数点を押せないようにする。
            mNoKey=0;//数字が押されるまで、四則演算子を押せないようにする。
        }
    }

    /**
     *
     * 四則演算子が押された時の処理
     */
    public void onClickKey(View view) {

        if (mNoKey == 1) {
            String key = (String) view.getTag();//四則演算子キーのタグを読み取ってテキストビューに代入
            ans_textview.setText(ans_textview.getText().toString() + " " + key + " "); // 1 3 5 7
            mSkey = 0;//数字が押されるまで、小数点を押せないようにする。
            mNoKey=0;//数字が押されるまで、四則演算子を押せないようにする。
        }
    }

    /**
     *
     * エンターが押された時の処理
     */
    public void EnterOnClick(View view) {

        if (mNoKey == 1) {

            /**
             * 配列に入れ直す
             */
            String str = ans_textview.getText().toString();//テキストビューに表示されてるデータを取り出す
            ans_strArr = str.split(" ");//スペースごと区切って配列に代入

            /**
             * 自作四則演算関数
             */
            Calculation();
        }
    }

    /**
     * 自作四則演算関数
     */
    private void Calculation() {
        /**
         *　
         * 変数の宣言
         */


        double a = 0;//計算用変数
        double b = 0;//計算用変数
        double c = 0;//計算用変数


        /**
         * 先に掛け算、割り算をする
         */
        for (int j = 1; j <ans_strArr.length; j = j + 1) {

            //掛け算の演算子が見つかったら
            if (ans_strArr[j].equals("*")) {
                a = Double.parseDouble(ans_strArr[j - 1]);//演算子の１つ前の数値をaに代入
                b = Double.parseDouble(ans_strArr[j + 1]);//演算子の１つ後の数値をbに代入
                c = a * b;//計算してcに代入
                ans_strArr[j + 1] = String.valueOf(c);//演算子の後の値をクリア
                ans_strArr[j - 1] = String.valueOf("0");//演算子の前の値を０にする
                ans_strArr[j] = String.valueOf("+");//掛け算演算子を足し算にする
            }

            //割り算の演算子が見つかったら
            else if (ans_strArr[j].equals("/")) {

                a = Double.parseDouble(ans_strArr[j - 1]);//演算子の１つ前の数値をaに代入

                //　/0 の形だったら計算をしない
                if (ans_strArr[j + 1].equals("0")) {

                } else {
                    b = Double.parseDouble(ans_strArr[j + 1]);//演算子の１つ後の数値をbに代入
                    c = a / b;//計算してcに代入
                    ans_strArr[j + 1] = String.valueOf(c);//演算子の後の値をクリア
                    ans_strArr[j - 1] = String.valueOf("0");//演算子の前の値を０にする
                    ans_strArr[j] = String.valueOf("+");//割り算演算子を足し算にする
                }
            }
        }


        /**
         * 残りの足し算、引き算をする
         */
        for (int j = 1; j < ans_strArr.length - 1; j = j + 1) {

            //引き算の演算子が見つかったら
            if (ans_strArr[j].equals("-")) {
                a = Double.parseDouble(ans_strArr[j - 1]);//演算子の１つ前の数値をaに代入
                b = Double.parseDouble(ans_strArr[j + 1]);//演算子の１つ後の数値をbに代入
                c = a - b;//計算してcに代入
                ans_strArr[j + 1] = String.valueOf(c);//演算子の後の値をクリア
                ans_strArr[j - 1] = String.valueOf("0");//演算子の前の値を０にする
                ans_strArr[j] = String.valueOf("+");//割り算演算子を足し算にする
            }

            //足し算の演算子が見つかったら
            else if (ans_strArr[j].equals("+")) {

                a = Double.parseDouble(ans_strArr[j - 1]);//演算子の１つ前の数値をaに代入
                b = Double.parseDouble(ans_strArr[j + 1]);//演算子の１つ後の数値をbに代入
                c = a + b;
                ans_strArr[j + 1] = String.valueOf(c);//演算子の後の値をクリア
            }
            /**
             *　配列の最後になった計算結果を一番目の配列に移動
             */
            for (int k = 0; k < ans_strArr.length; k++) {
                ans_strArr[0] = ans_strArr[k];
            }
        }

        /**
         *　計算し終わったデータをテキストに表示
         */
        ans_textview.setText(ans_strArr[0]);
        mNoKey = 1;//数字を押したので次に四則演算子を押せるようにする。
        mSkey = 0;//数字が押されるまで、小数点を押せないようにする。
    }
}
