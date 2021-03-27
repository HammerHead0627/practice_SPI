package com.example.practice_spi;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel, questionLabel;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void checkAnswer(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}