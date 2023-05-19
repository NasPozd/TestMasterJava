package com.example.testmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testmaster.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText signUpEmailAddress, signUpPassword, ConfirmPassword;
    Button btnSignUp;
    TextView textLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpEmailAddress = findViewById(R.id.signUpEmailAddress);
        signUpPassword = findViewById(R.id.signUpPassword);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        textLogin = findViewById(R.id.textLogin);
        mAuth = FirebaseAuth.getInstance();

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUpEmailAddress.getText().toString().isEmpty() || signUpPassword.getText().toString().isEmpty() || ConfirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
                }
                else if (!signUpPassword.getText().toString().equalsIgnoreCase(ConfirmPassword.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }
                else if (signUpPassword.getText().toString().length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Пароли должны быть длинее 6 символов!", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(signUpEmailAddress.getText().toString(), signUpPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Введен некорректный адрес почты!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



}