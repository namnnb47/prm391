package com.example.it.firebasetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it.firebasetest.Common.Common;
import com.example.it.firebasetest.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText password,phoneNum;
    private Button btnSignIn, btnSignUp;
    private TextView welcome;

    DatabaseReference table_user;

    private ImageView imageView2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNum = findViewById(R.id.txtPhoneNumber);
        password = findViewById(R.id.txtPssword);
        btnSignIn = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        welcome = (TextView) findViewById(R.id.welcome);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        imageView2.startAnimation(animSlideUp);

        Animation welcomani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        welcome.startAnimation(welcomani);



        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("user");


        btnSignUp.setOnClickListener(view -> {

           // buttonEffect(view);

            Intent signUp = new Intent(MainActivity.this, SignUp.class);
            startActivity(signUp);
        });

        btnSignIn.setOnClickListener(view -> {

            //buttonEffect(view);
            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Loading...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mDialog.dismiss();
                    if(dataSnapshot.child(phoneNum.getText().toString()).exists()){
                        String phone = phoneNum.getText().toString();
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);
                        if(user.getPassword().equals(MD5(password.getText().toString()))){
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = user;

                            startActivity(homeIntent);
                            finish();
                        }

                        else {
                            Toast.makeText(MainActivity.this,"Signin failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"User not exist",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });

    }
    public static String MD5(String password){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for(byte b : result){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if(hex.length() == 1){
                    sb.append("0" + hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }
    }


}


