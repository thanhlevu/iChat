import java.io.PrintStream
import java.time.LocalDateTime
// Student Name:  THANH LE
// Student ID:    1706203
// ChatHistory object where saves all messages and observers, allows to override registerObservers, deregisterObservers and notifyObservers functions, add a message to history as well as return all the messages
object ChatHistory: ChatHistoryObservable {
    var messageHistory = mutableListOf<String>()                            // where stores all messages
    var observers = mutableSetOf<ChatHistoryObserver>()          // where stores all observers
    override fun registerObserver(observer: ChatHistoryObserver) {                            // register observer by adding observer to the observer set
        observers.add(observer)
    }
    override fun deregisterObserver(observer: ChatHistoryObserver) {                          // deregister observer by removing observer from the observer set
        observers.remove(observer)
    }
    override fun notifyObservers(message: String) {                                           // take a input message to send to all observers
        for (i in observers){
            i.newMessage(message)
        }
    }
    fun insert(message: String) {
        messageHistory.add("$message at ${LocalDateTime.now()}")                               // add the message (+ sender name + sending time) into the List
        notifyObservers(message)                                                            // user notifyObservers to send messages
    }
    override fun toString(): String {                                                          // return the whole chat history as one string
        var converHistoryList: String = ""                                                     // create a string variable to contain all the messages as a long string
        for (i in 0..messageHistory.size - 1) {                                           // combine all the messages to one string, separating each message with \n
            converHistoryList += messageHistory[i] + "@@@" }
        return converHistoryList                                                               // return the long string
    }
}