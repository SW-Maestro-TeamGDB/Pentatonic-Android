package com.team_gdb.pentatonic.ui.create_cover

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.databinding.ActivityCreateCoverBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCoverActivity : BaseActivity<ActivityCreateCoverBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_create_cover
    override val viewModel: CreateCoverViewModel by viewModel()

    private val basicInfoFormFragment: Fragment = BasicInfoFormFragment()
    private val sessionConfigGormFragment: Fragment = SessionConfigFromFragment()
    private val transaction: FragmentTransaction by lazy {
        supportFragmentManager.beginTransaction()
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel?.coverBasicInfoValidation?.observe(this) {
            // Basic Information Form Validation 성립하는 경우 프래그먼트 이동
            if (it.getContentIfNotHandled() == true) {
                transaction.apply {  // 초기 프래그먼트는 기본 정보 입력폼으로 설정
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