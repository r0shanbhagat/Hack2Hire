package com.example.myapplication.dialog.beans;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.example.myapplication.R;
import com.example.myapplication.utilities.AppUtils;

public class CustomDialogModel extends BaseObservable {
    public String dialogTitle, messageText, okText, cancelText;
    public Object imageUrl;
    private Integer placeHolderDrawable;
    private boolean isCloseDialog;

    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void loadImage(ImageView imageView, Object imageUrl, Integer placeHolderDrawable) {
        if (placeHolderDrawable == null) {
            AppUtils.loadImage(imageView.getContext(), imageUrl, imageView, R.drawable.ic_default_icon_48dp);
        } else {
            AppUtils.loadImage(imageView.getContext(), imageUrl, imageView, placeHolderDrawable);
        }
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPlaceHolderDrawable() {
        return placeHolderDrawable;
    }

    public void setPlaceHolderDrawable(Integer placeHolderDrawable) {
        this.placeHolderDrawable = placeHolderDrawable;
    }

    public boolean isCloseDialog() {
        return isCloseDialog;
    }

    public void setCloseDialog(boolean closeDialog) {
        isCloseDialog = closeDialog;
    }
}
