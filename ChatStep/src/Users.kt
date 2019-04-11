import java.io.PrintStream
import java.net.Socket
// Student Name:  THANH LE
// Student ID:    1706203
// Users object where contains a set of user names, check the registered names, update the accepted name, remove the user name
object Users {
    val usersName = hashSetOf<String>()             // this set is to store all user names
    fun insertUserName(registeredName: String): Boolean {                     // this function returns a true/false result, means the registered name is accepted or not
        if (registeredName == "") {                                                       // if registered name is empty ==> print out a notification and return false
            println("Please, Enter your name")
            return false
        } else if (usersName.contains(registeredName)) {                                    // if registered name is existed ==> print out a notification and return false
            println("Please, Use another name")
            return false

        } else {                                                                            // if registered name is available ==> add the registered name to the set of user names, print out a notification and return false
            usersName.add(registeredName)
            println("User set to " + registeredName)
            return true
        }
    }

    fun removeUserName(name: String) {                                                     // remove the user name from the set
        usersName.remove(name)
    }
}