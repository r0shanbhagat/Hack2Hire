/*
package com.ranosys.digitalfoodmall.customerapp.module.sign_up

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ranosys.digitalfoodmall.commonmodule.api.ApiCallback
import com.ranosys.digitalfoodmall.commonmodule.api.ApiConstant
import com.ranosys.digitalfoodmall.commonmodule.api.ApiRepository
import com.ranosys.digitalfoodmall.commonmodule.models.common.ApiErrorModel
import com.ranosys.digitalfoodmall.commonmodule.models.common.SignUpResponseModel
import com.ranosys.digitalfoodmall.customerapp.api.ApiClientCustomer
import com.ranosys.digitalfoodmall.customerapp.base.BaseViewModel
import com.ranosys.digitalfoodmall.customerapp.utils.SharePreferencesCustomer
import kotlinx.coroutines.launch

*/
/**
 * @Details a view model to handle sign up logic operation
 * @Author Ranosys Technologies
 * @Date 09-May-2019
 *//*

class CustomerSignUpViewModel(application: Application) : BaseViewModel(application) {

    var fullName = ObservableField<String>("")
    var fullNameError = ObservableField<String>("")

    var mobileNumber = ObservableField<String>("")
    var mobileNumberError = ObservableField<String>("")

    var emailAddress = ObservableField<String>("")
    var emailAddressError = ObservableField<String>("")

    var userPassword = ObservableField<String>("")
    var userPasswordError = ObservableField<String>("")

    var apiResponse = MutableLiveData<Any>()

    var viewClick: MutableLiveData<Int> = MutableLiveData()

    var accessToken = ApiConstant.BEARER + SharePreferencesCustomer.getInstance().getStringValue(
        SharePreferencesCustomer.ACCESS_TOKEN
    )


    fun callSignUpApi(deviceId: String) {
        val apiService = ApiClientCustomer.getCustomerApiServices()
        val signUpRequest =
            CustomerSignUp(
                device_id = deviceId,
                email = emailAddress.get() ?: "",
                full_name = fullName.get() ?: "",
                mobile_number = mobileNumber.get() ?: "",
                password = userPassword.get() ?: ""
            )
        val request = apiService.customerSignUpAsync(signUpRequest)
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
        }
    }

    fun onViewClicks(view: View) {
        viewClick.value = view.id
    }

    fun clearErrors() {
        fullNameError.set("")
        emailAddressError.set("")
        mobileNumberError.set("")
        userPasswordError.set("")
    }


}*/
