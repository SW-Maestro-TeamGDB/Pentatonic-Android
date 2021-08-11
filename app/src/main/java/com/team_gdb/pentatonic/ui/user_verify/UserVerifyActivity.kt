package com.team_gdb.pentatonic.ui.user_verify

import android.content.Intent
import android.view.View
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.databinding.ActivityUserVerifyBinding
import com.team_gdb.pentatonic.ui.login.LoginActivity
import com.team_gdb.pentatonic.ui.register.RegisterActivity.Companion.EXTRA_NAME
import timber.log.Timber

class UserVerifyActivity : BaseActivity<ActivityUserVerifyBinding, UserVerifyViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_user_verify
    override val viewModel: UserVerifyViewModel by viewModel()
    lateinit var registerForm: RegisterForm

    override fun initStartView() {
        binding.viewModel = viewModel
        registerForm = intent.getSerializableExtra(EXTRA_NAME) as RegisterForm

        Timber.d("넘어온 회원가입 정보는 이러함 : $registerForm")

    }

    override fun initDataBinding() {
        viewModel.verifyCompleteEvent.observe(this) {
            it.getContentIfNotHandled()?.let {
                // 인증번호 전송이 완료되면 인증번호를 입력하는 EditText 보여줌
                binding.authCodeEditText.visibility = View.VISIBLE
                binding.confirmPhoneNumberButton.visibility = View.GONE
                binding.confirmAuthCodeButton.visibility = View.VISIBLE
            }
        }

        viewModel.registerCompleteEvent.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, "펜타토닉 회원이 되신 것을 축하드립니다!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun initAfterBinding() {
        binding.confirmPhoneNumberButton.setOnClickListener {
            if (binding.phoneNumberEditText.text.isNullOrBlank()) {
                binding.phoneNumberEditText.error = "휴대폰 번호를 입력해주세요"
            } else {
                viewModel.sendAuthCode()
            }
        }

        binding.confirmAuthCodeButton.setOnClickListener {
            if (binding.authCodeEditText.text.isNullOrBlank()) {
                binding.authCodeEditText.error = "인증번호를 입력해주세요"
            } else {
                viewModel.requestRegister(registerForm)
            }
        }
    }
}