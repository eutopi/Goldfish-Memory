package us.ait.goldfishmemory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewCompat
import kotlinx.android.synthetic.main.activity_game.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v4.os.HandlerCompat.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import us.ait.goldfishmemory.data.Player
import us.ait.goldfishmemory.model.GameModel
import us.ait.goldfishmemory.view.GameView
import java.util.*

class GameActivity : AppCompatActivity() {

    private var timerExist = false
    private var time = 0

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (GameModel.won) {
                    if (true) {
                        updateBestTime()
                        tvBestTime.text = "Best time: " + timeStatus.text
                    }
                    timerExist = false
                    mainTimer.cancel()
                }
                else {
                    time += 1
                    timeStatus.text = (time / 1000.toDouble()).toString()
                }
            }
        }
    }

    private lateinit var mainTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        timeStatus.text = (time / 1000.toDouble()).toString()

        startTimer()
        btnReplay.setOnClickListener {
            gameView.resetGame()
            time = 0
            startTimer()
        }
        btnExit.setOnClickListener {
            timerExist = false
            mainTimer.cancel()
        }
    }

    private fun startTimer() {
        if (!timerExist) {
            mainTimer = Timer()
            timerExist = true
            mainTimer.schedule(MyTimerTask(), 0, 1)
        }
    }

    private fun updateBestTime() {

    }

    override fun onStop() {
        super.onStop()
        mainTimer.cancel()
        timerExist = false
    }
}
