/*
package com.ranosys.digitalfoodmall.customerapp.module.reset_password

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ranosys.digitalfoodmall.commonmodule.databinding.ToolbarUserActivityBinding
import com.ranosys.digitalfoodmall.commonmodule.models.common.ApiErrorModel
import com.ranosys.digitalfoodmall.commonmodule.models.common.CommonMessageResponse
import com.ranosys.digitalfoodmall.commonmodule.utils.CommonDialogsUtils
import com.ranosys.digitalfoodmall.commonmodule.utils.PASSWORD_LENGTH
import com.ranosys.digitalfoodmall.commonmodule.utils.PASSWORD_PATTERN
import com.ranosys.digitalfoodmall.customerapp.BR
import com.ranosys.digitalfoodmall.customerapp.R
import com.ranosys.digitalfoodmall.customerapp.activity.CustomerAuthViewModel
import com.ranosys.digitalfoodmall.customerapp.activity.ToolbarType
import com.ranosys.digitalfoodmall.customerapp.base.BaseFragment
import com.ranosys.digitalfoodmall.customerapp.databinding.FragmentCustomeResetPasswordBinding
import com.ranosys.digitalfoodmall.customerapp.utils.UtilityHelperCustomer

*/
/**
 * @Details reset password fragment for customer module
 * @Author Ranosys Technologies
 * @Date 13-May-2019
 *//*


class CustomerResetPasswordFragment :
    BaseFragment<FragmentCustomeResetPasswordBinding, CustomerResetPasswordViewModel>() {

    private val resetPasswordViewModel: CustomerResetPasswordViewModel by lazy {
        ViewModelProviders.of(this).get(CustomerResetPasswordViewModel::class.java)
    }

    private val commonRegisterViewModel: CustomerAuthViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(CustomerAuthViewModel::class.java)
    }

    lateinit var mBinding: FragmentCustomeResetPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        observeClicks()
        observeApiResponse()
    }

    private fun observeClicks() {
        resetPasswordViewModel.viewClick.observe(this, Observer {
            when (it) {
                mBinding.imViewNewPassword.id -> {
                    changePasswordMask(mBinding.etNewPassword, mBinding.imViewNewPassword)
                    resetPasswordViewModel.viewClick.value = null
                }
                mBinding.imViewConfirmPassword.id -> {
                    changePasswordMask(mBinding.etConfirmPassword, mBinding.imViewConfirmPassword)
                    resetPasswordViewModel.viewClick.value = null
                }
                mBinding.btnChangePassword.id -> {
                    resetPasswordViewModel.clearErrors()
                    if (validateData()) {

                        if (UtilityHelperCustomer.isConnectionAvailable(requireContext())) {
                            showLoading()
                            resetPasswordViewModel.resetPassword(commonRegisterViewModel.otpId)
                        } else {
                            CommonDialogsUtils.showNetworkConnectionDialog(requireContext()) {
                                showLoading()
                                resetPasswordViewModel.resetPassword(commonRegisterViewModel.otpId)
                            }
                        }
                    }
                    resetPasswordViewModel.viewClick.value = null
                }
            }
        })
    }

    private fun validateData(): Boolean {
        if (resetPasswordViewModel.newPassword.get().isNullOrEmpty()) {
            resetPasswordViewModel.newPasswordErrorMsg.set(getString(R.string.please_provide_new_password))
            return false
        } else {
            if (resetPasswordViewModel.newPassword.get()?.length!! < PASSWORD_LENGTH || resetPasswordViewModel.newPassword.get()?.matches(
                    Regex(PASSWORD_PATTERN)
                )!!.not()
            ) {
                resetPasswordViewModel.newPasswordErrorMsg.set(getString(R.string.miss_match_password_pattern))
                return false
            }
        }
        if (resetPasswordViewModel.confirmPassword.get().isNullOrEmpty()) {
            resetPasswordViewModel.confirmPasswordErrorMsg.set(getString(R.string.please_confirm_password))
            return false
        } else {
            if (resetPasswordViewModel.confirmPassword.get() != resetPasswordViewModel.newPassword.get()) {
                resetPasswordViewModel.confirmPasswordErrorMsg.set(getString(R.string.password_does_not_match))
                return false
            }
        }
        return true
    }

    private fun observeApiResponse() {
        resetPasswordViewModel.apiResponse.observe(this, Observer {
            hideLoading()
            when (it) {
                is CommonMessageResponse -> {
                    CommonDialogsUtils.showCommonDialog(
                        requireActivity(),
                        getString(R.string.title_success),
                        it.message,
                        false,
                        false,
                        object : CommonDialogsUtils.DialogClick {
                            override fun onClick() {
                                navigateToFragment(
                                    R.id.customerLoginFragment,
                                    R.id.customerLoginFragment,
                                    true,
                                    showEnterAnim = false
                                )
                            }
                        }
                    )
                    resetPasswordViewModel.apiResponse.value = null
                }
                is ApiErrorModel -> {
                    CommonDialogsUtils.showApiErrorDialog(requireActivity(), it)
                    resetPasswordViewModel.apiResponse.value = null
                }
                is Throwable -> {
                    CommonDialogsUtils.showApiErrorDialog(
                        requireActivity(),
                        null
                    )
                    resetPasswordViewModel.apiResponse.value = null
                }
            }
        })
    }

    private fun changePasswordMask(editText: EditText, imViewPasswordMask: ImageView) {
        if (editText.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setSelection(0, editText.text.length)
            imViewPasswordMask.setImageResource(R.mipmap.ic_eye_green)
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(0, editText.text.length)
            imViewPasswordMask.setImageResource(R.mipmap.ic_eye)
        }
    }

    override fun onResume() {
        super.onResume()
        setToolbarType(ToolbarType.ONLY_BACK_TRANSPARENT, "", "", true, null, null)
        (getToolbar() as ToolbarUserActivityBinding).toolBarLeftIcon.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun getLayoutId() = R.layout.fragment_custome_reset_password

    override fun getBindingVariable() = BR.customerResetPassword

    override fun getViewModel() = resetPasswordViewModel

}*/
