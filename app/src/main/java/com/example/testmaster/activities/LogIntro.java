package com.example.testmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testmaster.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogIntro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_intro);

        mAuth = FirebaseAuth.getInstance();
        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null){
                    Toast.makeText(LogIntro.this, "Вы уже авторизованы!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIntro.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LogIntro.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
