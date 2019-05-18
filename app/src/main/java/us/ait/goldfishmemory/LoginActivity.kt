package us.ait.goldfishmemory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import us.ait.goldfishmemory.data.Player

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun registerClick(v: View) {
        if (!isFormValid()) {
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            val user = it.user
            user.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(userNameFromEmail(user.email!!))
                    .build()
            )
            addPlayerRecord(user)
            Toast.makeText(this@LoginActivity, getString(R.string.register_ok_msg),
                Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this@LoginActivity, getString(R.string.register_fail_msg) + it.message,
                Toast.LENGTH_LONG).show()
        }
    }

    fun loginClick(v: View){
        if (!isFormValid()){
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            Toast.makeText(this@LoginActivity,
                getString(R.string.login_ok_msg), Toast.LENGTH_LONG).show()
            //finish()
        }.addOnFailureListener{
            Toast.makeText(this@LoginActivity,
                getString(R.string.login_fail_msg) + it.message, Toast.LENGTH_LONG).show()
        }
    }


    private fun addPlayerRecord(user: FirebaseUser) {
        val player = Player(user.uid, userNameFromEmail(user.email!!),
            100f, 0, getString(R.string.icon_url_1))

        val playersCollection = FirebaseFirestore.getInstance().collection("players")
        playersCollection.add(player).addOnSuccessListener {
            Toast.makeText(this@LoginActivity, getString(R.string.player_created_msg), Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this@LoginActivity, getString(R.string.error_header) + it.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun isFormValid(): Boolean {
        return when {
            etEmail.text.isEmpty() -> {
                etEmail.error = getString(R.string.empty_field_msg)
                false
            }
            etPassword.text.isEmpty() -> {
                etPassword.error = getString(R.string.empty_field_msg)
                false
            }
            else -> true
        }
    }

    private fun userNameFromEmail(email: String) = email.substringBefore("@")
}