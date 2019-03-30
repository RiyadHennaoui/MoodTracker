package com.example.moodtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

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

        Log.i("date", LocalDate.now().toString());
        Realm.getDefaultInstance().beginTransaction();
        DailyMood dailyMood = new DailyMood();
        dailyMood.setComment("test1");
        dailyMood.setDate(LocalDate.now().toString());
        dailyMood.saveMood(Mood.HAPPY);
        DailyMood todayMood = Realm.getDefaultInstance().createObject(DailyMood.class, dailyMood);
        Realm.getDefaultInstance().commitTransaction();


        RealmResults<DailyMood> results = Realm.getDefaultInstance()
                .where(DailyMood.class)
                .sort("date", Sort.DESCENDING)
                .findAll();
        Toast.makeText(this, results.get(0).getComment(), Toast.LENGTH_LONG).show();


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moodHistory = new Intent(MainActivity.this, MoodHistory.class);
                startActivity(moodHistory);

            }
        });

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

        boolean shouldConsumeEvent = false;

        if (velocityY < 0) {
            shouldConsumeEvent = true;
            i++;

        } else if (velocityY > 0) {
            shouldConsumeEvent = true;
            i--;

        }
        if (i < 0) {

            i = 0;

        } else if (i > Mood.values().length - 1) {

            i = Mood.values().length - 1;

        }


        backgraoundMoods.setBackgroundResource(Mood.values()[i].getColorRes());
        faceMoods.setImageResource(Mood.values()[i].getDrawableRes());



        return shouldConsumeEvent;


    }


//    private void creatAndDisplayDialog(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LinearLayout layout = new LinearLayout(this);
//        TextView message = new TextView(this);
//        final EditText inputComment = new EditText(this);
//
//        message.setText("Commentaire");
//        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
//        inputComment.setSingleLine();
//        layout.setOrientation(LinearLayout.VERTICAL);
//        layout.addView(message);
//        layout.addView(inputComment);
//        layout.setPadding(50,40,50,10);
//
//        builder.setView(layout);
//
//        builder.setNegativeButton("ANNULER", DialogInterface.OnClickListener auditeur)
//
//
//
//    }


}
