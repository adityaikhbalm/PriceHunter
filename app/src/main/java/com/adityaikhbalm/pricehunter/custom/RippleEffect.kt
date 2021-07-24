package com.adityaikhbalm.pricehunter.custom

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import com.adityaikhbalm.pricehunter.utils.Utility.convertDpToPixel
import java.util.*

object RippleEffect {
    fun getAdaptiveRippleDrawable(
        normalColor: Int,
        pressedColor: Int,
        inset: Int = 5,
        radius : Int = 35
    ): Drawable {
        val rd = RippleDrawable(
            ColorStateList.valueOf(pressedColor),
            null,
            getRippleMask(normalColor, radius.convertDpToPixel.toFloat())
        )
        return InsetDrawable(rd, inset.convertDpToPixel)
    }

    private fun getRippleMask(color: Int, radius : Float): Drawable {
        val radii = FloatArray(8)
        Arrays.fill(radii, radius)

        val r = RoundRectShape(radii, null, null)
        val shapeDrawable = ShapeDrawable(r)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }

}