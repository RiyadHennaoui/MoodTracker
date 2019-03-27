package com.example.moodtracker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class DialogComments {

    public static void showCommentsDiags(MainActivity mainactivity) {

        final Dialog comments = new Dialog(mainactivity);
        comments.requestWindowFeature(Window.FEATURE_NO_TITLE);
        comments.setContentView(R.layout.alerte_dialogs_comments);

        final EditText userComments = comments.findViewById(R.id.ET_comments);

        Button btn_validate = comments.findViewById(R.id.btn_validate);
        Button btn_cancel   = comments.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                comments.cancel();

            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        comments.show();


    }

}
