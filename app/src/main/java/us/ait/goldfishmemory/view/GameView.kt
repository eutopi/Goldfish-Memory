package us.ait.goldfishmemory.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBackground = Paint()

    init {
        paintBackground.color = Color.LTGRAY
        paintBackground.style = Paint.Style.FILL
    }

    override fun onDraw (canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        //drawGameBoard(canvas)
        //drawPlays(canvas)
    }
}