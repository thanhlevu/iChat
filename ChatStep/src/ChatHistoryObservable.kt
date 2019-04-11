// Student Name:  THANH LE
// Student ID:    1706203
// ChatHistoryObservable interface allows to register Observer, deregister Observer and send messages to Observers
interface ChatHistoryObservable {
    fun registerObserver(observer:ChatHistoryObserver)                  // the function is to register observers
    fun deregisterObserver(observer:ChatHistoryObserver)                // the function is to deregister observers
    fun notifyObservers (message:String)                                // the function is to send messages to observers
}