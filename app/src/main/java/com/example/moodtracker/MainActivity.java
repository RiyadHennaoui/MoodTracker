package com.example.moodtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.threeten.bp.LocalDate;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;

    private ConstraintLayout backgraoundMoods;
    private ImageView faceMoods;
    private ImageView noteAdd;
    private ImageView history;
    private Button smsShare;
    private DailyMood dailyMood;
    private Mood defaultMood;
    private Mood consumableMood;
    private DailyMood dailyMoodInstance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(this, this);


        backgraoundMoods = findViewById(R.id.background_moods);
        faceMoods = findViewById(R.id.face_moods);
        noteAdd = findViewById(R.id.note_add);
        history = findViewById(R.id.history);
        smsShare = findViewById(R.id.sms_share);

        defaultMood = Mood.HAPPY;

        dailyMoodInstance = new DailyMood();

        RealmResults<DailyMood> results = dailyMoodInstance.saveAllMood();



        if (results.isEmpty()) {

            persistMood(defaultMood, true);

        } else {

            dailyMood = results.get(0);

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
                creatAndDisplayDialog();
            }
        });

        smsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] test = {"toto@gmail.com"};
                composeEmail(test, "salut");// a changer.
            }
        });


    }

    private void persistMood(Mood mood, boolean create) {

        Realm.getDefaultInstance().beginTransaction();
        if (create) {

            dailyMood = new DailyMood();
            dailyMood.setDate(LocalDate.now());
            dailyMood.saveMood(mood);
            Realm.getDefaultInstance().copyToRealmOrUpdate(dailyMood);

        } else {
            dailyMood.saveMood(mood);
        }

        Realm.getDefaultInstance().commitTransaction();

    }

    private void updateMood(Mood mood) {

        persistMood(mood, false);
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeSmsMessage(String message) {
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


        displayCurrentMood();


        boolean shouldConsumeEvent = checkConsumableMood(velocityY);

        if (shouldConsumeEvent) {
            updateMood(consumableMood);
        }

        return shouldConsumeEvent;


    }

    private boolean checkConsumableMood(float velocityY) {

        int i = dailyMood.getMood().ordinal();

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
        consumableMood = Mood.values()[i];

        return shouldConsumeEvent;
    }


    private void displayCurrentMood() {
        backgraoundMoods.setBackgroundResource(dailyMood.getMood().getColorRes());
        faceMoods.setImageResource(dailyMood.getMood().getDrawableRes());
    }

    // Build a dialog for save comment of user.
    private void creatAndDisplayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        TextView message = new TextView(this);
        final EditText inputComment = new EditText(this);

        message.setText("Commentaire");
        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        inputComment.setSingleLine();
        inputComment.setText(dailyMood.getComment());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(message);
        layout.addView(inputComment);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        builder.setNegativeButton("ANNULER", null);

        builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Realm.getDefaultInstance().beginTransaction();
                dailyMood.setComment(inputComment.getText().toString());
                Realm.getDefaultInstance().commitTransaction();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


}
