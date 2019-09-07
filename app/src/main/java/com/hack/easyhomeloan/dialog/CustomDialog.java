package com.hack.easyhomeloan.dialog;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.base.BaseDialog;
import com.hack.easyhomeloan.databinding.DialogCustomBinding;
import com.hack.easyhomeloan.dialog.beans.CustomDialogModel;


public class CustomDialog extends BaseDialog {

    public static String TAG = CustomDialog.class.getSimpleName();
    // private Dialog dialog;
    private String dialogTitle, messageText, okText, cancelText;
    private IDialogClick clickListener;
    private int requestCode;
    private boolean isDismissOnCancel, isDismissOnOk, isDismissOnClick;
    private Object objectValue;
    private Object imageUrl;
    private Integer placeHolderDrawable;
    private boolean isCloseDialog;


    private CustomDialog(Builder builder) {
        super(builder.context, builder.isCancelOnTouchOutside, builder.isCancelable, builder.isAnimated);
        initValue(builder);
    }

    private void initValue(Builder builder) {
        this.dialogTitle = builder.dialogTitle;
        this.messageText = builder.messageText;
        this.objectValue = builder.objectValue;
        this.okText = builder.okText;
        this.cancelText = builder.cancelText;
        this.isCloseDialog = builder.isCloseDialog;
        this.clickListener = builder.clickListener;
        this.requestCode = builder.requestCode;
        this.isDismissOnCancel = builder.isDismissOnCancel;
        this.isDismissOnOk = builder.isDismissOnOk;
        this.isDismissOnClick = builder.isDismissOnClick;
        this.imageUrl = builder.imageUrl;
        this.placeHolderDrawable = builder.placeHolder;

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_custom;
    }

    @Override
    protected void create(ViewDataBinding viewDataBinding) {
        DialogCustomBinding dialogCustomBinding = (DialogCustomBinding) viewDataBinding;
        dialogCustomBinding.setCustomDialog(this);
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setCancelText(cancelText);
        customDialogModel.setDialogTitle(dialogTitle);
        customDialogModel.setImageUrl(imageUrl);
        customDialogModel.setPlaceHolderDrawable(placeHolderDrawable);
        customDialogModel.setMessageText(messageText);
        customDialogModel.setOkText(okText);
        customDialogModel.setCancelText(cancelText);
        customDialogModel.setCloseDialog(isCloseDialog);
        dialogCustomBinding.setCustomDialogModel(customDialogModel);
    }


    public void onClick(View view) {
        dismissDialog(view);
        if (null != clickListener) {
            clickListener.dialogMessageCallback(view, objectValue, requestCode);
        }
    }

    private void dismissDialog(View view) {
        if (isDismissOnClick ||
                (isDismissOnOk && view.getId() == R.id.tvOk) ||
                (isDismissOnCancel && view.getId() == R.id.tvCancel) || (view.getId() == R.id.ivCross)) {
            dismiss();
        }
    }

    public static class Builder {
        private Context context;
        private IDialogClick clickListener;
        private int requestCode;
        private String dialogTitle, messageText, okText, cancelText;
        private Object objectValue;
        private int themeResId;
        private boolean isCancelOnTouchOutside, isCancelable, isAnimated, isCloseDialog;
        private boolean isDismissOnCancel, isDismissOnOk, isDismissOnClick;
        private Object imageUrl;
        private Integer placeHolder;


        public Builder(Context context) {
            this.context = context;
        }


        public Builder setImageUrl(Object imgUrl) {
            imageUrl = imgUrl;
            return this;
        }

        public Builder setPlaceHolderDrawable(Integer placeHolderDrawable) {
            placeHolder = placeHolderDrawable;
            return this;
        }

        public Builder setClickListener(IDialogClick clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder setDialogTitle(String dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public Builder setObjectValue(Object objectValue) {
            this.objectValue = objectValue;
            return this;
        }

        public Builder setMessageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder setCustomTheme(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public Builder setDismissOnCancel(boolean dismissOnCancel) {
            isDismissOnCancel = dismissOnCancel;
            return this;
        }

        public Builder setDismissOnOk(boolean dismissOnOk) {
            isDismissOnOk = dismissOnOk;
            return this;
        }

        public Builder setDismissOnClick(boolean dismissOnClick) {
            isDismissOnClick = dismissOnClick;
            return this;
        }

        public Builder setAnimated(boolean animated) {
            isAnimated = animated;
            return this;
        }

        public Builder setCloseDialog(boolean closeDialog) {
            isCloseDialog = closeDialog;
            return this;
        }

        public CustomDialog build() {
            CustomDialog customDialog = null;
            if (themeResId > 0) {
                customDialog = new CustomDialog(this);
            } else {
                customDialog = new CustomDialog(this);
            }
            return customDialog;
        }
    }


}

