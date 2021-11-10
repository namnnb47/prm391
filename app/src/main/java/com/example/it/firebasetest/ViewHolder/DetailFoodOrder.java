package com.example.it.firebasetest.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.it.firebasetest.Interface.ItemClickListener;
import com.example.it.firebasetest.R;

public class DetailFoodOrder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView detail_name,detail_price,detail_quantity;

    private ItemClickListener itemClickListener;

    public DetailFoodOrder(@NonNull View itemView) {
        super(itemView);

        detail_name = itemView.findViewById(R.id.detail_name);
        detail_price = itemView.findViewById(R.id.detail_price);
        detail_quantity = itemView.findViewById(R.id.detail_quantity);


        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
