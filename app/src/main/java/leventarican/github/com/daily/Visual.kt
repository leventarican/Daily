package leventarican.github.com.daily

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View

class Visual : View {

    private var isActive = false
    private var paint: Paint? = null
    var progress: Int = 0
        set(value) {
            Log.d("#######", "set.progress $progress - value: $value")
            field = value
            invalidate()
        }

    constructor(context: Context?): super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = Color.GRAY
            this.style = Paint.Style.FILL
        }

        setOnClickListener {
            if(isActive) {
                paint?.color = Color.GRAY
                isActive = false
            }
            else {
                paint?.color = Color.GREEN
                isActive = true
            }
            invalidate()
        }
    }

    fun update(p: Int) {
        Log.d("#######", "progress $progress")
        progress = p
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val cx: Float = 100F
        val cy: Float = 100F
        val radius: Float = 100F
//        canvas?.drawCircle(cx, cy, radius, paint)

        val r0 = Rect()
        r0.apply {
            left = 10
            top = 10
            right = 200
            bottom = 200
        }
        canvas?.drawRect(r0, paint!!)

        val r1 = Rect().apply {
            left = 20
            top = 20
            right = 190
            bottom = 190
        }
        canvas?.drawRect(r1, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        })

        Log.d("#######", "onDraw.progress $progress")
        val r2 = Rect().apply {
            left = 20
            top = 20 + (190-20) - (progress * ((190 - 20) / 24)) // 24h
            right = 190
            bottom = 190
        }
        canvas?.drawRect(r2, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
            style = Paint.Style.FILL_AND_STROKE
        })
    }
}