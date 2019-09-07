package com.hack.easyhomeloan.activities.home.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.hack.easyhomeloan.dialog.LoadingDialog;

public abstract class AbstractBaseFullScreenDialog extends DialogFragment {
    public static String TAG = AbstractBaseFullScreenDialog.class.getSimpleName();
    private ViewDataBinding viewDataBinding;
    private int style, theme;

    public AbstractBaseFullScreenDialog() {
        //  this(DialogFragment.STYLE_NORMAL, android.R.style.Theme);
    }

    public AbstractBaseFullScreenDialog(int style, int theme) {
        this.theme = theme;
        this.style = style;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setStyle(style, theme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutResourceId(), container, false);
        create(viewDataBinding);
        onFragmentReady();
        return viewDataBinding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        LoadingDialog.dismissDialog();

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
     * @param viewDataBinding the view data binding
     */
    protected abstract void create(ViewDataBinding viewDataBinding);

    protected void onFragmentReady() {

    }

}
