package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.MotionEvent



class Pomodoro : View {

    private var viewWidth: Int = 0
    private var value = 3  // 1..5
    private lateinit var rulerPaint: Paint

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

        var down = 0
        var motion = 0
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    down = event.x.toInt()
                }
                MotionEvent.ACTION_UP -> {
                    motion = event.x.toInt() - down
                    if (motion < 300) {
                        if ((value+1) <= 5) {
                            value++
                        }
                    } else if (motion > 300) {
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
        0 mins  > | | | | |
        25 min  >         | | | | |
        10 min  >     | | | | |
         */
        val steps = (width-100/*some padding*/) / 5
        // TODO: refactor
        when (value) {
            1 -> {
                for (i in 3..5) {
                    canvas?.drawLine((steps * i).toFloat(), 10F, (steps * i).toFloat(), 100F, rulerPaint)
                }
            }
            2 -> {
                for (i in 2..5) {
                    canvas?.drawLine((steps * i).toFloat(), 10F, (steps * i).toFloat(), 100F, rulerPaint)
                }
            }
            3 -> {
                for (i in 1..5) {
                    canvas?.drawLine((steps * i).toFloat(), 10F, (steps * i).toFloat(), 100F, rulerPaint)
                }
            }
            4 -> {
                for (i in 1..4) {
                    canvas?.drawLine((steps * i).toFloat(), 10F, (steps * i).toFloat(), 100F, rulerPaint)
                }
            }
            5 -> {
                for (i in 1..3) {
                    canvas?.drawLine((steps * i).toFloat(), 10F, (steps * i).toFloat(), 100F, rulerPaint)
                }
            }
        }
    }

}