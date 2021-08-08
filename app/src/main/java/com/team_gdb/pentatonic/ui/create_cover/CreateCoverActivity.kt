package com.team_gdb.pentatonic.ui.create_cover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivityCreateCoverBinding
import com.team_gdb.pentatonic.ui.create_cover.basic_info.BasicInfoFormFragment
import com.team_gdb.pentatonic.ui.create_cover.session_setting.SessionSettingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCoverActivity : BaseActivity<ActivityCreateCoverBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_create_cover
    override val viewModel: CreateCoverViewModel by viewModel()

    private val basicInfoFormFragment: Fragment = BasicInfoFormFragment()
    private val sessionConfigGormFragment: Fragment = SessionSettingFragment()
    private var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()


    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.coverBasicInfoValidation.observe(this) {
            // Basic Information Form Validation 성립하는 경우 프래그먼트 이동
            if (it.getContentIfNotHandled() == true) {
                transaction = supportFragmentManager.beginTransaction()
                transaction.apply {
                    addToBackStack(null)  // 뒤로가기 키 누르면 기본 정보 입력폼으로 올 수 있도록
                    setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
                    replace(R.id.fragmentContainer, sessionConfigGormFragment)
                    commit()
                }
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "밴드 커버 만들기"
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }

        transaction.apply {  // 초기 프래그먼트는 기본 정보 입력폼으로 설정
            replace(R.id.fragmentContainer, basicInfoFormFragment)
            commit()
        }
    }
}