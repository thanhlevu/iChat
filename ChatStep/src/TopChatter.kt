// Student Name:  THANH LE
// Student ID:    1706203
// TopChatter object: where contains pairs of information (user names and their number of messages) and present to the console window
object TopChatter {
    val usersMessageCounter = mutableMapOf<String, Int>()        // this map variable is responsible for storing user names and their numbers of messages
    var topChat:String = ""
    fun messagesCounter( userName: String, messageCounter: Int){                      // update user names and their numbers of messages
        usersMessageCounter.put(userName, messageCounter)
    }
    fun topMessages(){                                                                // this function is used for formatting the usersMessageCounter and print it out as an understandable notification
        var arrangedMap = mutableMapOf<Int, String>()                           // this map variable is to swap user name and number of messages in usersMessageCounter map so that the keys are integer, then we can rank users with the most messages
        usersMessageCounter.forEach{userName, messagesCounter->
            arrangedMap.put(messagesCounter, userName)
        }
        println("Top 3 Chatters: ")                                                                  // print out ("Top 3 Chatters: ")
        var i = 1                                                                                    // this integer variable is to break the forEach loop below (limit the chatters printed out)
        arrangedMap.toSortedMap(reverseOrder()).forEach {                                            // print out only top 3 chatter
            numberOfMessages, userName ->
            if (i > 4)return@forEach
            println("[$userName] sent $numberOfMessages messages")
            topChat += "[$userName] sent $numberOfMessages messages#@#"
            i++
        }
    }
}