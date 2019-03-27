package com.example.moodtracker;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    private ConstraintLayout backgraoundMoods;
    private ImageView faceMoods;
    private ImageView noteAdd;
    private ImageView history;


    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, this);


        backgraoundMoods = findViewById(R.id.background_moods);
        faceMoods = findViewById(R.id.face_moods);
        noteAdd = findViewById(R.id.note_add);
        history = findViewById(R.id.history);




    }



    public void userComments(View v){

        DialogComments.showCommentsDiags(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(MainActivity.class.getSimpleName(), "OnFling : " + velocityY);


        if (velocityY < 0 && i >= 0 && i < 4) {

            i++;

            backgraoundMoods.setBackgroundResource(Mood.values()[i].getColorRes());
            faceMoods.setImageResource(Mood.values()[i].getDrawableRes());


            return true;

        }


        if (velocityY > 0 && i >= 1 && i <= 4) {

            i--;
            backgraoundMoods.setBackgroundResource(Mood.values()[i].getColorRes());
            faceMoods.setImageResource(Mood.values()[i].getDrawableRes());


            return true;
        } else {
            return false;
        }

    }
}
