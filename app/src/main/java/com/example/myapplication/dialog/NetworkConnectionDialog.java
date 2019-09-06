package com.example.myapplication.dialog;

import android.content.Context;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseDialog;
import com.example.myapplication.databinding.DialogNetworkConnectionBinding;
import com.example.myapplication.utilities.AppUtils;

public class NetworkConnectionDialog extends BaseDialog<DialogNetworkConnectionBinding> {

    private Context mContext;
    private NetworkRetryCall networkRetryCall;

    public NetworkConnectionDialog(Context mContext, NetworkRetryCall networkRetryCall) {
        super(mContext);
        this.mContext = mContext;
        this.networkRetryCall = networkRetryCall;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_network_connection;
    }

    @Override
    protected void create(DialogNetworkConnectionBinding mBinding) {
        mBinding.btnRetry.setOnClickListener(v -> {
            if (AppUtils.isNetworkAvailable(mContext)) {
                dismiss();
                networkRetryCall.onRetryCall(true);
            }
        });
    }

    public interface NetworkRetryCall {
        void onRetryCall(boolean isDone);
    }

}
