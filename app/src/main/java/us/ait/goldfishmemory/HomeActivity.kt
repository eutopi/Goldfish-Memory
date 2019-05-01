package us.ait.goldfishmemory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.R.attr.delay
import android.content.Intent
import android.os.Handler
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.view.View


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        increaseWaterLevel()
    }

    private fun increaseWaterLevel() {
        var progress = 25
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (++progress < 40) {
                    wave_view_home.setProgress(progress)
                    handler.postDelayed(this, 100L)
                    return
                }
                handler.removeCallbacks(this)
            }
        }, 100L)
    }

    fun profileClick(v: View) {
        startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
