package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.main.view.*

class Progress : View {

    private lateinit var paint: Paint
    private lateinit var bar: Rect

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Progress)
        val progressColor = typedArray.getColor(R.styleable.Progress_progress_color, Color.GREEN)
        typedArray.recycle()

        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = progressColor
        }

        bar = Rect().apply {
            left = 10
            top = 10
            right = 0
            bottom = top + 20
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(bar, paint)
    }

    fun update(progress: Int) {
        progress.let {
            when {
                it < 1 -> bar.right= getWidth() / 3
                it < 2 -> bar.right = getWidth() / 2
                it > 3 -> bar.right = getWidth() / 1 - 10
            }
        }
        invalidate()
    }
}