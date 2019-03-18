package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View

class Progress : View {

    private lateinit var paint: Paint
    private lateinit var txtPaint: Paint
    private lateinit var bar: Rect
    private var txtValue = ""
    private val padding = 10

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
            left = padding
            top = padding
        }

        txtPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = Color.BLACK
            this.style = Paint.Style.FILL_AND_STROKE
            this.textAlign = Paint.Align.CENTER
            this.textSize = 40f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        Log.d("#code#", "height=${height} pixel; density=${height / resources.displayMetrics.density} dp")

        bar.right = padding
        bar.bottom = height - bar.top
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(bar, paint)
        canvas?.drawText(txtValue, width.toFloat() - 100.toFloat(), (height / 1.5).toFloat(), txtPaint)
    }

    fun update(progress: Int, total: Int) {
        txtValue = "$progress"
        if (total * progress != 0) {
            var right = width / total * progress - padding
            if (right < padding) {
                right = padding
            } else if (right > width) {
                right = width - padding
            }
            bar.right = right
            postInvalidate()
        }
    }
}