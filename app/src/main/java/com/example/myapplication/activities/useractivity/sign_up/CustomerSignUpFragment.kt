/*
package com.ranosys.digitalfoodmall.customerapp.module.sign_up

import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ranosys.digitalfoodmall.commonmodule.databinding.ToolbarUserActivityBinding
import com.ranosys.digitalfoodmall.commonmodule.models.common.ApiErrorModel
import com.ranosys.digitalfoodmall.commonmodule.models.common.SignUpResponseModel
import com.ranosys.digitalfoodmall.commonmodule.runtimepermission.RunTimePermissionHelper
import com.ranosys.digitalfoodmall.commonmodule.utils.*
import com.ranosys.digitalfoodmall.customerapp.BR
import com.ranosys.digitalfoodmall.customerapp.R
import com.ranosys.digitalfoodmall.customerapp.activity.CustomerAuthViewModel
import com.ranosys.digitalfoodmall.customerapp.activity.ToolbarType
import com.ranosys.digitalfoodmall.customerapp.base.BaseFragment
import com.ranosys.digitalfoodmall.customerapp.databinding.FragmentCustomerSignUpBinding
import com.ranosys.digitalfoodmall.customerapp.utils.UtilityHelperCustomer

*/
/**
 * @Details a fragment to show customer sign up screen
 * @Author Ranosys Technologies
 * @Date 09-May-2019
 *//*

class CustomerSignUpFragment :
    BaseFragment<FragmentCustomerSignUpBinding, CustomerSignUpViewModel>() {

    private val signUpViewModel: CustomerSignUpViewModel by lazy {
        ViewModelProviders.of(this).get(CustomerSignUpViewModel::class.java)
    }

    private val commonRegisterViewModel: CustomerAuthViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(CustomerAuthViewModel::class.java)
    }

    private lateinit var mBinding: FragmentCustomerSignUpBinding
    private lateinit var permissionHelper: RunTimePermissionHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()

        permissionHelper = RunTimePermissionHelper(requireActivity())
        observeClicks()
        observeApiResponses()
        setSpannableText()
    }

    private fun observeApiResponses() {
        signUpViewModel.apiResponse.observe(this, Observer { response ->
            hideLoading()
            when (response) {
                is SignUpResponseModel -> {
                    navigateToOtpVerification(response)
                    signUpViewModel.apiResponse.value = null
                }
                is ApiErrorModel -> {
                    CommonDialogsUtils.showApiErrorDialog(
                        requireActivity(),
                        response
                    )
                    signUpViewModel.apiResponse.value = null
                }
                is Throwable -> {
                    CommonDialogsUtils.showApiErrorDialog(
                        requireActivity(),
                        null
                    )
                    signUpViewModel.apiResponse.value = null
                }
            }
        })
    }

    private fun navigateToOtpVerification(response: SignUpResponseModel) {
        commonRegisterViewModel.otpId = response.otp_id
        commonRegisterViewModel.otpExpireTime = response.seconds_to_expire
        commonRegisterViewModel.fromScreen = SCREEN_SIGN_UP
        navigateToFragment(
            R.id.OTPVerificationFragment,
            bundleOf(Constant.MOBILE_NUMBER to signUpViewModel.mobileNumber.get()),
            null
        )
    }

    private fun observeClicks() {
        signUpViewModel.viewClick.observe(this, Observer {
            when (it) {
                mBinding.btnSignUp.id -> {
                    signUpViewModel.clearErrors()
                    if (isDataValid()) {
                        if (UtilityHelperCustomer.isConnectionAvailable(requireActivity())) {
                            showLoading()
                            signUpViewModel.callSignUpApi(
                                UtilityHelperCustomer.getDeviceId(
                                    requireContext()
                                )
                            )
                        } else {
                            CommonDialogsUtils.showNetworkConnectionDialog(requireContext()) {
                                showLoading()
                                signUpViewModel.callSignUpApi(
                                    UtilityHelperCustomer.getDeviceId(
                                        requireContext()
                                    )
                                )
                            }
                        }
                    }
                    signUpViewModel.viewClick.value = null
                }
                mBinding.imViewPassword.id -> {
                    if (mBinding.etPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        mBinding.etPassword.inputType =
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        mBinding.etPassword.setSelection(mBinding.etPassword.text?.length ?: 0)
                        mBinding.imViewPassword.setImageResource(R.mipmap.ic_eye_green)
                    } else {
                        mBinding.etPassword.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        mBinding.etPassword.setSelection(mBinding.etPassword.text?.length ?: 0)
                        mBinding.imViewPassword.setImageResource(R.mipmap.ic_eye)
                    }
                    signUpViewModel.viewClick.value = null
                }
            }

        })
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if (signUpViewModel.fullName.get().isNullOrEmpty()) {
            isValid = false
            signUpViewModel.fullNameError.set(getString(R.string.empty_name))
        }
        if (signUpViewModel.mobileNumber.get().isNullOrEmpty()) {
            isValid = false
            signUpViewModel.mobileNumberError.set(getString(R.string.empty_mobile_number))
        } else if (signUpViewModel.mobileNumber.get().toString().length > MOBILE_NUMBER_LENGTH_MAX || signUpViewModel.mobileNumber.get().toString().length < MOBILE_NUMBER_LENGTH_MIN) {
            isValid = false
            signUpViewModel.mobileNumberError.set(getString(R.string.invalid_mobile_number))
        }
        if (signUpViewModel.emailAddress.get().isNullOrEmpty()) {
            signUpViewModel.emailAddressError.set(getString(R.string.empty_email))
            isValid = false
        } else if (Patterns.EMAIL_ADDRESS.matcher(signUpViewModel.emailAddress.get()!!).matches().not()) {
            signUpViewModel.emailAddressError.set(getString(R.string.invalid_email))
            isValid = false
        }
        when {
            signUpViewModel.userPassword.get().isNullOrEmpty() -> {
                isValid = false
                signUpViewModel.userPasswordError.set(getString(R.string.empty_password))
            }
            signUpViewModel.userPassword.get()?.length!! < PASSWORD_LENGTH -> {
                isValid = false
                signUpViewModel.userPasswordError.set(getString(R.string.error_password_length))
            }
            signUpViewModel.userPassword.get()?.matches(
                Regex(PASSWORD_PATTERN)
            )!!.not()
            -> {
                isValid = false
                signUpViewModel.userPasswordError.set(getString(R.string.miss_match_password_pattern))
            }
        }
        if (isValid && mBinding.checkBoxTC.isChecked.not()) {
            CommonDialogsUtils.showCommonDialog(
                requireActivity(), getString(R.string.heads_up), getString(
                    R.string.accept_terms_conditions_warning
                ), false, false, null
            )
            isValid = false
        }
        return isValid
    }

    private fun setSpannableText() {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
                if (UtilityHelperCustomer.isConnectionAvailable(requireContext())) {
                    navigateToFragment(R.id.termsConditionsFragmentCustomer)
                } else {
                    CommonDialogsUtils.showNetworkConnectionDialog(requireContext()) {
                        navigateToFragment(R.id.termsConditionsFragmentCustomer)
                    }
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = requireActivity().getColor(R.color.yellow)
            }
        }

        val checkBoxText = getString(R.string.terms_and_condition_head).plus(" ")
            .plus(getString(R.string.terms_and_conditions)).plus(" ")
            .plus(getString(R.string.terms_and_condition_tail))

        val spannable = SpannableStringBuilder(checkBoxText)

        spannable.setSpan(
            clickableSpan,
            checkBoxText.indexOf(getString(R.string.terms_and_conditions)),
            checkBoxText.indexOf(getString(R.string.terms_and_conditions)) + getString(R.string.terms_and_conditions).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mBinding.tvTermsConditionsStatement.text = spannable
        mBinding.tvTermsConditionsStatement.movementMethod = LinkMovementMethod.getInstance()
    }


    override fun onResume() {
        super.onResume()
        setToolbarType(
            ToolbarType.ONLY_BACK_TRANSPARENT,
            "",
            "",
            true,
            null,
            null
        )
        (getToolbar() as ToolbarUserActivityBinding).toolBarScreenName.visibility = View.VISIBLE
        (getToolbar() as ToolbarUserActivityBinding).toolBarScreenName.text =
            getString(R.string.sign_up)
        (getToolbar() as ToolbarUserActivityBinding).toolBarScreenName.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple
            )
        )

        (getToolbar() as ToolbarUserActivityBinding).toolBarLeftIcon.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (getToolbar() as ToolbarUserActivityBinding).toolBarScreenName.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black_toolbar_text
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun getLayoutId() = R.layout.fragment_customer_sign_up

    override fun getBindingVariable() = BR.customerSignUpViewModel

    override fun getViewModel() = signUpViewModel

}*/
