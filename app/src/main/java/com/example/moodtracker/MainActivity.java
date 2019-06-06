package com.example.moodtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.moodtracker.Mood.HAPPY;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final Mood DEFAULT_MOOD = HAPPY;

    private GestureDetector gestureDetector;

    private ConstraintLayout backgraoundMoods;

    private ImageView faceMoods;


    private MoodDao mMoodDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoodDao = new MoodDao();
        gestureDetector = new GestureDetector(this, this);


        backgraoundMoods = findViewById(R.id.background_moods);
        faceMoods = findViewById(R.id.face_moods);
        ImageView noteAdd = findViewById(R.id.note_add);
        ImageView history = findViewById(R.id.history);
        Button smsShare = findViewById(R.id.sms_share);


        DailyMood results = mMoodDao.getTodaysMood();


        if (results == null) {

            mMoodDao.persistMood(DEFAULT_MOOD);

        }

        displayCurrentMood();

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moodHistory = new Intent(MainActivity.this, MoodHistory.class);
                startActivity(moodHistory);

            }
        });

        noteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndDisplayDialog();
            }
        });

        smsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeSmsMessage();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        DailyMood results = mMoodDao.getTodaysMood();


        if (results == null) {

            mMoodDao.persistMood(DEFAULT_MOOD);

        }
    }

    private void composeSmsMessage() {
        DailyMood dailyMood = mMoodDao.getTodaysMood();
        //noinspection ConstantConditions it is impossible not to have a comment here.
        String message = getString(
                R.string.sms_body,
                dailyMood.getMood().name(),
                dailyMood.getComment() == null ? "" : dailyMood.getComment()
        );
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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


        boolean shouldConsumeEvent = updateMood(velocityY);
        displayCurrentMood();


        return shouldConsumeEvent;


    }

    private boolean updateMood(float velocityY) {

        //noinspection ConstantConditions it is impossible not to have a comment here.
        int i = mMoodDao.getTodaysMood().getMood().ordinal();
        Log.d("Wis", "i = " + i);
        Log.d("Wis", "velo =" + velocityY);
        boolean shouldConsumeEvent = false;

        if (velocityY < 0) {
            i++;
            shouldConsumeEvent = true;


        } else if (velocityY > 0) {
            i--;
            shouldConsumeEvent = true;


        }
        if (i < 0) {

            i = 0;

        } else if (i > Mood.values().length - 1) {

            i = Mood.values().length - 1;

        }
        mMoodDao.persistMood(Mood.values()[i]);

        return shouldConsumeEvent;
    }


    private void displayCurrentMood() {
        DailyMood dailyMood = mMoodDao.getTodaysMood();
        Log.d("Wis", "dailymood" + dailyMood);
        //noinspection ConstantConditions it is impossible not to have a comment here.
        backgraoundMoods.setBackgroundResource(dailyMood.getMood().getColorRes());
        faceMoods.setImageResource(dailyMood.getMood().getDrawableRes());
    }

    // Build a dialog for save comment of user.
    private void createAndDisplayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        TextView message = new TextView(this);
        final EditText inputComment = new EditText(this);

        message.setText(R.string.comment);
        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        inputComment.setSingleLine();
        //noinspection ConstantConditions it is impossible not to have a comment here.
        inputComment.setText(mMoodDao.getTodaysMood().getComment());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(message);
        layout.addView(inputComment);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        builder.setNegativeButton(R.string.cancel, null);

        builder.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                mMoodDao.persistComment(inputComment.getText().toString());

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
