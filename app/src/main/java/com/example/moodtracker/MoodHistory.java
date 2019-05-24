package com.example.moodtracker;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class MoodHistory extends AppCompatActivity {

    private ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        constraintLayout = findViewById(R.id.main_layout);
        TextView txtView1 = findViewById(R.id.history_text_view_1);
        TextView txtView2 = findViewById(R.id.history_text_view_2);
        TextView txtView3 = findViewById(R.id.history_text_view_3);
        TextView txtView4 = findViewById(R.id.history_text_view_4);
        TextView txtView5 = findViewById(R.id.history_text_view_5);
        TextView txtView6 = findViewById(R.id.history_text_view_6);
        TextView txtView7 = findViewById(R.id.history_text_view_7);

        ImageView img1 = findViewById(R.id.history_img_comment1);
        ImageView img2 = findViewById(R.id.history_img_comment2);
        ImageView img3 = findViewById(R.id.history_img_comment3);
        ImageView img4 = findViewById(R.id.history_img_comment4);
        ImageView img5 = findViewById(R.id.history_img_comment5);
        ImageView img6 = findViewById(R.id.history_img_comment6);
        ImageView img7 = findViewById(R.id.history_img_comment7);

        List<DailyMood> results = new MoodDao().getLastSevenDailyMoods();

        switch (results.size()) {

            default:
            case 7:
                bindView(txtView1, img1, results.get(6));

            case 6:
                bindView(txtView2, img2, results.get(5));

            case 5:
                bindView(txtView3, img3, results.get(4));

            case 4:
                bindView(txtView4, img4, results.get(3));

            case 3:
                bindView(txtView5, img5, results.get(2));

            case 2:
                bindView(txtView6, img6, results.get(1));

            case 1:
                bindView(txtView7, img7, results.get(0));

                break;

            case 0:

                break;

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

            btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MoodHistory.this, mood.getComment(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
