package com.atria.software.flashcardquiz.ui.blockbuster.customviews


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.atria.software.flashcardquiz.R

/**
 * Created by SamuelGjk on 2019/6/13.
 */
class RoundCornerProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val DEFAULT_STROKE_WIDTH: Float = 3F
        private const val DEFAULT_STROKE_COLOR: Int = 0xFFDAE1FE.toInt()
        private const val DEFAULT_PROGRESS_COLOR: Int = 0xFF6087DB.toInt()
    }

    private val strokePaint: Paint = Paint()
    private val progressPaint: Paint = Paint()
    private val progressPath: Path = Path()

    private var strokeWidth: Float = 0F
    private var strokeColor: Int = 0
    private var progressColor: Int = 0

    private var progress: Float = 0F

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgressBar)

        strokeWidth = a.getDimension(
            R.styleable.RoundCornerProgressBar_rcpb_stroke_width,
            Utils.dp2px(context, DEFAULT_STROKE_WIDTH)
        )
        strokeColor = a.getColor(R.styleable.RoundCornerProgressBar_rcpb_stroke_color, DEFAULT_STROKE_COLOR)
        progressColor = a.getColor(R.styleable.RoundCornerProgressBar_rcpb_progress_color, DEFAULT_PROGRESS_COLOR)
        progress = a.getFloat(R.styleable.RoundCornerProgressBar_rcpb_progress, 0F)
        if (progress < 0F) {
            progress = 0F
        } else if (progress > 1F) {
            progress = 1F
        }

        a.recycle()

        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = strokeWidth
        strokePaint.color = strokeColor
        strokePaint.isAntiAlias = true

        progressPaint.style = Paint.Style.FILL
        progressPaint.color = progressColor
        progressPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius: Float = height / 2F

        drawStroke(canvas, radius)

        drawProgress(canvas, radius)
    }

    private fun drawStroke(canvas: Canvas, radius: Float) {
        val left: Float = paddingStart + strokeWidth / 2
        val top: Float = strokeWidth / 2
        val right: Float = width - paddingEnd - strokeWidth / 2
        val bottom: Float = height - strokeWidth / 2

        canvas.drawRoundRect(left, top, right, bottom, radius, radius, strokePaint)
    }

    private fun drawProgress(canvas: Canvas, radius: Float) {
        canvas.save()

        val left: Float = strokeWidth + paddingStart
        val top: Float = strokeWidth
        val right: Float = (width - paddingStart - paddingEnd - strokeWidth * 2) * progress + left
        val bottom: Float = height - strokeWidth

        progressPath.reset()
        progressPath.addRect(
            left, top, right, bottom, Path.Direction.CW
        )
        canvas.clipPath(progressPath)

        canvas.drawRoundRect(
            left, top, if (right < radius * 2 + left) radius * 2 + left else right, bottom, radius, radius,
            progressPaint
        )

        canvas.restore()
    }

    fun getProgress(): Float = progress

    fun setProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setSmoothProgress(@FloatRange(from = 0.0, to = 1.0) progress: Float, duration: Long = 1000) {
        val valueAnimator = ValueAnimator
            .ofFloat(this.progress, progress)
            .setDuration(duration)
        valueAnimator.addUpdateListener { animation ->
            setProgress(animation.animatedValue as Float)
        }
        valueAnimator.start()
    }
}