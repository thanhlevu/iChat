import java.io.PrintStream
import java.net.ServerSocket
import java.util.*
import kotlin.concurrent.thread
// Student Name:  THANH LE
// Student ID:    1706203
// ChatServer class manages the connections to server and catch the exception
class ChatServer{
     fun serve(){
         ChatConsole               // present new messages to console window
         try {                     // use try{} - catch{} to avoid exceotions happened during the connecting process
            val serverSocket = ServerSocket(3333, 1)                                              // create a socket server with ports and
            println(" We have port " + serverSocket.localPort )
            while(true) {
                val s = serverSocket.accept()                                                           // wait for a connection
                println("new connection " + s.port+""+s)                      // print out a notification when there is a connection
                val processThread = Thread(CommandInterpreter(s.getInputStream(), s.getOutputStream(), s))      // create a thread
                processThread.start()
            }
        } catch (e: Exception) {    // if there is an exception ==> print out the notification ("Got exception: ")
            println("Got exception: ${e.message}")
        } finally {                 // the notification ("All serving done.") is always printed out to the console window because of finally function
            println("All serving done.")
        }
    }
}