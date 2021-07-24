package com.adityaikhbalm.pricehunter.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.adityaikhbalm.pricehunter.R
import com.adityaikhbalm.pricehunter.custom.CustomDrawable
import com.adityaikhbalm.pricehunter.custom.RippleEffect
import com.adityaikhbalm.pricehunter.utils.Utility.getMyColor

object Utility {
    private val metrics = Resources.getSystem().displayMetrics

    val Int.convertDpToPixel
        get() = (this * metrics.density).toInt()

    fun Context.getMyColor(color: Int) = ContextCompat.getColor(this, color)

    fun View.showIt() {
        this.isVisible = true
    }

    fun View.hideIt() {
        this.isVisible = false
    }

    fun View.backgroundNeon(
        strokeColor: IntArray,
        strokeWidth: Int,
        radius: Int
    ) : CustomDrawable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foreground = RippleEffect.getAdaptiveRippleDrawable(
                this.context.getMyColor(R.color.low_white),
                this.context.getMyColor(R.color.low_white),
                strokeWidth / 2,
                radius
            )
        }

        val c = CustomDrawable(
            strokeColor,
            strokeWidth.toFloat(),
            radius.convertDpToPixel.toFloat()
        )
        background = c
        return c
    }
}