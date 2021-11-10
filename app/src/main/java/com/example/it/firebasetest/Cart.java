package com.example.it.firebasetest;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it.firebasetest.Common.Common;
import com.example.it.firebasetest.Database.Database;
import com.example.it.firebasetest.Model.Order;
import com.example.it.firebasetest.Model.Request;
import com.example.it.firebasetest.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;
    public ImageView deleteItemCart;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    int tempTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("request");

        //init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        deleteItemCart = findViewById(R.id.deleteItemCart);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);



        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //buttonEffect(view);

//                Request request = new Request(
//                        Common.currentUser.getPhone(),
//                        Common.currentUser.getName(),
//                        Common.currentUser.getAddress(),


//                );

//
//                if(tempTotal != 0) {
//                    //submit to firebase
//                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
//
//                    //Delete Cart
//                    new Database(getBaseContext()).cleneCart();
//                    Toast.makeText(Cart.this,"Successfully Ordered!", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("totalPrice", txtTotalPrice.getText().toString());


                        Intent intent  = new Intent(Cart.this, CheckoutActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
//                }
//                else {
//                    Toast.makeText(Cart.this,"Cart is empty",Toast.LENGTH_SHORT).show();
//                }



            }
        });

        loadListFood();
        content();

    }

    private void content() {
        loadListFood();
        refresh(1000);
    }

    private void refresh(int mili) {
        final Handler handler = new Handler();
        final  Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,mili);
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

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);


        //Calculate price
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        tempTotal = total;



        txtTotalPrice.setText(" " +total + "VND");

    }
}
