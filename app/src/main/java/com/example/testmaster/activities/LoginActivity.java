package com.example.testmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    EditText EmailAddress, Password;
    Button btnLogin;
    TextView textSignUp;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailAddress = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.Password);
        btnLogin = findViewById(R.id.btnLogin);
        textSignUp = findViewById(R.id.textSignUp);
        mAuth = FirebaseAuth.getInstance();

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(mAuth.getCurrentUser() != null){
            Log.i("TAG", "onCreate: UserLoggedIN "+mAuth.getCurrentUser().getEmail());
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               userSignIn();
            }
        });
    }

    public void userSignIn() {
        if (EmailAddress.getText().toString().isEmpty() || Password.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(EmailAddress.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Вы не зарегистрированы, либо почта или пароль неверны!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}