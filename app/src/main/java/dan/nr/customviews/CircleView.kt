package dan.nr.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var radius = 50f
    private val TAG = "CircleView"
    var rect : Rect
    var paint:Paint
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
        }
        rect = Rect()
    }
    private val centre = PointF(150f, 150f)
    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
    public fun setViewColor(colorResource: Int){
        //paint.color=Color.GREEN
        paint.color=colorResource
    }
}