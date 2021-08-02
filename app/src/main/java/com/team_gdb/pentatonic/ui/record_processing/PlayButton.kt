package com.team_gdb.pentatonic.ui.record_processing

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.team_gdb.pentatonic.R
import com.team_gdb.pentatonic.ui.record.ButtonState

class PlayButton(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {

    init {
        setBackgroundResource(R.color.transparent)
    }

    /**
     * 전달받은 버튼 상태에 따라 이미지 리소스 변경
     * @param state : 현재 버튼 상태
     */
    fun updateIconWithState(state: ButtonState) {
        when (state) {
            ButtonState.BEFORE_PLAYING -> {
                setImageResource(R.drawable.ic_play)
            }
            ButtonState.ON_PLAYING -> {
                setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            }
        }
    }
}