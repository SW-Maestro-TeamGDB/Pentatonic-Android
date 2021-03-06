package com.team_gdb.pentatonic.ui.create_record

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivityCreateRecordBinding
import com.team_gdb.pentatonic.ui.create_cover.basic_info.BasicRecordInfoFormFragment
import com.team_gdb.pentatonic.ui.record.RecordActivity
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SONG_ENTITY
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 커버를 생성하는 페이지
 * - 사용자에 의해 입력되는 것
 *   1. 기본 정보 (BasicInfo) : 커버 이름, 소개글, 원곡
 *   2. 세션 구성 (SessionSetting) : 참여할 수 있는 세션 종류와 최대 인원
 */
class CreateRecordActivity : BaseActivity<ActivityCreateRecordBinding, CreateRecordViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_create_record
    override val viewModel: CreateRecordViewModel by viewModel()

    private val basicInfoFormFragment: Fragment = BasicRecordInfoFormFragment()
    private val coverSessionSettingFragment: Fragment = CoverSessionSettingFragment()
    private var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

    // 외부에서 곡을 지정한 경우 담기는 Extra Data (곡 정보)
    private val coverSong: SongEntity? by lazy {
        intent.getSerializableExtra(SONG_ENTITY) as SongEntity?
    }

    // 자유곡의 경우 본인의 자유곡인지, 타인의 자유곡 밴드 참여 형태인지 구분 필요
    private val isNotMyFreeSong: Boolean by lazy {
        intent.getBooleanExtra(IS_MY_FREE_SONG, true)
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.recordBasicInfoValidationEvent.observe(this) {
            // Basic Information Form Validation 성립하는 경우 프래그먼트 이동
            if (it.getContentIfNotHandled() == true) {
                transaction = supportFragmentManager.beginTransaction()
                transaction.apply {
                    addToBackStack(null)  // 뒤로가기 키 누르면 기본 정보 입력폼으로 올 수 있도록
                    setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
                    replace(R.id.fragmentContainer, coverSessionSettingFragment).commit()
                }
            }
        }

        viewModel.completeCreateCoverEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                val intent = Intent(this, RecordActivity::class.java).apply {
                    putExtra(CREATED_COVER_ENTITY, viewModel.createdCoverEntity)
                    putExtra(IS_MY_FREE_SONG, isNotMyFreeSong)
                }
                finish()
                startActivity(intent)
            }
        }
    }

    override fun initAfterBinding() {
        binding.titleBar.titleTextView.text = "녹음하기"
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }
        transaction.apply {  // 초기 프래그먼트는 기본 정보 입력폼으로 설정
            replace(R.id.fragmentContainer, basicInfoFormFragment)
            commit()
        }

        if (coverSong != null){  // 만약 외부에서 곡을 지정해서 들어온 것이라면
            viewModel.recordOriginalSong.value = coverSong
        }
    }

    companion object {
        const val CREATED_COVER_ENTITY = "CREATED_COVER_ENTITY"
        const val IS_MY_FREE_SONG = "IS_MY_FREE_SONG"
    }
}