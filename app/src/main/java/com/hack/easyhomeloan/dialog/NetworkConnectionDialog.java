package com.hack.easyhomeloan.dialog;

import android.content.Context;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.base.BaseDialog;
import com.hack.easyhomeloan.databinding.DialogNetworkConnectionBinding;
import com.hack.easyhomeloan.utilities.AppUtils;

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
