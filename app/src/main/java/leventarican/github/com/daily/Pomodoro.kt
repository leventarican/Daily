package leventarican.github.com.daily

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.MotionEvent
import kotlin.math.ceil
import android.R.attr.y
import android.R.attr.x
import android.graphics.*
import android.graphics.Path.FillType
import android.system.Os.close


class Pomodoro : View {

    private var viewWidth: Int = 0
    private var steps = 25/5    // max pomdoro time: 25min; number of setting steps: 5
    private var value = ceil(steps/2.0).toInt()

    private lateinit var rulerPaint: Paint
    private lateinit var txtPaint: Paint

    constructor(context: Context?): super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet): super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        rulerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = Color.WHITE
            this.strokeWidth = 5F
        }
        txtPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = Color.WHITE
            this.style = Paint.Style.FILL_AND_STROKE
            this.textAlign = Paint.Align.CENTER
            this.textSize = 40f
        }

        var down = 0
        var motion: Int
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    down = event.x.toInt()
                }
                MotionEvent.ACTION_UP -> {
                    motion = event.x.toInt() - down
                    if (motion < -200) {
                        if ((value+1) <= steps) {
                            value++
                        }
                    } else if (motion > 200) {
                        if ((value-1) > 0) {
                            value--
                        }
                    }
                    postInvalidate()
                    Log.d("#code#", "motion: $motion; value: $value")
                }
            }
            true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        Log.d("#code#", "width: $width; w: $w")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /*
        25 min  > | | | | |
        05 min  >         | | | | |
        15 min  >     | | | | |
         */
        val gab = (width-100/*some padding*/) / steps
        val centre = ceil(steps/2.0).toInt()
        // e.g. steps = 5; centre = 3
        when {
            value < centre -> {
                for (i in centre-centre%value..steps) {
                    val x = (gab * i).toFloat()
                    canvas?.drawLine(x, 10F, x, 100F, rulerPaint)
                    canvas?.drawText("${(i-(centre-value))*steps}", x, 150F, txtPaint)
                }
            }
            value > centre -> {
                for (i in 1..centre+steps%value) {
                    val x = (gab * i).toFloat()
                    canvas?.drawLine((gab * i).toFloat(), 10F, (gab * i).toFloat(), 100F, rulerPaint)
                    canvas?.drawText("${(i+(value-centre))*steps}", x, 150F, txtPaint)
                }
            }
            value == centre -> {
                for (i in 1..steps) {
                    val x = (gab * i).toFloat()
                    canvas?.drawLine((gab * i).toFloat(), 10F, (gab * i).toFloat(), 100F, rulerPaint)
                    canvas?.drawText("${i*steps}", x, 150F, txtPaint)
                }
            }
        }
    }

}