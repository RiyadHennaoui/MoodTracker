package com.example.moodtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout backgraoundMoods;
    ImageView faceMoods;
    ImageView noteAdd;
    ImageView history;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        backgraoundMoods = findViewById(R.id.background_moods);
        faceMoods = findViewById(R.id.face_moods);
        noteAdd = findViewById(R.id.note_add);
        history = findViewById(R.id.history);


    }
}
