package com.jspstudio.gamingtalent.util

import android.content.Context
import android.content.res.Resources

object Util {
    /**
     * dp값 or px값 가져오기
     */
    fun fromPxToDp(px: Float): Float { return (px / Resources.getSystem().displayMetrics.density) }
    fun fromDpToPx(dp: Float): Float { return (dp * Resources.getSystem().displayMetrics.density) }
    fun fromDpToPx(dp: Int): Float { return (dp * Resources.getSystem().displayMetrics.density) }

    /**
     * 실행중인 앱의 가로길이가 600이상이면 true
     * */
    fun isScreenWidthGreaterThan600dp(context: Context): Boolean {
        val configuration = context.resources.configuration
        val screenWidthDp = configuration.screenWidthDp
        return screenWidthDp >= 600
    }
}