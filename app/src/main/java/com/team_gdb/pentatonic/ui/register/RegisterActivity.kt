package com.team_gdb.pentatonic.ui.register

import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import com.newidea.mcpestore.libs.base.BaseActivity
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.databinding.ActivityRegisterBinding
import com.team_gdb.pentatonic.ui.user_verify.UserVerifyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = viewModel
    }

    override fun initDataBinding() {
        viewModel.checkDone.observe(this){
            if (it[0] && it[1]){
                Timber.d("YEAH")
                if (viewModel.isValidId.value == false){
                    binding.idEditText.error = "아이디 형식이 올바르지 않습니다"
                }
                if (viewModel.isValidNickname.value == false){
                    binding.nicknameEditText.error = "닉네임 형식이 올바르지 않습니다"
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.confirmButton.setOnClickListener {
            confirmRegisterForm()
        }
    }

    private fun confirmRegisterForm() {
        if (isValidForm()) {
            viewModel.isValidForm()
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

        if (viewModel.userTypeField.value == null) {
            binding.userTypeText.text = "사용자 유형을 선택해주세요"
            binding.userTypeText.setTextColor(getColor(R.color.red))
            valid = false
        }

        return valid
    }
}