package com.example.moodtracker;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MoodHistory extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView txtView1;
    private TextView txtView2;
    private TextView txtView3;
    private TextView txtView4;
    private TextView txtView5;
    private TextView txtView6;
    private TextView txtView7;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        constraintLayout = findViewById(R.id.main_layout);
        txtView1 = findViewById(R.id.history_text_view_1);
        txtView2 = findViewById(R.id.history_text_view_2);
        txtView3 = findViewById(R.id.history_text_view_3);
        txtView4 = findViewById(R.id.history_text_view_4);
        txtView5 = findViewById(R.id.history_text_view_5);
        txtView6 = findViewById(R.id.history_text_view_6);
        txtView7 = findViewById(R.id.history_text_view_7);

        img1 = findViewById(R.id.history_img_comment1);
        img2 = findViewById(R.id.history_img_comment2);
        img3 = findViewById(R.id.history_img_comment3);
        img4 = findViewById(R.id.history_img_comment4);
        img5 = findViewById(R.id.history_img_comment5);
        img6 = findViewById(R.id.history_img_comment6);
        img7 = findViewById(R.id.history_img_comment7);

        RealmResults<DailyMood> results = Realm.getDefaultInstance().where(DailyMood.class).findAll();

        switch (results.size()){

            default:
            case 7 :
                bindView(txtView1, img1, results.get(6));

            case 6 :
                bindView(txtView2, img2, results.get(5));

            case 5 :
                bindView(txtView3, img3, results.get(4));

            case 4 :
                bindView(txtView4, img4, results.get(3));

            case 3 :
                bindView(txtView5, img5, results.get(2));


            case 2 :
                bindView(txtView6, img6, results.get(1));

            case 1 :
                bindView(txtView7, img7, results.get(0));







        }


    }

    private void bindView(TextView txtview, ImageView btnComment, final DailyMood mood) {
        txtview.setBackgroundResource(mood.getMood().getColorRes());

        ConstraintSet constraintTextView = new ConstraintSet();
        constraintTextView.clone(constraintLayout);
        constraintTextView.constrainPercentWidth(txtview.getId(), mood.getMood().getMoodPercent());
        constraintTextView.applyTo(constraintLayout);

        if (mood.getComment() == null) {

            btnComment.setVisibility(View.GONE);

        } else {

            btnComment.setVisibility(View.VISIBLE);
            // ajouter un listner sur le comment.
            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MoodHistory.this, mood.getComment(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
