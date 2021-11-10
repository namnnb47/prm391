package com.example.it.firebasetest;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.it.firebasetest.Common.Common;
import com.example.it.firebasetest.Database.Database;
import com.example.it.firebasetest.Model.Order;
import com.example.it.firebasetest.Model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference requests;

    private Button payment;
    private Button back;

    private ImageView momo;
    private ImageView vnpay;
    private ImageView zalopay;

    private EditText name;
    private EditText phone;
    private EditText address;
    List<Order> cart = new ArrayList<>();

    int tempTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        payment = (Button) findViewById(R.id.txtPayment);
        back = (Button) findViewById(R.id.txtBack);

        momo = (ImageView) findViewById(R.id.imgMomo);
        vnpay = (ImageView) findViewById(R.id.imgVnpay);
        zalopay = (ImageView) findViewById(R.id.imgZalopay);

        name = (EditText) findViewById(R.id.txtHoVaTen);
        phone = (EditText) findViewById(R.id.txtSoDienThoai);
        address = (EditText) findViewById(R.id.txtDiaChi);

        name.setText(Common.currentUser.getName());
        phone.setText(Common.currentUser.getPhone());
        address.setText(Common.currentUser.getAddress());

        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://test-payment.momo.vn/pay/store/MOMOIQA420180417-storeid01?a=10000&b=B001221&s=601a7280711dd72bfae8c365801f5e257311a1ebd8779cf3bc4ac57c4002a978"));
                startActivity(intent);
            }
        });

        vnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=1806000&vnp_Command=pay&vnp_CreateDate=20210801153333&vnp_CurrCode=VND&vnp_IpAddr=127.0.0.1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang+%3A5&vnp_OrderType=other&vnp_ReturnUrl=https%3A%2F%2Fdomainmerchant.vn%2FReturnUrl&vnp_TmnCode=DEMOV210&vnp_TxnRef=5&vnp_Version=2.1.0&vnp_SecureHash=3e0d61a0c0534b2e36680b3f7277743e8784cc4e1d68fa7d276e79c23be7d6318d338b477910a27992f5057bb1582bd44bd82ae8009ffaf6d141219218625c42"));
                startActivity(intent);
            }
        });

        zalopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("https://sbgateway.zalopay.vn/openinapp?order=eyJ6cHRyYW5zdG9rZW4iOiIxOTA3MTkwMDAwMDExMjluOVo2VjlPIiwiYXBwaWQiOjU1M30"));
                startActivity(intent);
            }
        });


        Intent intentResult = getIntent();
        Bundle bundle = intentResult.getBundleExtra("data");
        String totalPrice = bundle.getString("totalPrice", "");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonEffect(view);
                String nameU = name.getText().toString().trim();
                String phoneU = phone.getText().toString().trim();
                String addressU = address.getText().toString().trim();


                database = FirebaseDatabase.getInstance();
                requests = database.getReference("request");

                Request request = new Request(
                        phoneU,
                        nameU,
                        addressU,
                        totalPrice,
                        new Database(getBaseContext()).getCarts()
//                        cart
                );
                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);


                    new Database(getBaseContext()).cleneCart();
                    Toast.makeText(CheckoutActivity.this,"Successfully Ordered!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CheckoutActivity.this,Home.class));
            }
        });
    }

    private void buttonEffect(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
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
}