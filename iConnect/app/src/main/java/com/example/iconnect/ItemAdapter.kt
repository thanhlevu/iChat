package com.example.iconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ItemAdapter(var context: Context, var arrayList: ArrayList<ItemType>) : BaseAdapter() {

    class ViewHolder(row: View) {
        var item_content: TextView
        var item_photo: ImageView

        init {
            item_content = row.findViewById(R.id.item)
            item_photo = row.findViewById(R.id.item_icon)
        }
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_layout, null)

            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        var items: ItemType = getItem(position) as ItemType
        viewHolder.item_content.text = items.content
        viewHolder.item_photo.setImageResource(items.photo)

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