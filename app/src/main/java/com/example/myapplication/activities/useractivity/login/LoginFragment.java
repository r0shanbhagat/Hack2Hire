package com.example.myapplication.activities.useractivity.login;


import android.text.InputType;
import android.text.TextUtils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.dialog.NetworkConnectionDialog;
import com.example.myapplication.utilities.AppUtils;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    private LoginViewModel loginVM;
    private FragmentLoginBinding mBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        loginVM = ViewModelProviders.of(this).get(LoginViewModel.class);
        return loginVM;
    }

    @Override
    public int getBindingVariable() {
        return BR.loginViewModel;
    }

    @Override
    protected void onViewCreated(FragmentLoginBinding viewDataBinding) {
        mBinding = viewDataBinding;
        observeClicks();
        observeApiResponse();
    }


    private void observeClicks() {
        loginVM.viewClick.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer id) {
                if (id == R.id.tvSignUp2) {
                  //  navigateToFragment(R.id.loginFragment2, null, false, true, true);
                   // loginVM.viewClick.setValue(null);
                } else if (id == R.id.btnLogin) {
                    loginVM.clearErrors();
                    if (validateData()) {
                        if (AppUtils.isNetworkAvailable(requireContext())) {
                            LoadingDialog.show(getContext());
                            loginVM.login(AppUtils.getDeviceId(requireContext()));
                        } else {
                            new NetworkConnectionDialog(requireContext(), isDone -> {
                                LoadingDialog.show(getContext());
                                loginVM.login(AppUtils.getDeviceId(requireContext()));
                            }).show();
                        }
                    }
                   // loginVM.viewClick.setValue(null);

                } else if (id == R.id.tvForgotPassword) {
                    BaseActivity.getBaseActivity().pushFragment(null,null, new LoginFragment(),R.id.flUserActivity,"TAG1",false);

                  //  navigateToFragment(R.id.loginFragment, null, false, true, true);
                   //loginVM.viewClick.setValue(null);

                } else if (id == R.id.imViewPassword) {
                    setShowHidePasswordIcon();
                   // loginVM.viewClick.setValue(null);

                }
            }
        });

    }

    private void setShowHidePasswordIcon() {
        if (mBinding.etPassword.getInputType() == InputType.TYPE_CLASS_TEXT ||
                mBinding.etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            mBinding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mBinding.etPassword.setSelection(mBinding.etPassword.getText().length());
            mBinding.imViewPassword.setImageResource(R.mipmap.ic_launcher);
        } else {
            mBinding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mBinding.etPassword.setSelection(mBinding.etPassword.getText().length());
            mBinding.imViewPassword.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

    private void observeApiResponse() {
        loginVM.apiResponse.observe(this, new Observer() {
            @Override
            public void onChanged(Object response) {
                           /* if(response instanceof SignUpResponseModel){
                                AppPreferences.getInstance().setUserLogin(true);
                               // UtilityHelperCustomer.updateDeviceDetail(requireContext());
                                loginVM.apiResponse.setValue(null);
                            }else   if(response instanceof Throwable) {
                             new CustomDialog.Builder(requireContext())
                                     .setDialogTitle("Heads Up!")
                                     .setMessageText(getString(R.string.error_msg))
                                     .build();
                                loginVM.apiResponse.setValue(null);
                            }*/
            }
        });

    }


    private boolean validateData() {
        if (TextUtils.isEmpty(loginVM.mobileNumber.get().toString())
                || loginVM.mobileNumber.get().toString().length() < 10) {
            loginVM.mobileNumberErrorMsg.set(getString(R.string.please_provide_valid_mobile));
            return false;
        }
        if (TextUtils.isEmpty(loginVM.userPassword.get().toString())) {
            loginVM.userPasswordErrorMsg.set(getString(R.string.please_enter_password));
            return false;
        }
        return true;
    }


}
