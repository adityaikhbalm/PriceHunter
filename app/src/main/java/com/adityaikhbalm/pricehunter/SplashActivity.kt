package com.adityaikhbalm.pricehunter

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnLayout
import com.adityaikhbalm.pricehunter.custom.DrawableAnimation
import com.adityaikhbalm.pricehunter.databinding.ActivitySplashBinding
import com.adityaikhbalm.pricehunter.utils.Utility.convertDpToPixel
import com.adityaikhbalm.pricehunter.utils.Utility.getMyColor

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var colors: IntArray
    private lateinit var image: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        colors = intArrayOf(
            getMyColor(R.color.f),
            getMyColor(R.color.g),
            getMyColor(R.color.h),
            getMyColor(R.color.i)
        )

        val s = DrawableAnimation(colors, 20f)
        binding.pit.background = s
        Handler(Looper.myLooper()!!).postDelayed({
            s.startAnimating()
        },300)

        binding.message.doOnLayout {
            val textShader = LinearGradient(
                0f,
                0f,
                it.width.toFloat(),
                it.height.toFloat(),
                colors,
                null,
                TileMode.CLAMP
            )

            binding.message.paint.shader = textShader
        }

        image = arrayOf(binding.tokopedia, binding.shopee, binding.bukalapak)

        for (blackWhite in image) {
            blackWhite.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f)})
        }

        binding.circleLayout.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.circleLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    Handler(Looper.myLooper()!!).postDelayed({
                        logoAnimation()
                    },1950)

                }
            })
    }

    private fun logoAnimation() {
        var t = 0f
        var a = 0f
        var b = 0f
        var c = 0f

        val x = floatArrayOf(
            (binding.circleLayout.width / 2 - 25.convertDpToPixel).toFloat(),
            (binding.circleLayout.width - 96.convertDpToPixel).toFloat(),
            45.convertDpToPixel.toFloat()
        )
        val y = floatArrayOf(
            45.convertDpToPixel.toFloat(),
            (binding.circleLayout.height / 2 - 24.convertDpToPixel).toFloat(),
            (binding.circleLayout.height / 2 - 25.convertDpToPixel).toFloat()
        )
        val path: Array<Path> = arrayOf(
            Path(),
            Path(),
            Path()
        )
        path[0].moveTo(x[0], y[0])
        path[1].moveTo(x[1], y[1])
        path[2].moveTo(x[2], y[2])

        val circleWidth = binding.circleLayout.width / 4 - 10.convertDpToPixel

        for (z in 1..circleWidth * 4) {
            if (z >= circleWidth) {
                if (z >= circleWidth * 2 && z < circleWidth * 3) {
                    --t
                    --b
                    c = t
                    path[0].lineTo(x[0] + t, y[0] + b)
                } else if (z >= circleWidth * 3) {
                    t++
                    c++
                    --b
                    path[0].lineTo(x[0] + c, y[0] + b)
                } else {
                    --t
                    a++
                    b = z.toFloat()
                    path[0].lineTo(x[0] + t, y[0] + a)
                }
            } else {
                path[0].lineTo(x[0] + z, y[0] + z)
                path[1].lineTo(x[1] - t, y[1] + z)
                path[2].lineTo(x[2] + z, y[2])
                t = z.toFloat()
                a = z.toFloat()
            }
        }
        var objectAnimator: ObjectAnimator
        for (i in 0..2) {
            objectAnimator =
                ObjectAnimator.ofFloat(image[i], View.X, View.Y, path[i])
            objectAnimator.setDuration(2000).start()

            if (i == 2) {
                objectAnimator.doOnEnd {
                    val gradientDrawable = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        colors
                    )
                    gradientDrawable.cornerRadius = 160.convertDpToPixel.toFloat()

                    binding.light.background = gradientDrawable
                    binding.light.visibility = View.VISIBLE
                    for (blackWhite in image) {
                        blackWhite.colorFilter = ColorMatrixColorFilter(
                            ColorMatrix().apply {
                                setSaturation(1f)
                            }
                        )
                    }
                    Handler(Looper.myLooper()!!).postDelayed({
                        startActivity(
                            Intent(this@SplashActivity, MainActivity::class.java)
                        )
                        finish()
                    },1000)
                }
            }
        }
    }
}