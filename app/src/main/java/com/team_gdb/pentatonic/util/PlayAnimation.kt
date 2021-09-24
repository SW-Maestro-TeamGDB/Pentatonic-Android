package com.team_gdb.pentatonic.util

import android.app.Activity
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.tapadoo.alerter.Alerter
import com.team_gdb.pentatonic.R

object PlayAnimation {
    fun playErrorAnimationOnEditText(view: View){
        YoYo.with(Techniques.Shake)
            .duration(700)
            .playOn(view)
    }

    fun playSuccessAlert(activity: Activity, text: String) {
        Alerter.create(activity)
            .setText(text)
            .setIcon(R.drawable.ic_baseline_check_circle_24)
            .setBackgroundColorRes(R.color.main_regular)
            .setIconColorFilter(0)
            .setIconSize(R.dimen.custom_icon_size)
            .show()
    }

    fun playFailureAlert(activity: Activity, text: String) {
        Alerter.create(activity)
            .setText(text)
            .setIcon(R.drawable.ic_baseline_error_outline_24)
            .setBackgroundColorRes(R.color.error_red)
            .setIconColorFilter(0)
            .setIconSize(R.dimen.custom_icon_size)
            .show()
    }
}