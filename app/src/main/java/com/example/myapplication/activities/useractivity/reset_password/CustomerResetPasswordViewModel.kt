/*
package com.ranosys.digitalfoodmall.customerapp.module.reset_password

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ranosys.digitalfoodmall.commonmodule.api.ApiCallback
import com.ranosys.digitalfoodmall.commonmodule.api.ApiRepository
import com.ranosys.digitalfoodmall.commonmodule.models.common.ApiErrorModel
import com.ranosys.digitalfoodmall.commonmodule.models.common.CommonMessageResponse
import com.ranosys.digitalfoodmall.commonmodule.models.common.ResetPasswordRequest
import com.ranosys.digitalfoodmall.customerapp.api.ApiClientCustomer
import com.ranosys.digitalfoodmall.customerapp.base.BaseViewModel
import kotlinx.coroutines.launch

*/
/**
 * @Details view model for reset password customer module screen
 * @Author Ranosys Technologies
 * @Date 13-May-2019
 *//*


class CustomerResetPasswordViewModel(application: Application) : BaseViewModel(application) {

    var newPassword = ObservableField<String>()
    var confirmPassword = ObservableField<String>()

    var newPasswordErrorMsg = ObservableField<String>()
    var confirmPasswordErrorMsg = ObservableField<String>()

    var apiResponse = MutableLiveData<Any>()
    var viewClick: MutableLiveData<Int> = MutableLiveData()

    fun onViewClicks(view: View) {
        viewClick.value = view.id
    }

    fun resetPassword(otpId: String) {
        val apiService = ApiClientCustomer.getApiService()
        val request =
            apiService.resetPassword(ResetPasswordRequest(otpId, newPassword.get().toString()))
        viewModelScope.launch {
            ApiRepository.callApi(
                request,
                object :
                    ApiCallback<CommonMessageResponse> {
                    override fun onError(errorModel: ApiErrorModel) {
                        apiResponse.value = errorModel
                    }

                    override fun onSuccess(t: CommonMessageResponse?) {
                        apiResponse.value = t
                    }

                    override fun onException(error: Throwable) {
                        apiResponse.value = error
                    }
                })
        }
    }

    fun clearErrors() {
        newPasswordErrorMsg.set("")
        confirmPasswordErrorMsg.set("")
    }
}*/
