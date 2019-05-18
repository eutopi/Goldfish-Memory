package us.ait.goldfishmemory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.row_player.*
import us.ait.goldfishmemory.data.Player

class ProfileActivity : AppCompatActivity(), IconDialog.ProfileHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        txtUsername.text = FirebaseAuth.getInstance().currentUser!!.displayName
        setPlayerStats()

        ivAvatar.setOnClickListener {
            IconDialog().show(supportFragmentManager, "TAG_ICON_DIALOG")
        }
    }

    private fun setPlayerStats() {
        FirebaseFirestore.getInstance().collection("players")
            .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val player = document.toObject(Player::class.java)
                    tvShortestTime.text = player!!.bestTime.toString()
                    tvGamesPlayed.text = player!!.gamesPlayed.toString()
                    if (player!!.icon.isNotEmpty()) {
                        Glide.with(this).load(player!!.icon).into(ivAvatar)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@ProfileActivity, "Error retrieving player", Toast.LENGTH_LONG).show()
            }
    }

    override fun updateIcon(url: String) {
        Glide.with(this).load(url).into(ivAvatar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
