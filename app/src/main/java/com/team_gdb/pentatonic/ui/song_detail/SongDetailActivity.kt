package com.team_gdb.pentatonic.ui.song_detail

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.team_gdb.pentatonic.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.team_gdb.pentatonic.base.BaseActivity
import com.team_gdb.pentatonic.data.model.SongEntity
import com.team_gdb.pentatonic.databinding.ActivitySongDetailBinding
import com.team_gdb.pentatonic.ui.studio.StudioFragment.Companion.SONG_ENTITY
import jp.wasabeef.blurry.Blurry
import timber.log.Timber

class SongDetailActivity : BaseActivity<ActivitySongDetailBinding, SongDetailViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_song_detail
    override val viewModel: SongDetailViewModel by viewModel()

    override fun initStartView() {
        binding.viewModel = this.viewModel

        val songEntity: SongEntity = intent.getSerializableExtra(SONG_ENTITY) as SongEntity
        viewModel.songEntity.postValue(songEntity)

        Blurry.with(this)
            .radius(8)
            .sampling(8)
            .color(Color.argb(66, 255, 255, 255))
            .animate(500)
            .from(BitmapFactory.decodeResource(resources, R.drawable.beautiful_album_jacket))
            .into(binding.albumJacketImageBackground)
    }

    override fun initDataBinding() {
        viewModel.songEntity.observe(this) {
            Timber.d(it.toString())
        }
    }

    override fun initAfterBinding() {
    }
}