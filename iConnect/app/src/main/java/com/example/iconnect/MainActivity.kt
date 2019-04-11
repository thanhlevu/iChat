package com.example.iconnect

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_Login.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("name", ":user " + editText_Name!!.text.toString())
            startActivity(intent)
        }
    }
}
