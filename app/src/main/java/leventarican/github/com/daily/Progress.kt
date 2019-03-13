package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View

class Progress : View {

    private var width = 0.0F
    private var height = 0.0F
    private lateinit var paint: Paint
    private var progress = 0

    constructor(context: Context?): super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = resources.getColor(R.color.LightGreenA700, context.theme)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d("#code#", "size changed: w=$w, h=$h, oldw=$oldw, oldh=$oldh")
        width = w.toFloat()
        height = h.toFloat()
        progress = width.toInt() - 10
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        Log.d("#code#", "on draw: ${width.toInt() / 2} - ${height.toInt() / 2 - 10}")

        canvas?.drawRect(Rect().apply {
            left = 10
            top = 10
            right = progress
            bottom = height.toInt() - 10
        }, paint)
    }

    fun update(progress: Int) {
        this.progress = progress * 10 % (width.toInt() - 10) // TODO: this is just sample data
        Log.d("#code#", "progress: $progress")
        invalidate()
    }
}