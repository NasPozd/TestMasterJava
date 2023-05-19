package com.example.testmaster.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmaster.R;
import com.example.testmaster.adapters.TestAdapter;
import com.example.testmaster.models.Test;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar appBar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout mainDrawer;
    NavigationView nav_view;
    RecyclerView testRecyclerView;
    FloatingActionButton fab;
    ArrayList<Test> testList = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBar = findViewById(R.id.appBar);
        mainDrawer = findViewById(R.id.mainDrawer);
        fab = findViewById(R.id.btn_date_picker);
        testRecyclerView = findViewById(R.id.testRecyclerView);
        nav_view = findViewById(R.id.nav_view);
        firebaseFirestore = FirebaseFirestore.getInstance();

        setUpView();
    }

    void setUpView(){
        setUpDrawerLayout();
        setUpRecyclerLayout();
        setUpFireStore();
        setUpDatePicker();
    }

    public void setUpDatePicker(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().build();
                datePicker.show(getSupportFragmentManager(),"DataPicker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Log.d("DATE",selection.toString());
                        Log.d("DATE",datePicker.getHeaderText());
                        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

                        String date = dateFormater.format(new Date((Long) selection));

                        Log.d("DATE","DATE FORMAT"+date);
                        Intent intent = new Intent(MainActivity.this,TestActivity.class);
                        intent.putExtra("DATE",date);
                        startActivity(intent);
                    }
                });

                datePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DATE",datePicker.getHeaderText());
                    }
                });
            }
        });
    }

    public void setUpFireStore() {
        CollectionReference collection = firebaseFirestore.collection("tests");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                if(value == null || error != null){
                    Toast.makeText(MainActivity.this,"Error in Fetching Data",Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("TAG",value.toObjects(Test.class).get(0).questions.toString());
                Log.d("TAG",""+value.toObjects(Test.class).toString());
                testList.clear();
                testList.addAll(value.toObjects(Test.class));

                testRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerLayout(){
        testRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        testRecyclerView.setAdapter(new TestAdapter(this,testList));
        testRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setUpDrawerLayout(){
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                mainDrawer.closeDrawers();
                return true;
            }
        });
        setSupportActionBar(appBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mainDrawer,
                R.string.app_name,
                R.string.app_name
        );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return true;
    }
}
