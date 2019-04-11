package com.example.iconnect

import android.util.Log
import java.io.*
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

class Connection(val ipAddress: String) : Runnable, Observable {

    private var observers = mutableSetOf<Observer>()          // where stores all observers
    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun deregisterObserver(observer: Observer) {
        observers.remove(observer)
    }

    private var socket: Socket? = null
    private var reader: Scanner? = null
    private var writer: PrintWriter? = null
    var isRunning = false
        private set

    override fun notifyObservers(event: UpdateEvent) {
        observers.forEach { it.update(event) }
    }

    override fun run() {

        try {
            socket = Socket(ipAddress, 3333)
            reader = Scanner(BufferedReader(InputStreamReader(socket!!.getInputStream())))
            writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket!!.getOutputStream())), true)

            notifyObservers(UpdateEvent(EventType.SOCKET_STARTED, ""))

            isRunning = true

            while (isRunning) {
                val msg = receiveMessage()
                observers.forEach {
                    if (msg != null)
                        it.update(UpdateEvent(EventType.MESSAGE, msg))
                }
            }
            Log.d("server", "server running")
        } catch (e: Exception) {
            Log.d("server", "connect failed")
        }
    }

    fun sendMessage(message: String) {
        thread { writer?.println(message) }
    }

    fun quit() {
        thread {
            writer?.println(":quit")
            socket?.close()
            isRunning = false
        }
    }

    private fun receiveMessage(): String? {
        return reader?.nextLine()
    }
}