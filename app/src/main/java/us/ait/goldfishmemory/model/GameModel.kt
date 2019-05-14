package us.ait.goldfishmemory.model

import java.util.*

data class Card(var type: Int, var clicked: Boolean, var gone: Boolean)

object GameModel {

    private val DIM = 4
    private var model = Array(DIM) { Array(DIM) { Card(0, clicked = false, gone = false) } }
    public var won = false

    init {
        resetModel()
    }

    public fun getCard(x: Int, y: Int) = model[x][y]

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
}
