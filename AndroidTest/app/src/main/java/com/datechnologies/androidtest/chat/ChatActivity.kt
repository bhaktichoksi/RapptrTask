package com.datechnologies.androidtest.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.datechnologies.androidtest.Api
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatData
import com.datechnologies.androidtest.api.ChatLogMessageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

/**
 * Screen that displays a list of chats from a chat log.
 */
class ChatActivity : AppCompatActivity() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private var recyclerView: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setTitle("Chat")
        chatAdapter = ChatAdapter()
        recyclerView!!.adapter = chatAdapter
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,
                false)

        getChatData()
        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }

    private fun getChatData() {
        val tempList: MutableList<ChatLogMessageModel> = ArrayList()

        val ROOT_URL = "https://dev.rapptrlabs.com/Tests/scripts/"
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        fun getApiService(): Api {
            return getRetrofitInstance().create(Api::class.java)
        }

        val apis = getApiService()
        val call = apis.getDataFromURL()

        call?.enqueue(object : Callback<ChatData> {
            override fun onResponse(call: Call<ChatData>, response: Response<ChatData>) {
                if (response.isSuccessful && response != null) {

                    val chatResponse = response.body()?.data!!
                    if (chatResponse != null) {
                        for (i in 0 until chatResponse.size) {
                            var chatList = chatResponse.get(i)

                            tempList.add(ChatLogMessageModel(chatList.userId, chatList.name, chatList.avatarUrl, chatList.message))
                            chatAdapter!!.setChatLogMessageModelList(tempList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatData>, t: Throwable) {
                println("failure")
            }
        })
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        //==============================================================================================
        // Static Class Methods
        //==============================================================================================
        fun start(context: Context) {
            val starter = Intent(context, ChatActivity::class.java)
            context.startActivity(starter)
        }
    }
}