package com.team_gdb.pentatonic.ui.create_cover

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.team_gdb.pentatonic.data.model.SongEntity

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