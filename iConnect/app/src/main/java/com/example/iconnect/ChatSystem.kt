package com.example.iconnect

object ChatSystem {
    var connection: Connection? = null
    var myThread: Thread? = null
    val messageStorage = mutableListOf<String>()
    var userStorage = mutableListOf<String>()
    var topChatStorage = mutableSetOf<String>()
}