package com.tvd12.freechat

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import android.content.IntentFilter


class MyFirebaseMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        Log.d("TAG","The token refreshed: $token")
    }


}