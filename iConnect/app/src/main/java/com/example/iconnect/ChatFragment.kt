package com.example.iconnect


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button_send.setOnClickListener {
            if (editText_message.text.toString() != "") {
                ChatSystem.connection?.sendMessage(editText_message.text.toString())
                ChatSystem.connection?.sendMessage(":topChat")
                ChatSystem.connection?.sendMessage(":users")
                editText_message.text.clear()
            }
        }
        msgList?.adapter = ArrayAdapter(view.context, android.R.layout.simple_expandable_list_item_1, ChatSystem.messageStorage) as ListAdapter?    //keep the fragment, not disappear
    }
}
