//import ActiveClients.privateList
import javafx.beans.binding.When
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.net.Socket
import java.util.*

// Command Interpreter needs InputStream and OutPutStream to interact with Command Line Window.
// Command Interpreter has a socket to connect to server.
// Command Interpreter inherits from ChatHistoryObserver Interface to be able to override the newMessage() method.
// Command Interpreter inherits from Runnable Interface to override the run() method.
class CommandInterpreter(val cmd: InputStream, val action: OutputStream, var s: Socket) : ChatHistoryObserver, Runnable {
    val reader = Scanner(s.getInputStream())           // declare a variable to scan the input from the Command Line Window
    val writter = PrintStream(action)                           // declare a variable to print out the output to the Command Line Window
    override fun newMessage(message: String) {                  // the newMessage() method is overridden with the purpose that takes the message and print it out to the Command Line Window
        writter.println(message)
    }

    var isOff = false                       // create this boolean variable to know if the user want to continue using Chat Server or not
    var hadUserName = false                 // create this boolean variable: "did the user has a user name yet?"
    var userName = ""                       // this variable will store the accepted user name
    var messageCounter: Int = 0             // this integer variable will count the user messages
    override fun run() {                                         // the run() method is overridden to deal with what the user inputs
        writter.println("Welcome:")
        while (!hadUserName && !isOff) {                         // the loop requires the user to register a User Name first.
            val input = reader.nextLine()                                         // create "input" variable to read the next line command from user
            if (input.startsWith(":user ")) {                                      // check the command if it starts with ":user "
                userName = input.replace(":user ", "")                  // userName takes the string after replacing ":user " as a User Name
                hadUserName = Users.insertUserName(userName)                              // hadUserName = true if the registered userName meets the requirements(no space, no used name) and saved in a set of User Names
                if (hadUserName) {                               // if hadUserName = true, now the user is able to use that name, the while loop is going to be ended
                    ChatHistory.registerObserver(this)                                   // register Observer for this CommandInterpreter (this user)
                    writter.println("*User [$userName]: accepted")                             // send a notification that the registered name is accepted
                }                                                        // print out the top 3 chatters who have the most messages to the console
                else {
                    writter.println("Please, Use another name")                           // print out a notification ("Please, Use another name")
                }
            } else if (input.startsWith(":exit")) {                                // check the command if it starts with ":exit "
                println("Did not get it :exit")                                                   // print out a notification to the console
                writter.println("Did not get it :exit")                                           // print out a notification to the Command Line Window

            } else if (input.startsWith(":users")) {                               // check the command if it starts with ":users "
                var userList = ""
                for (names in Users.usersName) {                                           // this loop to print all user names to the console window and the command line window
                    println("[$names]")
                    userList += "Users: [$names] is active#$#"
                }
                writter.println(userList)
            } else if (input.startsWith(":quit")) {                                // check the command if it starts with ":quit "
                println("Goodbye $userName")                                                      // print out a notification ("Goodbye") to the console window
                isOff = true                                                                      // turn off the switch of using the Chat Server == close the while loop
            } else {                                                                      // check the command if it starts with anything else
                println("User name not set. Use command :user to set it")                         // print out a notification ("User name not set. Use command :user to set it") to the console window
                writter.println("User name not set. Use command :user to set it")                 // print out a notification ("User name not set. Use command :user to set it") to the command line window
            }
        }
        //the loop for making and implementing commands
        while (hadUserName && !isOff) {                          // the loop : if the user had a user name and want to chat
            val input = reader.nextLine()                                       // create "input" variable to read the next line command from user
            if (input.startsWith(":messages")) {                                 // check the command if it starts with ":messages"
                //writter.println("The Public Messages:")                                           // print out "The Public Messages:" to the command line window
                writter.println(ChatHistory.toString())                                           // print out the public history messages to the command line window
            } else if (input.startsWith(":exit")) {                                // check the command if it starts with ":exit "
                println("Did not get it :exit")                                                   // print out a notification to the console
                writter.println("Did not get it :exit")                                           // print out a notification to the Command Line Window
            } else if (input.startsWith(":topChat")) {                               // check the command if it starts with ":topChat "
                TopChatter.topChat = ""                                                           // clear old top chat data.
                TopChatter.topMessages()                                                          // print out the top 3 chatters who have the most messages to the console
                if(TopChatter.topChat != ""){                                                     // make sure that top chatter has data
                    writter.println(TopChatter.topChat)
                }
            } else if (input.startsWith(":users")) {                               // check the command if it starts with ":users "
                var userList = ""
                for (names in Users.usersName) {                                           // this loop to print all user names to the console window and the command line window
                    println("[$names]")
                    userList += "Users: [$names] is active#$#"
                }
                writter.println(userList)
            } else if (input.startsWith(":quit")) {                                // check the command if it starts with ":quit "
                println("Goodbye $userName")                                                      // print out a notification ("Goodbye") to the console window
                Users.removeUserName(userName)                                                    // remove the user name from the set of user names
                TopChatter.usersMessageCounter.remove(userName)                                   // remove the user name from the map of top chatter
                TopChatter.topMessages()                                                          // print out the top 3 chatters who have the most messages to the console
                isOff = true                                                                      // turn off the switch of using the Chat Server == close the while loop
            } else {
                ChatHistory.insert("$userName: $input")                         // insert a message to the message list in history
                messageCounter++                                                                  // increase a number of the user's messages
                TopChatter.messagesCounter(userName, messageCounter)                              // save the user names and and their number of messages
            }
        }
        s.close()
    }
}

