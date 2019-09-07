package com.hack.easyhomeloan.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    protected T mViewDataBinding;
    protected V mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.bind(inflater.inflate(getLayoutId(), container, false));
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(mViewDataBinding);
    }

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * Create view.
     *
     * @param viewDataBinding the view
     */
    protected abstract void onViewCreated(T viewDataBinding);

    public BaseActivity getBaseActivity() {
        return BaseActivity.getBaseActivity();
    }

    private void performDependencyInjection() {
        // AndroidSupportInjection.inject(this);
    }


}
