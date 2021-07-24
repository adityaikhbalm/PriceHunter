package com.adityaikhbalm.pricehunter.custom

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path.FillType
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.NonNull

class ShadowDrawable(
    private var strokeColor: IntArray,
    private var strokeWidth: Float,
    private var radius: Float,
    private var strokeGradientDirection: Direction = Direction.LEFT_RIGHT
) : Drawable() {

    enum class Direction {
        LEFT_RIGHT,
        TOP_BOTTOM,
        RIGHT_LEFT,
        BOTTOM_TOP,
        TL_BR,
        TR_BL,
        BR_TL,
        BL_TR
    }

    private val strokePaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val fillPaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val strokeOuterRect = RectF()
    private val fillRect = RectF()
    private val path = Path()

    init {
        strokePaint.style = Paint.Style.FILL
        strokePaint.maskFilter = BlurMaskFilter(strokeWidth, BlurMaskFilter.Blur.NORMAL)
        path.fillType = FillType.EVEN_ODD
        fillPaint.style = Paint.Style.FILL
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

        path.reset()

        strokeOuterRect.set(
            bounds.left + strokeWidth,
            bounds.top + strokeWidth,
            bounds.right - strokeWidth,
            bounds.bottom - strokeWidth
        )
        fillRect.set(
            bounds.left + strokeWidth * 2,
            bounds.top + strokeWidth * 2,
            bounds.right - strokeWidth * 2,
            bounds.bottom - strokeWidth * 2
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
        val x0: Float
        val y0: Float
        val x1: Float
        val y1: Float

        when (strokeGradientDirection) {
            Direction.LEFT_RIGHT -> {
                x0 = strokeOuterRect.left
                y0 = strokeOuterRect.centerY()
                x1 = strokeOuterRect.right
                y1 = strokeOuterRect.centerY()
            }
            Direction.TOP_BOTTOM -> {
                x0 = strokeOuterRect.centerX()
                y0 = strokeOuterRect.top
                x1 = strokeOuterRect.centerX()
                y1 = strokeOuterRect.bottom
            }
            Direction.RIGHT_LEFT -> {
                x0 = strokeOuterRect.right
                y0 = strokeOuterRect.centerY()
                x1 = strokeOuterRect.left
                y1 = strokeOuterRect.centerY()
            }
            Direction.BOTTOM_TOP -> {
                x0 = strokeOuterRect.centerX()
                y0 = strokeOuterRect.bottom
                x1 = strokeOuterRect.centerX()
                y1 = strokeOuterRect.top
            }
            Direction.TL_BR -> {
                x0 = strokeOuterRect.left
                y0 = strokeOuterRect.top
                x1 = strokeOuterRect.right
                y1 = strokeOuterRect.bottom
            }
            Direction.TR_BL -> {
                x0 = strokeOuterRect.right
                y0 = strokeOuterRect.top
                x1 = strokeOuterRect.left
                y1 = strokeOuterRect.bottom
            }
            Direction.BR_TL -> {
                x0 = strokeOuterRect.right
                y0 = strokeOuterRect.bottom
                x1 = strokeOuterRect.left
                y1 = strokeOuterRect.top
            }
            Direction.BL_TR -> {
                x0 = strokeOuterRect.left
                y0 = strokeOuterRect.bottom
                x1 = strokeOuterRect.right
                y1 = strokeOuterRect.top
            }
        }

        strokePaint.shader = LinearGradient(
            x0,
            y0,
            x1,
            y1,
            strokeColor,
            null,
            Shader.TileMode.CLAMP
        )

        fillPaint.shader = LinearGradient(
            x0,
            y0,
            x1,
            y1,
            strokeColor,
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawPath(path, strokePaint)
        canvas.drawPath(path, fillPaint)
    }

    override fun setAlpha(alpha: Int) {
        strokePaint.alpha = alpha
        fillPaint.alpha = alpha

        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}