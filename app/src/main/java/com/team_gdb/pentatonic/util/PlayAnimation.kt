package com.team_gdb.pentatonic.util

import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

object PlayAnimation {
    fun playErrorAnimation(view: View){
        YoYo.with(Techniques.Shake)
            .duration(700)
            .playOn(view)
    }
}