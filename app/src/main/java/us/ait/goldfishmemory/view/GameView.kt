package us.ait.goldfishmemory.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import us.ait.goldfishmemory.model.GameModel

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val DIM = 4

    private val paintBackground = Paint()
    private val whiteLine = Paint()
    private val paintText = Paint()

    init {
        paintBackground.color = Color.LTGRAY
        paintBackground.style = Paint.Style.FILL

        whiteLine.color = Color.WHITE
        whiteLine.style = Paint.Style.STROKE
        whiteLine.strokeWidth = 20f

        paintText.color = Color.BLACK
        paintText.textSize = 80f

        resetGame()
    }

    override fun onDraw (canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameBoard(canvas)
        drawPlays(canvas)
    }

    private fun drawPlays(canvas: Canvas?) {
        for (i in 0 until DIM) {
            for (j in 0 until DIM) {
                val card = GameModel.getCard(i, j)
                canvas?.drawText(card.type.toString(), (i * width / DIM.toFloat())+75f,
                    (j * height / DIM.toFloat())+125f, paintText)
            }
        }
    }

    private fun drawGameBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), whiteLine)
        for (i in 1 until DIM) {
            canvas?.drawLine(0f, (i * height / DIM).toFloat(),
                width.toFloat(), (i * height / DIM).toFloat(), whiteLine)
            canvas?.drawLine((i * width / DIM).toFloat(), 0f,
                (i * width / DIM).toFloat(), height.toFloat(), whiteLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / DIM)
            val tY = event.y.toInt() / (height / DIM)
            if (tX < DIM && tY < DIM ) {
                GameModel
                checkGameStatus()
                invalidate()
            }
        }
        return true
    }

    private fun checkGameStatus() {

    }

    public fun resetGame() {
        GameModel.resetModel()
        invalidate()
    }
}