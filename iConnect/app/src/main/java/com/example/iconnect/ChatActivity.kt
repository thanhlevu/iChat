package com.example.iconnect

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.header.*
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity(), Observer {
    init {
        ChatSystem.connection = Connection("192.168.1.35")
        ChatSystem.myThread = Thread(ChatSystem.connection)
    }

    private var mToggle: ActionBarDrawerToggle? = null

    var messageList: ArrayList<Messages> = ArrayList()
    var now = SimpleDateFormat("HH:mm")
    var checkName: String = ""
    override fun update(event: UpdateEvent) {
        Handler(Looper.getMainLooper()).post {
            if (event.type == EventType.SOCKET_STARTED) {
                ChatSystem.connection?.sendMessage(checkName)
                ChatSystem.messageStorage.clear()
 //               msgList?.adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, ChatSystem.messageStorage) as ListAdapter?
            } else if (event.message == "Please, Use another name") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, event.message, Toast.LENGTH_LONG).show()
            } else if (event.message!!.contains("#@#")) {
                ChatSystem.topChatStorage = (event.message.split("#@#").toMutableSet())
                ChatSystem.topChatStorage.remove("")
                var topChatterList: ArrayList<ItemType> = ArrayList()
                ChatSystem.topChatStorage.toList().forEach { i: String -> topChatterList.add(ItemType(i.replace("sent", ":"), R.drawable.ic_user_128)) }
                topChatter_listView.adapter = ItemAdapter(this, topChatterList)
                ChatSystem.connection?.sendMessage(":users")
            } else if (event.message!!.contains("#$#")) {
                ChatSystem.userStorage = (event.message.split("#$#").toMutableList())
                ChatSystem.userStorage.remove("")
                var userList: ArrayList<ItemType> = ArrayList()
                ChatSystem.userStorage.forEach { i: String -> userList.add(ItemType(i.replace("Users: ", ""), R.drawable.ic_user_128)) }
                active_friend_list.adapter = ItemAdapter(this, userList)
            } else if (event.message!!.startsWith("*User ")) {
                user_name?.text = checkName.replace(":user ", "")
                ChatSystem.connection?.sendMessage(":topChat")
            } else if (event.message == "User name not set. Use command :user to set it" || event.message == "") {
            } else if (event.message == "Welcome:") {
                ChatSystem.connection?.sendMessage(":messages")
            } else if (event.message!!.contains("@@@")) {
                var history_message_list: MutableList<String>
                history_message_list = event.message.split("@@@").toMutableList()
                history_message_list.remove("")
                history_message_list.forEach { i: String ->
                    ChatSystem.messageStorage.add(i)
                    messageList.add(Messages(
                            (i.split("at").toMutableList()[0]).split(":").toMutableList()[0],
                            ((i.split("at").toMutableList()[0]).split(":").toMutableList()[1]),
                            R.drawable.ic_user_128,
                            ((i.split("at").toMutableList()[1]).split(".").toMutableList()[0]).replace("T", " ")))
                    msgList?.adapter = CustomMessageAdapter(this, messageList, checkName.replace(":user ", ""))
                }
                ChatSystem.connection?.sendMessage(":topChat")
                msgList.post(Runnable { msgList.setSelection(msgList.getCount() - 1) })
            } else {
                ChatSystem.messageStorage.add(event.message)
                messageList.add(Messages(event.message.split(":").toMutableList()[0], event.message.split(":").toMutableList()[1],
                        R.drawable.ic_user_128, now!!.format(Calendar.getInstance().getTime())))
                msgList?.adapter = CustomMessageAdapter(this, messageList, checkName.replace(":user ", ""))
                ChatSystem.connection?.sendMessage(":topChat")
                msgList.post(Runnable { msgList.setSelection(msgList.getCount() - 1) })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        ChatSystem.connection?.registerObserver(this)
        ChatSystem.myThread?.start()
        var intent = intent
        checkName = intent.getStringExtra("name")


        val adapter = MyViewPagerAdapter(supportFragmentManager)

        val chatRoom = ChatFragment()
        chatRoom.retainInstance = true

        val friendActive = FriendsFragment()
        friendActive.retainInstance = true

        adapter.addFragment(chatRoom, getString(R.string.chat_room))
        adapter.addFragment(friendActive, getString(R.string.friends))

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)


        mToggle = ActionBarDrawerToggle(this, activity_chat, R.string.open, R.string.close)
        activity_chat.addDrawerListener(mToggle!!)
        mToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ChatSystem.connection?.quit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mToggle!!.onOptionsItemSelected(item)) {
            var topChatterList: ArrayList<ItemType> = ArrayList()
            ChatSystem.topChatStorage.toList().forEach { i: String -> topChatterList.add(ItemType(i.replace("sent", ":"), R.drawable.ic_user_128)) }
            topChatter_listView.adapter = ItemAdapter(this, topChatterList)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}

