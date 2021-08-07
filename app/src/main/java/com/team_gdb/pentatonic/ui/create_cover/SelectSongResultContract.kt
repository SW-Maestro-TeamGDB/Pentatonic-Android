package com.team_gdb.pentatonic.ui.create_cover

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.team_gdb.pentatonic.data.model.SongEntity

/**
 * SelectSongActivity -> BasicInfoFromFragment 로 선택한 곡 정보를 넘기기 위한 Contract
 * - SongEntity 를 결과 인자로 넘겨줌 (콜백에서 받아서 처리하면 됨)
 */
class SelectSongResultContract : ActivityResultContract<Intent, SongEntity>() {
    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SongEntity {
        return intent?.getSerializableExtra(SELECT_SONG) as SongEntity
    }

    companion object {
        const val SELECT_SONG = "SELECT_SONG"
    }
}