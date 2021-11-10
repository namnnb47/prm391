package com.example.it.firebasetest;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyPhoneActivity extends AppCompatActivity {

    private EditText txtOtp;
    private Button sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        txtOtp = (EditText) findViewById(R.id.txtOtp);
        sendOtp = (Button) findViewById(R.id.Verify);



        Intent intentResult = getIntent();
        Bundle bundle = intentResult.getBundleExtra("data");
        String otpResult = bundle.getString("otp", "");

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = txtOtp.getText().toString().trim();

                if (OTP.isEmpty() && OTP.length() < 6) {
                    txtOtp.setError("Enter Otp");
                    txtOtp.requestFocus();
                    return;
                }
                if(OTP.equals(otpResult)){
                    Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    txtOtp.setError("Otp Error");
                    txtOtp.requestFocus();
                }

            }
        });
    }

}