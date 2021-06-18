package com.tvd12.freechat.firebase.cloud.message

import android.R
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tvd12.freechat.MainActivity


class MyFirebaseMessagingService : FirebaseMessagingService(){
    private lateinit var auth: FirebaseAuth


    override fun onNewToken(token: String) {
        super.onNewToken(token);
        Log.d("newToken", token);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply();
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val imgSett = prefs.getString("fb", "");


    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage!!)
    }

    companion object  fun  getToken(mainActivity: MainActivity,context: Context) {

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser == null){
        }else{
            auth.signInWithEmailAndPassword("toilahtc@gmail.com", "123456")
                .addOnCompleteListener(mainActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
//                        updateUI(user)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }

        }
    }


}