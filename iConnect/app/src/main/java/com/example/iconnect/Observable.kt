package com.example.iconnect

interface Observable {
    fun registerObserver(observer: Observer)                  // the function is to register observers
    fun deregisterObserver(observer: Observer)                // the function is to deregister observers
    fun notifyObservers(event: UpdateEvent)
}