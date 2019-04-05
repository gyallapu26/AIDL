package com.example.aidlserver

import android.app.Service
import android.content.Intent
import android.os.IBinder

class GetServerDetailsService : Service() {

    override fun onBind(intent: Intent): IBinder {
       return object : GetServerDetails.Stub(){
           override fun getServerId(): Int {
              return 4026
           }

           override fun getServerOwnerName() = "Guru Prasad Yallapu"

       }
    }
}
