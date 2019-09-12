package com.hack.easyhomeloan.activities.listing.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.activities.detail.DetailActivity;
import com.hack.easyhomeloan.activities.listing.model.HomeItem;
import com.hack.easyhomeloan.activities.listing.viewholder.ProductViewHolder;
import com.hack.easyhomeloan.base.BaseActivity;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<HomeItem> homeItemArrayList;
    private ProductAdapterStatus status;

    public HomeListAdapter(Context context, ArrayList<HomeItem> homeItemArrayList, ProductAdapterStatus status) {
        this.context = context;
        this.homeItemArrayList = homeItemArrayList;
        this.status = status;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setProductName(((ProductViewHolder) holder).product_name, homeItemArrayList.get(position).getProductName());
        setProductId(((ProductViewHolder) holder).product_id, homeItemArrayList.get(position).getHomeType());
        setProductImage(((ProductViewHolder) holder).product_image, homeItemArrayList.get(position).getProductImage());
        setProductPrice(((ProductViewHolder) holder).product_price, homeItemArrayList.get(position).getProductPrice());
        setOnClickItem(((ProductViewHolder) holder).product_parent, homeItemArrayList.get(position).getProductId());
        setOnClickCancel(((ProductViewHolder) holder).product_cancel, position);
    }

    @Override
    public int getItemCount() {
        return homeItemArrayList.size();
    }

    private void setProductName(TextView textView, String text) {
        textView.setText(text);
    }

    private void setProductId(TextView textView, String text) {
        textView.setText(text);
    }

    private void setProductImage(ImageView imageView, int imageId) {
        imageView.setBackgroundResource(imageId);
    }

    private void setProductPrice(TextView textView, float price) {
        textView.setText(context.getString(R.string.currency) + "" + price);
    }

    private void setOnClickItem(RelativeLayout relativeLayout, final int id) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDashboard();
                Toast.makeText(context, "Item click: " + id, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToDashboard() {
        Bundle bundleArgs = new Bundle();
        //bundleArgs.putString(HomeActivity.KEY_USER_ID, Objects.requireNonNull(loginVM.userId.get()).toString());
        AppUtils.navigateToActivity(
                BaseActivity.getBaseActivity(),
                DetailActivity.class,
                bundleArgs,
                true
        );
    }


    private void setOnClickCancel(RelativeLayout relativeLayout, final int position) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                homeItemArrayList.remove(position);
//                notifyDataSetChanged();
//                status.DataRefresh(true);
            }
        });
    }

    public interface ProductAdapterStatus {
        void DataRefresh(boolean isRefresh);
    }
}
