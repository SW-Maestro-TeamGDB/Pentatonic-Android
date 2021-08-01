package com.team_gdb.pentatonic.ui.register

import android.content.Intent
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.RegisterForm
import com.team_gdb.pentatonic.databinding.ActivityRegisterBinding
import com.team_gdb.pentatonic.ui.user_verify.UserVerifyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()
    // RadioButton 각각의 아이디 값에 따른 회원 유형 고유값 매핑
    private val userTypeMap = mapOf(R.id.musician to 1, R.id.hobby to 2, R.id.onlyListen to 3)

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        // 아이디, 닉네임 Validation 체크가 수행되면 실행됨
        viewModel.checkCompleteEvent.observe(this) {
            Timber.d(it.toString())
            if (it.size == 2) {  // ID, 닉네임 체크가 모두 완료되었을 때 진입
                if (viewModel.isValidId.value == false) {
                    binding.idEditText.error = "아이디 형식이 올바르지 않습니다"
                }
                if (viewModel.isValidNickname.value == false) {
                    binding.nicknameEditText.error = "닉네임 형식이 올바르지 않습니다"
                }

                if (viewModel.isValidId.value == true && viewModel.isValidNickname.value == true) {
                    // RadioGroup 중 어떤 버튼을 선택했는지에 따라 회원 유형을 달리함
                    val userType = userTypeMap[viewModel.userTypeField.value]
                    val registerForm = RegisterForm(
                        id = binding.idEditText.text.toString(),
                        nickname = binding.nicknameEditText.text.toString(),
                        password = binding.passwordEditText.text.toString(),
                        userType = userType!!
                    )

                    // 회원가입 폼 정보를 Extra 로 담아 휴대전화 인증화면 이동 Intent 실행
                    val intent = Intent(this, UserVerifyActivity::class.java)
                    intent.putExtra(EXTRA_NAME, registerForm)
                    startActivity(intent)
                    finish()
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

    /**
     * 입력정보가 올바른지 확인
     * - 비어있는 필드나, 비밀번호와 비밀번호 확인란이 일치하는지 확인
     * @return : 올바른 폼이면 true, 그렇지 않으면 false
     */
    private fun isValidForm(): Boolean {
        var isNotEmpty = true
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
                isNotEmpty = false
            }
        }

        // 비밀번호, 비밀번호 확인 필드가 일치한지 확인
        if (fieldList[1].text.toString() != fieldList[2].text.toString()) {
            fieldList[1].error = "비밀번호와 확인란이 일치하지 않습니다"
            fieldList[2].error = "비밀번호와 확인란이 일치하지 않습니다"
            isNotEmpty = false
        }

        if (viewModel.userTypeField.value == null) {
            binding.userTypeText.text = "사용자 유형을 선택해주세요"
            binding.userTypeText.setTextColor(getColor(R.color.red))
            isNotEmpty = false
        }

        return isNotEmpty
    }

    companion object {
        val EXTRA_NAME = "REGISTER_FROM"
    }
}