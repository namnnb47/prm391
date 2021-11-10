package com.example.it.firebasetest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it.firebasetest.Model.User;
import com.example.it.firebasetest.Model.UserSignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SignUp extends AppCompatActivity {

    private EditText txtphoneNumber,txtname,txtaddress,txtpassword,txtconfrimpassword;
    private Button btnSubmit;
    private TextView login;
    private ImageView imageView9;

    DatabaseReference table_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtphoneNumber = findViewById(R.id.txtPhoneNumber2);
        txtname = findViewById(R.id.txtName2);
        txtaddress = findViewById(R.id.txtAddress2);
        login = (TextView) findViewById(R.id.loginNow);
        txtpassword = findViewById(R.id.txtPassword2);
        txtconfrimpassword = findViewById(R.id.txtConfirmPassowrd2);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageView9 = (ImageView) findViewById(R.id.imageView9);

        Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        imageView9.startAnimation(animSlideUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }
        });

        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("user");

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Loading...");
                mDialog.show();

                buttonEffect(view);

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mDialog.dismiss();
                        if(!txtphoneNumber.getText().toString().isEmpty() && !txtname.getText().toString().isEmpty() && !txtaddress.getText().toString().isEmpty() && !txtpassword.getText().toString().isEmpty() && !txtconfrimpassword.getText().toString().isEmpty()){
                            if(dataSnapshot.child(txtphoneNumber.getText().toString()).exists()) {
                                Toast.makeText(SignUp.this, "Phone number registered already. Please try another one", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(txtpassword.getText().toString().equals(txtconfrimpassword.getText().toString())){
                                    UserSignUp userSignUp = new UserSignUp(
                                            txtname.getText().toString(),
                                            MD5(txtpassword.getText().toString()),
                                            txtaddress.getText().toString()
                                    );
                                    table_user.child(txtphoneNumber.getText().toString()).setValue(userSignUp);
                                    Toast.makeText(SignUp.this, "Submited Successfully", Toast.LENGTH_SHORT).show();

                                    ActivityCompat.requestPermissions(SignUp.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
                                    String number = "114";
                                    Random random = new Random();
                                    int otpnumber = random.nextInt(999999);
                                    String otp = String.valueOf(otpnumber);


                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(number,null,otp,null,null);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("otp", otp);
                                    Intent intent = new Intent(SignUp.this,VerifyPhoneActivity.class);
                                    intent.putExtra("data", bundle);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(SignUp.this, "Please enter same password in both places", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                        else {
                            Toast.makeText(SignUp.this, "Every field must be filled. Please check!", Toast.LENGTH_SHORT).show();
                        }


                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void buttonEffect(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521,PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
    // mã hóa bằng MD5
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
