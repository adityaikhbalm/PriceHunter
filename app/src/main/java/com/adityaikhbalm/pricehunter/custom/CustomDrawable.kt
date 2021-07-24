package com.adityaikhbalm.pricehunter.custom

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path.FillType
import android.graphics.drawable.Drawable
import androidx.annotation.NonNull

class CustomDrawable(
    private val strokeColor: IntArray,
    private val strokeWidth: Float,
    private val radius: Float
) : Drawable() {

    private val strokePaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val strokePaint2: Paint = Paint(ANTI_ALIAS_FLAG)
    private val fillPaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val strokeOuterRect = RectF()
    private val fillRect = RectF()
    private val path = Path()

    init {
        strokePaint.style = Paint.Style.FILL
        strokePaint.maskFilter = BlurMaskFilter(strokeWidth, BlurMaskFilter.Blur.NORMAL)
        strokePaint2.style = Paint.Style.FILL
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = Color.TRANSPARENT
        path.fillType = FillType.EVEN_ODD
    }

    fun changeColor(backgroundColor: Int) {
        fillPaint.color = backgroundColor
        fillPaint.alpha = 50
        invalidateSelf()
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

        path.reset()

        strokeOuterRect.set(
            bounds.left + strokeWidth * 2f,
            bounds.top + strokeWidth * 2f,
            bounds.right - strokeWidth * 2f,
            bounds.bottom - strokeWidth * 2f
        )
        fillRect.set(
            bounds.left + strokeWidth * 1.5f,
            bounds.top + strokeWidth * 1.5f,
            bounds.right - strokeWidth * 1.5f,
            bounds.bottom - strokeWidth * 1.5f
        )

        path.addRoundRect(
            strokeOuterRect,
            radius,
            radius,
            Path.Direction.CW
        )

        path.addRoundRect(
            fillRect,
            radius,
            radius,
            Path.Direction.CW
        )
    }

    override fun draw(@NonNull canvas: Canvas) {
        val x0 = strokeOuterRect.left
        val y0 = strokeOuterRect.centerY()
        val x1 = strokeOuterRect.right
        val y1 = strokeOuterRect.centerY()

        strokePaint.shader = LinearGradient(
            x0,
            y0,
            x1,
            y1,
            strokeColor,
            null,
            Shader.TileMode.CLAMP
        )

        strokePaint2.shader = LinearGradient(
            x0,
            y0,
            x1,
            y1,
            strokeColor,
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawPath(path, strokePaint)
        canvas.drawPath(path, strokePaint2)
        canvas.drawRoundRect(
            fillRect,
            radius,
            radius,
            fillPaint
        )
    }

    override fun setAlpha(alpha: Int) {
        strokePaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}