package com.team_gdb.pentatonic.ui.record

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.team_gdb.pentatonic.R

class RecordButton(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {

//    init {
//        setBackgroundResource(R.drawable.shape_oval_button)
//    }

    fun updateIconWithState(state: ButtonState) {
        when (state) {
            ButtonState.BEFORE_RECORDING -> {
                setImageResource(R.drawable.ic_record)
            }
            ButtonState.ON_RECORDING -> {
                setImageResource(R.drawable.ic_stop)
            }
            ButtonState.AFTER_RECORDING -> {
                setImageResource(R.drawable.ic_play)
            }
            ButtonState.ON_PLAYING -> {
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}