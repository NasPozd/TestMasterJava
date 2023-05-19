package com.example.testmaster.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmaster.R;
import com.example.testmaster.models.Question;
import com.example.testmaster.models.Test;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    Gson gson;
    Test test;
    TextView scoreText;
    TextView textAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        scoreText = findViewById(R.id.scoreText);
        textAnswer = findViewById(R.id.textAnswer);

        setUpViews();
    }

    public void setUpViews() {
        gson = new Gson();
        Intent intent = getIntent();
        String json = intent.getStringExtra("TEST");
        test = (Test) gson.fromJson(json, Test.class);

        calculateScore();
        setAnswerView();
    }

    public void setAnswerView() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Question> entry : test.questions.entrySet()){
            Question question = entry.getValue();
            builder.append("<font color='#18206F'><b>Вопрос: "+question.description+" </b></font><br><br>");
            builder.append("<font color='#009866'>Ответ: "+question.answer+"</font><br><br>");
            builder.append("<font color='#009866'>Ваш ответ: "+question.userAnswer+"</font><br><br>");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                textAnswer.setText(Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT));
            }else{
                textAnswer.setText(Html.fromHtml(builder.toString()));
            }
        }
    }

    public void calculateScore() {
        int score = 0;
        Log.d("DATA",""+test.questions);
        for(HashMap.Entry<String,Question> entry : test.questions.entrySet()){
            Log.d("DATA",""+entry.getValue());
            Question val = entry.getValue();
            if(val.userAnswer.equals(val.answer)){
                score += 10;
            }
        }
        scoreText.setText("Ваш результат: " + score);
    }
}
