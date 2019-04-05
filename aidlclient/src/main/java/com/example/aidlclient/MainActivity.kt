package com.example.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.aidlserver.GetServerDetails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     var getServerDetails: GetServerDetails? = null

    var serviceConnection : ServiceConnection? = object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            getServerDetails = null
            Toast.makeText(this@MainActivity, "AIDL service disconnected", Toast.LENGTH_LONG).show()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
           getServerDetails = GetServerDetails.Stub.asInterface(service)
            Toast.makeText(this@MainActivity, "AIDL service connected", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        server_id.setOnClickListener {
            Toast.makeText(this@MainActivity, "UserId is ${getServerDetails?.serverId}", Toast.LENGTH_LONG).show()
        }
        owner_name.setOnClickListener {
            Toast.makeText(this@MainActivity, "UserId is ${getServerDetails?.serverOwnerName}", Toast.LENGTH_LONG).show()
        }

        initService()
    }

    private fun initService() {
        val intent = Intent()
        intent.setComponent(ComponentName("com.example.aidlserver", "com.example.aidlserver.GetServerDetailsService"))
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseService()
    }

    private fun releaseService() {
        unbindService(serviceConnection)
        serviceConnection = null
        Log.d("sos", "releaseService() trigger")
    }
}
