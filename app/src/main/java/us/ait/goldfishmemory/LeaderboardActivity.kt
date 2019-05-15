package us.ait.goldfishmemory

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_leaderboard.*
import us.ait.goldfishmemory.adapter.PlayersAdapter
import us.ait.goldfishmemory.data.Player

class LeaderboardActivity : AppCompatActivity() {

    lateinit var playersAdapter: PlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        playersAdapter = PlayersAdapter(
            this,
            FirebaseAuth.getInstance().currentUser!!.uid
        )
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerPlayers.layoutManager = layoutManager
        recyclerPlayers.adapter = playersAdapter

        initPosts()
    }

    private fun initPosts() {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("players")
        var allPlayersListener = query.addSnapshotListener(
            object : EventListener<QuerySnapshot> {
                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    if (e != null) {
                        Toast.makeText(this@LeaderboardActivity, "listen error: ${e.message}", Toast.LENGTH_LONG).show()
                        return
                    }

                    for (dc in querySnapshot!!.getDocumentChanges()) {
                        when (dc.getType()) {
                            DocumentChange.Type.ADDED -> {
                                val player = dc.document.toObject(Player::class.java)
                                playersAdapter.addPlayer(player, dc.document.id)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Toast.makeText(this@LeaderboardActivity, "update: ${dc.document.id}", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
        )
    }
}
