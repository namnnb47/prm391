package com.example.it.firebasetest.ViewHolder;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it.firebasetest.Database.Database;
import com.example.it.firebasetest.FoodList;
import com.example.it.firebasetest.Interface.ItemClickListener;
import com.example.it.firebasetest.Model.Food;
import com.example.it.firebasetest.Model.Order;
import com.example.it.firebasetest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName;
    public TextView txtFoodId;
    public TextView txtFoodDescription;
    public TextView txtFoodPrice;
    public ImageView imgFood;
    public FloatingActionButton addToCartImg;
    public FloatingActionButton fab;
    Food currentFood;
    String foodId = "";
    public Context context;
    FirebaseDatabase database;
    DatabaseReference foods;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        txtFoodName = itemView.findViewById(R.id.food_name);
        txtFoodDescription = itemView.findViewById(R.id.food_description);
        txtFoodPrice = itemView.findViewById(R.id.food_price);
        txtFoodId = itemView.findViewById(R.id.food_id);
        imgFood = itemView.findViewById(R.id.food_image);

        addToCartImg = (FloatingActionButton) itemView.findViewById(R.id.addToCartImg);
        fab = (FloatingActionButton) itemView.findViewById(R.id.fab);

        itemView.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("food");


        addToCartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new Database(view.getContext()).addToCart(new Order(
                            txtFoodId.getText().toString(),
                            txtFoodName.getText().toString().trim(),
                            "1",
                            txtFoodPrice.getText().toString().trim(),
                            ""
                    ));

                    Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
