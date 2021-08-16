package com.team_gdb.pentatonic.ui.login

import android.content.Intent
import android.widget.Toast
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.base.BaseApplication
import com.team_gdb.pentatonic.databinding.ActivityLoginBinding
import com.team_gdb.pentatonic.ui.home.HomeActivity
import com.team_gdb.pentatonic.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel

        Timber.d("JWT Token ${BaseApplication.prefs.token}")
        if (!BaseApplication.prefs.token.isNullOrBlank()) {  // JWT Token 로컬에 저장되어있다면 자동 로그인
            Timber.d("JWT Token Stored : ${BaseApplication.prefs.token}")
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun initDataBinding() {
        viewModel.loginCompleteEvent.observe(this) {
            it.getContentIfNotHandled()?.let {  // Event Content 로 JWT Token 넘어옴
                if (it.isBlank()) {
                    Toast.makeText(
                        this,
                        "회원 정보가 일치하지 않습니다! 확인 후 다시 로그인 해주세요",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    BaseApplication.prefs.token = it
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun initAfterBinding() {
        // 회원가입 페이지로 이동
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // 비어있는 필드가 없다면 로그인 요청 보냄
        binding.loginButton.setOnClickListener {
            if (!isThereAnythingEmpty()) {
                viewModel.login()
            }
        }
    }

    /**
     *  비어있는 EditText 모두 오류 처리, 패스워드 확인란 일치 여부 확인
     *  @return : 문제 없었으면 true, 비어있는 게 있었으면 false
     */
    private fun isThereAnythingEmpty(): Boolean {
        var isEmpty = false
        val fieldList = listOf(
            binding.idEditText,
            binding.passwordEditText,
        )

        // 비어있는 필드에 대하여 오류 발생
        fieldList.forEach {
            if (it.text.isNullOrBlank()) {
                it.error = "필수 입력 항목입니다"
                isEmpty = true
            }
        }

        return isEmpty
    }
}