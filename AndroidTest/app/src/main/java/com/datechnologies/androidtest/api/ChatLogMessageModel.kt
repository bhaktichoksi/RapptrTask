package com.datechnologies.androidtest.api

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

class ChatLogMessageModel : Serializable {
    @SerializedName("user_id")
    @Expose
    var userId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null


    constructor() {}

    constructor(userId: String?, name: String?, avatarUrl: String?, message: String?) : super() {
        this.userId = userId
        this.name = name
        this.avatarUrl = avatarUrl
        this.message = message
    }

}