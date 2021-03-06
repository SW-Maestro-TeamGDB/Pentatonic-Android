package com.team_gdb.pentatonic.ui.create_cover

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivityCreateCoverBinding
import com.team_gdb.pentatonic.ui.create_cover.basic_info.BasicCoverInfoFormFragment
import com.team_gdb.pentatonic.ui.create_cover.session_setting.BandCoverSessionSettingFragment
import com.team_gdb.pentatonic.ui.create_cover.session_setting.SoloCoverSessionSettingFragment
import com.team_gdb.pentatonic.ui.select_library.SelectLibraryActivity
import com.team_gdb.pentatonic.ui.studio.StudioFragment
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.BAND_COVER
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.COVER_MODE
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SOLO_COVER
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 커버를 생성하는 페이지
 * - 사용자에 의해 입력되는 것
 *   1. 기본 정보 (BasicInfo) : 커버 이름, 소개글, 원곡
 *   2. 세션 구성 (SessionSetting) : 참여할 수 있는 세션 종류와 최대 인원
 */
class CreateCoverActivity : BaseActivity<ActivityCreateCoverBinding, CreateCoverViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_create_cover
    override val viewModel: CreateCoverViewModel by viewModel()

    private val basicInfoFormFragment: Fragment = BasicCoverInfoFormFragment()
    private val bandCoverSessionSettingFragment: Fragment = BandCoverSessionSettingFragment()
    private val soloCoverSessionSettingFragment: Fragment = SoloCoverSessionSettingFragment()
    private var transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

    // 밴드 커버 / 솔로 커버 구분을 위한 모드 변수
    private val coverMode: String by lazy {
        intent.getStringExtra(COVER_MODE) as String
    }
    
    // 외부에서 곡을 지정한 경우 담기는 Extra Data (곡 정보)
    private val coverSong: SongEntity? by lazy {
        intent.getSerializableExtra(StudioFragment.SONG_ENTITY) as SongEntity?
    }

    override fun initStartView() {
        binding.viewModel = this.viewModel
    }

    override fun initDataBinding() {
        viewModel.coverBasicInfoValidationEvent.observe(this) {
            // Basic Information Form Validation 성립하는 경우 프래그먼트 이동
            if (it.getContentIfNotHandled() == true) {
                transaction = supportFragmentManager.beginTransaction()
                transaction.apply {
                    addToBackStack(null)  // 뒤로가기 키 누르면 기본 정보 입력폼으로 올 수 있도록
                    setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
                    when (coverMode) {
                        BAND_COVER -> replace(
                            R.id.fragmentContainer,
                            bandCoverSessionSettingFragment
                        ).commit()
                        SOLO_COVER -> replace(
                            R.id.fragmentContainer,
                            soloCoverSessionSettingFragment
                        ).commit()
                    }
                }
            }
        }

        viewModel.completeCreateCoverEvent.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                // 라이브러리 선택 페이지로 이동
                val intent = Intent(this, SelectLibraryActivity::class.java).apply {
                    putExtra(CREATED_COVER_ENTITY, viewModel.createdCoverEntity)
                }
                startActivity(intent)  // 커버 기본 정보 채워진 객체 전달
            }
        }
    }

    override fun initAfterBinding() {
        when (coverMode) {
            SOLO_COVER -> binding.titleBar.titleTextView.text = "솔로 커버 만들기"
            BAND_COVER -> binding.titleBar.titleTextView.text = "밴드 커버 만들기"
        }
        binding.titleBar.backButton.setOnClickListener {
            finish()
        }
        transaction.apply {  // 초기 프래그먼트는 기본 정보 입력폼으로 설정
            replace(R.id.fragmentContainer, basicInfoFormFragment)
            commit()
        }

        if (coverSong != null){  // 만약 외부에서 곡을 지정해서 들어온 것이라면
            viewModel.coverSong.value = coverSong
        }
    }

    companion object {
        const val CREATED_COVER_ENTITY = "CREATED_COVER_ENTITY"
    }
}