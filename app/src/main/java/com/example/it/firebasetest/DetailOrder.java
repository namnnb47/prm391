package com.example.it.firebasetest;

import static android.R.layout.simple_list_item_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.it.firebasetest.Common.Common;
import com.example.it.firebasetest.Interface.ItemClickListener;
import com.example.it.firebasetest.Model.Order;
import com.example.it.firebasetest.Model.Request;
import com.example.it.firebasetest.ViewHolder.DetailFoodOrder;
import com.example.it.firebasetest.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class DetailOrder extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    private ArrayAdapter<Order> listViewAdapter;

    private final List<Order> myCreditList = new ArrayList<Order>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("request");

        recyclerView = findViewById(R.id.listDetail);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intentResult = getIntent();
        Bundle bundle = intentResult.getBundleExtra("data");
        String id = bundle.getString("id", "");
        String name = bundle.getString("nameFood", "");
        String price = bundle.getString("priceFood", "");
        String phone = bundle.getString("phone", "");
        String quantity = bundle.getString("quantityFood", "");


//        Query query = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child("request")
//                .orderByChild("phone")
//                .equalTo(phone);
//
//        FirebaseRecyclerOptions<Request> options =
//                new FirebaseRecyclerOptions.Builder<Request>()
//                        .setQuery(query, Request.class)
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Request, DetailFoodOrder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull DetailFoodOrder holder, int position, @NonNull Request model) {
//                holder.detail_name.setText(name);
//                holder.detail_price.setText(price);
//                holder.detail_quantity.setText(quantity);
//                final Request clickItem = model;
//
//            }
//
//
//            @Override
//            public DetailFoodOrder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.order_layout, parent, false);
//
//                return new DetailFoodOrder(view);
//            }
//        };
//        recyclerView.setAdapter(adapter);

    }

//    private void loadOrders(String id) {
//
//
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
