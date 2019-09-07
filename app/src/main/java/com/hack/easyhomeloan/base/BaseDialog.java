package com.hack.easyhomeloan.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hack.easyhomeloan.R;

public abstract class BaseDialog<V extends ViewDataBinding> extends Dialog {

    private boolean isCancelOnTouchOutside, isCancelable, isAnimated;

    /**
     * Instantiates a new Abstract base dialog.
     *
     * @param context the m context
     */
    public BaseDialog(Context context) {
        super(context, R.style.DialogTheme);
    }

    /**
     * Instantiates a new Abstract base dialog.
     *
     * @param context    the context
     * @param themeResId the theme res id
     */
    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * Instantiates a new Abstract base dialog.
     *
     * @param context                the context
     * @param themeResId             the theme res id
     * @param isCancelOnTouchOutside the is cancel on touch outside
     * @param isCancelable           the is cancelable
     * @param isAnimated             the is animated
     */
    public BaseDialog(Context context, int themeResId, boolean isCancelOnTouchOutside, boolean isCancelable, boolean isAnimated) {
        super(context, R.style.DialogTheme);
        setDialogProperty(isCancelOnTouchOutside, isCancelable, isAnimated);
    }

    public BaseDialog(Context context, boolean isCancelOnTouchOutside, boolean isCancelable, boolean isAnimated) {
        super(context, R.style.DialogTheme);
        setDialogProperty(isCancelOnTouchOutside, isCancelable, isAnimated);
    }

    /**
     * Sets dialog height width.
     *
     * @param context the context
     * @param dialog  the dialog
     */
    private void setDialogHeightWidth(@NonNull Context context, @NonNull Dialog dialog) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        //   int height = metrics.heightPixels;
        if (null != dialog.getWindow()) {
            dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setDialogProperty(boolean isCancelOnTouchOutside, boolean isCancelable, boolean isAnimated) {
        this.isCancelOnTouchOutside = isCancelOnTouchOutside;
        this.isCancelable = isCancelable;
        this.isAnimated = isAnimated;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        V mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutResourceId(), null, false);
        setContentView(mBinding.getRoot());
        if (isAnimated) {
            setDialogAnimationEnter(this);
        }
        setCanceledOnTouchOutside(isCancelOnTouchOutside);
        setCancelable(isCancelable);
        setDialogHeightWidth(getContext(), this);
        create(mBinding);
    }

    @Override
    protected void onStop() {
        dismiss();
        super.onStop();
    }

    /**
     *
     * @param dialog
     */
    private void setDialogAnimationEnter(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogEnterAnimation;
    }


    /**
     * Gets layout resource id.
     *
     * @return the layout resource id
     */
    protected abstract int getLayoutResourceId();

    /**
     * Create.
     *
     * @param mBinding the view data binding
     */
    protected abstract void create(V mBinding);

    public interface IDialogClick<T> {
        void dialogMessageCallback(View view, T objectText, int requestCode);
    }

}



