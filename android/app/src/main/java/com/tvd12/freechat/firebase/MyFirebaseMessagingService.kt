package com.tvd12.freechat.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import java.util.logging.Logger

class ChatFirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var auth: FirebaseAuth


    override fun onNewToken(token: String) {
        super.onNewToken(token);
        storeToken(token)
    }

    private fun storeToken(token: String) {
        getSharedPreferences("_", MODE_PRIVATE)
            .edit()
            .putString("fb", token)
            .apply();
    }

}
