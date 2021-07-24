package com.adityaikhbalm.pricehunter.custom

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path.FillType
import android.graphics.drawable.Drawable
import androidx.annotation.NonNull

class DrawableAnimation(
    private val strokeColor: IntArray,
    private val strokeWidth: Float
) : Drawable(), ValueAnimator.AnimatorUpdateListener {

    private val strokePaint: Paint = Paint(ANTI_ALIAS_FLAG)
    private val strokePaint2: Paint = Paint(ANTI_ALIAS_FLAG)
    private val strokeOuterRect = RectF()
    private val path = Path()
    private var oke = 0f

    private var mAnimator: ValueAnimator? = null

    init {
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = 10f
        strokePaint.maskFilter = BlurMaskFilter(strokeWidth, BlurMaskFilter.Blur.NORMAL)
        strokePaint2.style = Paint.Style.STROKE
        strokePaint2.strokeWidth = 10f
        path.fillType = FillType.EVEN_ODD
    }

    fun startAnimating() {
        val b = bounds
        mAnimator = ValueAnimator.ofInt(-b.bottom, b.bottom)
        mAnimator?.duration = 2000
        mAnimator?.addUpdateListener(this)
        mAnimator?.start()
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

    }

    override fun setAlpha(alpha: Int) {
        strokePaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        path.reset()

        path.addArc(
            bounds.left + strokeWidth * 2f,
            bounds.top + strokeWidth * 2f,
            bounds.right - strokeWidth * 2f,
            bounds.bottom - strokeWidth * 2f,
            270f,
            oke
        )
        oke += 3.25f

        strokeOuterRect.set(
            bounds.left + strokeWidth * 2f,
            bounds.top + strokeWidth * 2f,
            bounds.right - strokeWidth * 2f,
            bounds.bottom - strokeWidth * 2f
        )

        invalidateSelf()
    }
}