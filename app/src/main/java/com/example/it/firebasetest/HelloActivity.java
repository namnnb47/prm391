package com.example.it.firebasetest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class HelloActivity extends AppCompatActivity {

    DatabaseReference table_user;
    ImageView imageVieww;
    TextView textView;

    private ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //deploy
        AppCenter.start(getApplication(), "2debc4e6-2614-4d84-a3be-8611d04a2561",
                Analytics.class, Crashes.class);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);

        layout = (ConstraintLayout) findViewById(R.id.layout);
        //layout.setAnimation(zoomin);
        layout.setAnimation(zoomout);
        imageVieww=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.textView7);
        imageVieww.animate().alpha(0f).setDuration(0);
        imageVieww.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageVieww.animate().alpha(1f).setDuration(800);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                table_user = database.getReference("user");


                        AlertDialog.Builder builder = new AlertDialog.Builder(HelloActivity.this);
//                        builder.setMessage("Check whether you have verified your details, Otherwise please verify");
//                        builder.setCancelable(false);
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {

                              //  dialog.dismiss();
                                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

//                            }
//                        });
//                        AlertDialog alert = builder.create();
//                        alert.show();


            }
        }, 3000);


    }
}