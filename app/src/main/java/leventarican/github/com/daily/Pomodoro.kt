package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class Pomodoro : View {

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
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.d("#code#", "measuredHeight: $measuredHeight; measuredWidth: $measuredWidth; height: $height; width: $width")

        for (i in 0..25 step 5) {
            canvas?.drawLine(10F * i, 10F, 10F * i, 100F, rulerPaint)
        }
    }

}