package com.example.myapplication.activities.useractivity.login;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.base.BaseViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class LoginViewModel extends BaseViewModel {
    MutableLiveData apiResponse = new MutableLiveData<>();
    MutableLiveData viewClick=new MutableLiveData<Integer>();

    public ObservableField mobileNumber =new  ObservableField<String>();
    public ObservableField userPassword = new ObservableField<String>();

    public ObservableField mobileNumberErrorMsg = new ObservableField<String>();
    public ObservableField userPasswordErrorMsg =new ObservableField<String>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
   public void onViewClicks(View view) {
        viewClick.setValue(view.getId());
    }

    void login(String deviceId) {
      /*  val apiService = ApiClientCustomer.getCustomerApiServices()
        val loginRequest = LoginRequest(
                deviceId,
                ApiConstant.DEVICE_TYPE.toString(),
                mobileNumber.get() ?: "",
                userPassword.get() ?: ""
        )
        val request = apiService.loginCustomerAsync(loginRequest)

        viewModelScope.launch {
            ApiRepository.callApi(request, object : ApiCallback<SignUpResponseModel> {
                override fun onException(error: Throwable) {
                    apiResponse.value = error
                }

                override fun onError(errorModel: ApiErrorModel) {
                    apiResponse.value = errorModel
                }

                override fun onSuccess(t: SignUpResponseModel?) {
                    apiResponse.value = t
                }
            })
        }*/
    }
    void clearErrors() {
        userPasswordErrorMsg.set("");
        mobileNumberErrorMsg.set("");
    }


}
