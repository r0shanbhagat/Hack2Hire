package com.example.myapplication.base;

import android.view.View;

public interface OnItemClickListener<T> {
        void onItemViewClick(View view, T viewModel);
    }
