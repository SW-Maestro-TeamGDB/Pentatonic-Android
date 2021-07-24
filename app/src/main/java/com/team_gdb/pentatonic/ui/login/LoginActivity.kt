package com.team_gdb.pentatonic.ui.login

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityLoginBinding
import com.team_gdb.pentatonic.ui.home.HomeActivity
import com.team_gdb.pentatonic.ui.register.RegisterActivity
import com.team_gdb.pentatonic.util.makeStatusBarTransparent
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.loginCompleteEvent.observe(this) {
            if (it.hasBeenHandled){
                val userToken = viewModel.userToken.value
                if (userToken.isNullOrBlank()){
                    Toast.makeText(this, "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
                } else{
                    // TODO : JWT 토큰 저장 과정 필요
                    startActivity(Intent(this, HomeActivity::class.java))
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
            if (checkEmptyField()) {
                viewModel.login()
            }
        }

    }

    // 비어있는 EditText 모두 오류 처리, 패스워드 확인란 일치 여부 확인
    private fun checkEmptyField(): Boolean {
        var isNotEmpty = true
        val fieldList = listOf(
            binding.idEditText,
            binding.passwordEditText,
        )

        // 비어있는 필드가 있는지 확인
        fieldList.forEach {
            if (it.text.isNullOrBlank()) {
                it.error = "필수 입력 항목입니다"
                isNotEmpty = false
            }
        }

        return isNotEmpty
    }
}