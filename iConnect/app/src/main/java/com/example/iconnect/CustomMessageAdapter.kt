package com.example.iconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomMessageAdapter(var context: Context, var arrayList: ArrayList<Messages>, var myName: String) : BaseAdapter() {
    class OtherViewHolder(row: View) {
        var sender_name: TextView
        var message_content: TextView
        var message_time: TextView
        var user_photo: ImageView

        init {
            sender_name = row.findViewById(R.id.sender_name)
            message_content = row.findViewById(R.id.message_content)
            message_time = row.findViewById(R.id.current_time)
            user_photo = row.findViewById(R.id.user_photo)
        }
    }

    class MyViewHolder(row: View) {
        var sender_name: TextView
        var message_content: TextView
        var message_time: TextView
        var user_photo: ImageView

        init {
            sender_name = row.findViewById(R.id.me)
            message_content = row.findViewById(R.id.my_message_content)
            message_time = row.findViewById(R.id.now)
            user_photo = row.findViewById(R.id.my_photo)
        }
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        var view: View?
        var messages: Messages = getItem(position) as Messages
        var myViewHolder: MyViewHolder
        var otherViewHolder: OtherViewHolder
        if (myName == messages.name) {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.my_message_layout, null)
            myViewHolder = CustomMessageAdapter.MyViewHolder(view)
            var messages: Messages = getItem(position) as Messages
            myViewHolder.message_content.text = messages.content
            myViewHolder.sender_name.text = messages.name
            myViewHolder.message_time.text = messages.currentTime
            myViewHolder.user_photo.setImageResource(messages.photo)
        } else {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.message_layout, null)
            otherViewHolder = CustomMessageAdapter.OtherViewHolder(view)

            var messages: Messages = getItem(position) as Messages
            otherViewHolder.message_content.text = messages.content
            otherViewHolder.sender_name.text = messages.name
            otherViewHolder.message_time.text = messages.currentTime
            otherViewHolder.user_photo.setImageResource(messages.photo)
        }
        return view as View
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}