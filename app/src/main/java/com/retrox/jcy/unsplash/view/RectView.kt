package com.retrox.jcy.unsplash.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.retrox.jcy.unsplash.R

/**
 * Created by jichenyang on 06/07/2017.
 */
class RectView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : View(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    private val completePaint = Paint()
    private val unCompletePaint = Paint()
    private val dashPathEffect = DashPathEffect(floatArrayOf(6f, 6f, 6f, 6f), 0f)
    private val path = Path()
    private val complectedXPosition = mutableListOf<Float>()
    private val attentionIcon: Drawable
    private val completeIcon: Drawable
    private val defaultIcon: Drawable
    private var defaultStepIndicatorNum = 100
    private var completedLineHeight = 0.05f * defaultStepIndicatorNum
    private var circleRadius = 0.28f * defaultStepIndicatorNum
    private var linePadding = 0.8f * defaultStepIndicatorNum
    private var leftY = 0f
    private var rightY = 0f
    private var centerY = 0f
    var completingPosition = 3
    var stepNum = 6

    init {
        setBackgroundColor(Color.rgb(2, 200, 189))
        with(completePaint) {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.WHITE
            strokeWidth = 2.0f
        }
        with(unCompletePaint) {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.WHITE
            strokeWidth = 4.0f
            pathEffect = dashPathEffect
        }

        completeIcon = ContextCompat.getDrawable(getContext(), R.drawable.complted) //已经完成的icon
        attentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.attention) //正在进行的icon
        defaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.default_icon) //未完成的icon
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.drawRect(Rect(200, 200, 800, 220), completePaint)
//        canvas?.drawCircle(350f, 350f, 100f, completePaint)

//        path.moveTo(200f,600f)
//        path.lineTo(800f,600f)
//        canvas?.drawPath(path, unCompletePaint)
        for (i in 0 until (complectedXPosition.size - 1)) {
            val preComplectedPosition = complectedXPosition[i]
            val afterComplectedPosition = complectedXPosition[i + 1]

            if (i < completingPosition) {
                canvas?.drawRect(preComplectedPosition + circleRadius - 10, leftY, afterComplectedPosition - circleRadius + 10, rightY, completePaint)
            } else {
                path.moveTo(preComplectedPosition + circleRadius, centerY)
                path.lineTo(afterComplectedPosition - circleRadius, centerY)
                canvas?.drawPath(path, unCompletePaint)
            }
        }

        for (i in 0 until complectedXPosition.size) {
            val currentPosition = complectedXPosition[i]
            val rect = rectFloat(currentPosition - circleRadius, centerY - circleRadius, currentPosition + circleRadius, centerY + circleRadius)
            if (i < completingPosition) {
                completeIcon.bounds = rect
                completeIcon.draw(canvas)
            } else if (i == completingPosition && complectedXPosition.size != 1){
                completePaint.color = Color.WHITE
                canvas?.drawCircle(currentPosition,centerY,circleRadius * 1.1f,completePaint)
                attentionIcon.bounds = rect
                attentionIcon.draw(canvas)
            } else {
                defaultIcon.bounds = rect
                defaultIcon.draw(canvas)
            }

        }

    }

    fun rectFloat(left: Float, top: Float, right: Float, bottom: Float) = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = defaultStepIndicatorNum * 2
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec)
        }
        var height = defaultStepIndicatorNum
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = MeasureSpec.getSize(heightMeasureSpec)
        }
        setMeasuredDimension(width, height)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerY = 0.5f * height
        leftY = centerY - completedLineHeight / 2
        rightY = centerY + completedLineHeight / 2
        for (i in 0 until stepNum) {
            //先计算全部最左边的padding值（getWidth()-（圆形直径+两圆之间距离）*2）
            val paddingLeft = (width - stepNum * circleRadius * 2 - (stepNum - 1) * linePadding) / 2
            complectedXPosition.add(paddingLeft + circleRadius + i * circleRadius * 2 + i * linePadding)
        }
        //todo 加入对indicator的回调
    }
}




