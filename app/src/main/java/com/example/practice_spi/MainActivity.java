package com.example.practice_spi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private TextView countLabel, questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10 ;

    //問題番号ゲッター
    public static int getQuizCount() {
        return QUIZ_COUNT;
    }

    //問題の配列を入れる配列quizArrayを定義する
    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    private Handler handler = new Handler();

    //一定時間経過に画面表示を行う
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable(){
                @Override
                public void run(){
                quizCount++;
                showNextQuiz();
                }
            });
        };
    };

    //問題を定義
    String[][] quizData = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"北海道", "札幌市", "長崎市", "福島市", "前橋市"},
            {"青森県", "青森市", "広島市", "甲府市", "岡山市"},
            {"岩手県", "盛岡市","大分市", "秋田市", "福岡市"},
            {"宮城県", "仙台市", "水戸市", "岐阜市", "福井市"},
            {"秋田県", "秋田市","横浜市", "鳥取市", "仙台市"},
            {"山形県", "山形市","青森市", "山口市", "奈良市"},
            {"福島県", "福島市", "盛岡市", "新宿区", "京都市"},
            {"茨城県", "水戸市", "金沢市", "名古屋市", "奈良市"},
            {"栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市"},
            {"群馬県", "前橋市", "福岡市", "松江市", "福井市"},
    };



    //todo
    //DBから問題を取得する


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idからビューを取得する
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // quizDataからクイズ出題用のquizArrayを作成する
        for (String[] quizDatum : quizData) {

            // 新しいArrayListを準備
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizDatum[0]);  // 都道府県名
            tmpArray.add(quizDatum[1]);  // 正解
            tmpArray.add(quizDatum[2]);  // 選択肢１
            tmpArray.add(quizDatum[3]);  // 選択肢２
            tmpArray.add(quizDatum[4]);  // 選択肢３

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);
        }

        //次の問題へ
        showNextQuiz();

    }

    //問題を表示する
    public void showNextQuiz() {
        // 問題カウントラベルを更新
        countLabel.setText(getString(R.string.count_label, quizCount));

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題文を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // 問題配列から問題文（都道府県名）を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 解答ボタンに正解と選択肢３つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //出題した問題をquizArrayから削除
        quizArray.remove(randomNum);

    }

    //正誤判定
    public void checkAnswer(View view) {

        // どの回答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();


        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
        } else {
            alertTitle = "不正解...";
        }

        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // 全問終了後結果画面へ移動する
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    //制限時間を超えた際に行う処理
    public void timeOutProcess(){
        Timer timer = new Timer();
        timer.schedule(task, 30000);
    }


}