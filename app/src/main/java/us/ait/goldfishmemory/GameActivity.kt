package us.ait.goldfishmemory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewCompat
import kotlinx.android.synthetic.main.activity_game.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v4.os.HandlerCompat.postDelayed
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import us.ait.goldfishmemory.data.Player
import us.ait.goldfishmemory.model.GameModel
import us.ait.goldfishmemory.view.GameView
import java.util.*

class GameActivity : AppCompatActivity() {

    private var timerExist = false
    private var time = 0
    private var gameCounted = false

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (GameModel.won) {
                    if (timeStatus.text.toString().toFloat() < player.bestTime) {
                        player.bestTime = timeStatus.text.toString().toFloat()
                        tvBestTime.text = "Best time: " + timeStatus.text
                    }
                    if (!gameCounted) {
                        player.gamesPlayed = player.gamesPlayed + 1
                        gameCounted = true
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
    private lateinit var docID: String
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        getPlayer()

        timeStatus.text = (time / 1000.toDouble()).toString()
        startTimer()

        btnReplay.setOnClickListener {
            gameView.resetGame()
            time = 0
            gameCounted = false
            startTimer()
        }
    }

    private fun startTimer() {
        if (!timerExist) {
            mainTimer = Timer()
            timerExist = true
            mainTimer.schedule(MyTimerTask(), 0, 1)
        }
    }

    private fun getPlayer() {
        FirebaseFirestore.getInstance().collection("players")
            .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    docID = document.id
                    player = document.toObject(Player::class.java)
                    tvBestTime.text = "Best time: " + player.bestTime
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@GameActivity, "Error retrieving player", Toast.LENGTH_LONG).show()
            }
    }

    private fun closeGame() {
        FirebaseFirestore.getInstance().collection("players").document(docID)
            .set(player)
        mainTimer.cancel()
        timerExist = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeGame()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onStop() {
        super.onStop()
        closeGame()
    }
}
