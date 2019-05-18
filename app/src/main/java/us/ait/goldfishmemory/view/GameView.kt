package us.ait.goldfishmemory.view

import android.content.Context
import android.graphics.*
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import us.ait.goldfishmemory.GameActivity
import us.ait.goldfishmemory.R
import us.ait.goldfishmemory.model.GameModel

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val DIM = 4
    private var gameWon = false
    private var winMsg = false

    private val paintBackground = Paint()
    private val oceanBackground = Paint()
    private val oceanLine = Paint()
    private val paintText = Paint()
    private val cardArr = arrayOf(
        BitmapFactory.decodeResource(resources, R.drawable.c1),
        BitmapFactory.decodeResource(resources, R.drawable.c2),
        BitmapFactory.decodeResource(resources, R.drawable.c3),
        BitmapFactory.decodeResource(resources, R.drawable.c4),
        BitmapFactory.decodeResource(resources, R.drawable.c5),
        BitmapFactory.decodeResource(resources, R.drawable.c6),
        BitmapFactory.decodeResource(resources, R.drawable.c7),
        BitmapFactory.decodeResource(resources, R.drawable.c8)
    )

    init {
        paintBackground.color = Color.WHITE
        paintBackground.style = Paint.Style.FILL
        oceanBackground.color = Color.parseColor("#c0dddd")
        oceanBackground.style = Paint.Style.FILL

        oceanLine.color = Color.parseColor("#c0dddd")
        oceanLine.style = Paint.Style.STROKE
        oceanLine.strokeWidth = 20f

        paintText.color = Color.BLACK
        paintText.textSize = 80f

        resetGame()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (i in 0 until DIM*DIM/2) {
            cardArr[i] = Bitmap.createScaledBitmap(
                cardArr[i], width/DIM, width/DIM, false
            )
        }
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
                    canvas?.drawBitmap(cardArr[card.type],
                        i*width/DIM.toFloat(),
                        j*height/DIM.toFloat()+20f,
                        null)
                }
                if (card.gone) {
                    canvas?.drawRect(i*width/DIM.toFloat(), j*height/DIM.toFloat(),
                        (i+1)*width/DIM.toFloat(), (j+1)*height/DIM.toFloat(), oceanBackground)
                }
            }
        }
        invalidate()
    }

    private fun drawGameBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), oceanLine)
        for (i in 1 until DIM) {
            canvas?.drawLine(0f, (i * height / DIM).toFloat(),
                width.toFloat(), (i * height / DIM).toFloat(), oceanLine)
            canvas?.drawLine((i * width / DIM).toFloat(), 0f,
                (i * width / DIM).toFloat(), height.toFloat(), oceanLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && !GameModel.waiting) {
            val tX = event.x.toInt() / (width / DIM)
            val tY = event.y.toInt() / (height / DIM)
            if (tX < DIM && tY < DIM && !GameModel.cardIsGone(tX, tY) && !gameWon) {
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