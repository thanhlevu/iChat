// Student Name:  THANH LE
// Student ID:    1706203
// ChatConsole object is used to print out notifications to console window
object ChatConsole: ChatHistoryObserver {
    init {
        ChatHistory.registerObserver(this)
    }
    override fun newMessage(message: String) {
        println(message)
    }
}