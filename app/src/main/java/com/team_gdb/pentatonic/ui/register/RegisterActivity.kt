package com.team_gdb.pentatonic.ui.register

import android.graphics.Color
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.checked
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        binding.confirmButton.setOnClickListener {
            confirmRegisterForm()
        }
    }

    private fun confirmRegisterForm() {
        if (isValidForm()) {
            when (viewModel.isValidForm()) {
                RegisterFormError.ID_INVALID -> {
                }
                RegisterFormError.NICKNAME_INVALID -> {
                }
                RegisterFormError.VALID -> {
                }
            }
        } else {
            Snackbar.make(binding.root, "입력 정보를 다시 확인해주세요!", Snackbar.LENGTH_LONG).show()
        }
    }

    // 비어있는 EditText 모두 오류 처리, 패스워드 확인란 일치 여부 확인
    private fun isValidForm(): Boolean {
        var valid = true
        val fieldList = listOf(
            binding.idEditText,
            binding.passwordEditText,
            binding.passwordConfirmEditText,
            binding.nicknameEditText
        )

        // 비어있는 필드가 있는지 확인
        fieldList.forEach {
            if (it.text.isNullOrBlank()) {
                it.error = "필수 입력 항목입니다"
                valid = false
            }
        }

        // 비밀번호, 비밀번호 확인 필드가 일치한지 확인
        if (fieldList[1].text.toString() != fieldList[2].text.toString()) {
            fieldList[1].error = "비밀번호와 확인란이 일치하지 않습니다"
            fieldList[2].error = "비밀번호와 확인란이 일치하지 않습니다"
            valid = false
        }

//        if (viewModel.userTypeField.value == null) {
//            binding.userTypeText.text = "사용자 유형을 선택해주세요"
//            binding.userTypeText.setTextColor(getColor(R.color.red))
//            valid = false
//        }

        return valid
    }
}