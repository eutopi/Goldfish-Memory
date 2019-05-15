package us.ait.goldfishmemory.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import us.ait.goldfishmemory.GameActivity
import us.ait.goldfishmemory.model.GameModel

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val DIM = 4
    private var gameWon = false
    private var winMsg = false

    private val paintBackground = Paint()
    private val redBackground = Paint()
    private val whiteLine = Paint()
    private val paintText = Paint()

    init {
        paintBackground.color = Color.LTGRAY
        paintBackground.style = Paint.Style.FILL
        redBackground.color = Color.RED
        redBackground.style = Paint.Style.FILL

        whiteLine.color = Color.WHITE
        whiteLine.style = Paint.Style.STROKE
        whiteLine.strokeWidth = 20f

        paintText.color = Color.BLACK
        paintText.textSize = 80f

        resetGame()
    }

    override fun onDraw (canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawPlays(canvas)
        drawGameBoard(canvas)
    }

    private fun drawPlays(canvas: Canvas?) {
        checkGameStatus()
        for (i in 0 until DIM) {
            for (j in 0 until DIM) {
                val card = GameModel.getCard(i, j)
                if (card.clicked && !card.gone) {
                    canvas?.drawText(card.type.toString(), (i * width / DIM.toFloat())+75f,
                        (j * height / DIM.toFloat())+125f, paintText)
                }
                if (card.gone) {
                    canvas?.drawRect(i*width/DIM.toFloat(), j*height/DIM.toFloat(),
                        (i+1)*width/DIM.toFloat(), (j+1)*height/DIM.toFloat(), redBackground)
                }
            }
        }
        invalidate()
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
        if (event?.action == MotionEvent.ACTION_DOWN && !GameModel.waiting) {
            val tX = event.x.toInt() / (width / DIM)
            val tY = event.y.toInt() / (height / DIM)
            if (tX < DIM && tY < DIM && !gameWon) {
                GameModel.click(tX, tY)
                invalidate()
            }
        }
        return true
    }

    public fun checkGameStatus() {
        if (GameModel.won && !winMsg) {
            val msg = "You finished!"
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
            gameWon = true
            winMsg = true
        }
    }

    public fun resetGame() {
        GameModel.resetModel()
        invalidate()
        gameWon = false
        winMsg = false
    }
}