package com.datechnologies.androidtest.chat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatLogMessageModel
import com.datechnologies.androidtest.chat.ChatAdapter.ChatViewHolder
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * A recycler view adapter used to display chat log messages in [ChatActivity].
 *
 */
class ChatAdapter : RecyclerView.Adapter<ChatViewHolder>() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private var chatLogMessageModelList: List<ChatLogMessageModel>

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================
    fun setChatLogMessageModelList(chatLogMessageModelList: List<ChatLogMessageModel>) {
        this.chatLogMessageModelList = chatLogMessageModelList
        notifyDataSetChanged()
    }

    //==============================================================================================
    // RecyclerView.Adapter Methods
    //==============================================================================================
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        val chatLogMessageModel = chatLogMessageModelList[position]
        viewHolder.messageTextView.text = chatLogMessageModel.message
        viewHolder.userTextView.text = chatLogMessageModel.name

        Picasso.get().load(chatLogMessageModel.avatarUrl).into(viewHolder.avatarImageView)
        //viewHolder.avatarImageView.setImageBitmap(getBitmapFromURL(chatLogMessageModel.avatarUrl))

    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun getItemCount(): Int {
        return chatLogMessageModelList.size
    }

    //==============================================================================================
    // ChatViewHolder Class
    //==============================================================================================
    class ChatViewHolder(view: View) : ViewHolder(view) {
        var avatarImageView: ImageView
        var messageTextView: TextView
        var userTextView: TextView

        init {
            avatarImageView = view.findViewById<View>(R.id.avatarImageView) as ImageView
            messageTextView = view.findViewById<View>(R.id.messageTextView) as TextView
            userTextView = view.findViewById<View>(R.id.userTextView) as TextView
        }
    }

    //==============================================================================================
    // Constructor
    //==============================================================================================
    init {
        chatLogMessageModelList = ArrayList()
    }
}