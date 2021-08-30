package com.team_gdb.pentatonic.ui.song_detail

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        binding.lifecycleOwner = this

        val songEntity: SongEntity = intent.getSerializableExtra(SONG_ENTITY) as SongEntity
        viewModel.songEntity.postValue(songEntity)

        Glide.with(binding.root)
            .load(songEntity.albumJacketImage)
            .centerCrop()
            .placeholder(R.drawable.placeholder_cover_bg)
            .listener(glideLoadingListener)
            .into(binding.albumJacketImage)

    }

    override fun initDataBinding() {
        viewModel.songEntity.observe(this) {
            Timber.d(it.toString())
        }
    }

    override fun initAfterBinding() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Glide 가 앨범 자켓 이미지를 로드 완료 했을때, 해당 이미지 리소스를 통해
     * Blurry 로 이미지 블러 처리를 수행해야 올바른 결과가 표시됨
     */
    private val glideLoadingListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Blurry.with(applicationContext)
                .radius(5)
                .sampling(5)
                .async()
                .animate(500)
                .from(resource?.toBitmap())
                .into(binding.albumJacketBackgroundImage)
            return false
        }
    }
}