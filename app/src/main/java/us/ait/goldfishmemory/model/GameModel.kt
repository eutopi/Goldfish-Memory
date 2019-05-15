package us.ait.goldfishmemory.model

import android.os.Handler
import java.util.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v4.os.HandlerCompat.postDelayed


data class Card(var type: Int, var clicked: Boolean, var gone: Boolean)

object GameModel {

    private val DIM = 4
    private var model = Array(DIM) { Array(DIM) { Card(0, clicked = false, gone = false) } }
    private var recentX = -1
    private var recentY = -1
    public var won = false
    public var waiting = false

    init {
        resetModel()
    }

    public fun getCard(x: Int, y: Int) = model[x][y]

    public fun click(x: Int, y: Int) {
        if (x == recentX && y == recentY) return
        model[x][y].clicked = true
        if (recentX > -1 && recentY > -1) {
            waiting = true
            //wait
            val handler = Handler()
            handler.postDelayed(Runnable {
                checkMatch(x, y)
            }, 500)
        }
        else {
            recentX = x
            recentY = y
        }
    }

    public fun checkMatch(x: Int, y: Int) {
        if (model[x][y].type == model[recentX][recentY].type) {
            model[x][y].gone = true
            model[recentX][recentY].gone = true
        }
        else {
            model[x][y].clicked = false
            model[recentX][recentY].clicked = false
        }
        recentX = -1
        recentY = -1
        waiting = false
        checkWin()
    }

    public fun resetModel() {
        won = false
        val totalCount = DIM * DIM
        val uniqueCount = totalCount / 2
        val arr = (1..totalCount).toList()
        Collections.shuffle(arr)
        var k = 0
        for (i in 0 until DIM) {
            for (j in 0 until DIM) {
                model[i][j] = Card(arr[k].rem(uniqueCount), clicked = false, gone = false)
                k += 1
            }
        }
    }

    public fun checkWin() {
        for (i in 0 until DIM) {
            for (j in 0 until DIM) {
                if (!model[i][j].gone) return
            }
        }
        won = true
    }
}
