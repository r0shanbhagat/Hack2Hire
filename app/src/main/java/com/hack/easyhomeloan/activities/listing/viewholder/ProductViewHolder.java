package com.hack.easyhomeloan.activities.listing.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hack.easyhomeloan.R;


public class ProductViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout product_parent, product_cancel;
    public ImageView product_image;
    public TextView product_name, product_price, product_id, homeType;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        product_parent = itemView.findViewById(R.id.product_parent);
        product_image = itemView.findViewById(R.id.product_image);
        product_name = itemView.findViewById(R.id.product_name);
        product_price = itemView.findViewById(R.id.product_price);
        product_id = itemView.findViewById(R.id.product_id);
        product_cancel = itemView.findViewById(R.id.product_cancel);
    }
}
