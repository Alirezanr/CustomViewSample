package dan.nr.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * A Custom View Class that can be use as a ui element
 * */
class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //radius of the drawn circle
    private var radius = 50f
    //create an object from Paint class for setting color of circle
    var paint: Paint
    //initialize paint object with default color
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.RED
        }
    }

    /**
     * draws shape to the screen
     * */
    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
    }

    /**
     * changes shape color using a resource color id
     * */
    public fun setViewColor(colorResource: Int) {
        paint.color = colorResource
    }
}