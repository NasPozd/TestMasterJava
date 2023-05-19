package com.example.testmaster.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmaster.R;
import com.example.testmaster.adapters.OptionAdapter;
import com.example.testmaster.models.Question;
import com.example.testmaster.models.Test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {
    RecyclerView testRecycler;
    TextView description;
    FirebaseFirestore firestore;

    ArrayList<Test> tests = new ArrayList<>();
    HashMap<String, Question> questions = new HashMap<>();
    int index = 1;

    Button submit;
    Button previous;
    Button next;

    Question question = new Question();
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testRecycler = findViewById(R.id.question_recycler);
        description = findViewById(R.id.test_description);
        firestore = FirebaseFirestore.getInstance();

        submit = findViewById(R.id.submit_btn);
        previous = findViewById(R.id.prev_btn);
        next = findViewById(R.id.next_btn);

        submit.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        previous.setVisibility(View.GONE);

        setUpFireStore();
        setUpEventListener();
    }

    public void setUpEventListener(){
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                index++;
                setUpView();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                index--;
                setUpView();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DATA","FINAL RESULTS : "+ questions);
                Log.d("DATA","FINAL RESULTS : "+ tests.get(0));

                Gson gson = new Gson();
                String data = gson.toJson(tests.get(0));
                Intent intent = new Intent(TestActivity.this,ResultActivity.class);
                intent.putExtra("TEST",data);
                startActivity(intent);
                finish();

            }
        });
    }

    public void setUpFireStore() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("DATE");
        if(date != null){
            firestore.collection("tests").whereEqualTo("title",date)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.R)
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Log.d("DATA","In onComplete");
                            if(task.isSuccessful()){
                                if(task.getResult() != null && !task.getResult().isEmpty()){
                                    Log.d("DATA",""+task.getResult().toObjects(Test.class).toString());
                                    tests = (ArrayList<Test>) task.getResult().toObjects(Test.class);
                                    questions = tests.get(0).questions;

                                    Log.d("DATA","Hello array"+tests);
                                    Log.d("DATA","QUESTIONS "+ questions.get("question1"));
                                    setUpView();
                                }else{
                                    Log.d("DATA","No Data");
                                }
                            }
                        }
                    });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void setUpView(){

        if(questions.size() != 0 || questions != null) {

            if (index == 1 && index == questions.size()) {
                submit.setVisibility(View.VISIBLE);
            } else if (index == 1 && questions.size() > 1) {
                next.setVisibility(View.VISIBLE);
            } else if (index == questions.size()) {
                previous.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
            } else {
                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            }
            question = questions.get("question"+index);
            Log.d("DATA", "Hello DAta" + question);
            Log.d("DATA", "Hello DAta" + questions.size());
        }
        setUpRecycler();

    }

    @RequiresApi(api = Build.VERSION_CODES.R)

    public void setUpRecycler() {
        if(question != null) {
            description.setText(question.description);
            testRecycler.setLayoutManager(new LinearLayoutManager(this));
            testRecycler.setAdapter(new OptionAdapter(TestActivity.this, question));
            testRecycler.setHasFixedSize(true);
        }
    }
}
