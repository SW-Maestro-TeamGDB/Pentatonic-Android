package com.team_gdb.pentatonic.util

import android.content.Context
import android.content.res.Resources

object SystemUtil {
    /**
     * 상태바 높이를 가져온다.
     * @param context UI Context
     * @return 상태바 높이
     */
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getBottomNavigationBarHeight(context: Context) : Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}