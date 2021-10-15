package com.team_gdb.pentatonic.ui.select_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.CreatedCoverEntity
import com.team_gdb.pentatonic.databinding.ActivitySelectLibraryBinding
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity
import com.team_gdb.pentatonic.ui.create_cover.CreateCoverActivity.Companion.CREATED_COVER_ENTITY
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectLibraryActivity : BaseActivity<ActivitySelectLibraryBinding, SelectLibraryViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_select_library
    override val viewModel: SelectLibraryViewModel by viewModel()

    // 이전 액티비티 (CreateCoverActivity) 에서 넘어온 정보
    private val createdCoverEntity: CreatedCoverEntity by lazy {
        intent.getSerializableExtra(CREATED_COVER_ENTITY) as CreatedCoverEntity
    }

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}