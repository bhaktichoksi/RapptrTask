package com.datechnologies.androidtest.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ChatData : Serializable {
    @SerializedName("data")
    @Expose
    var data: List<ChatLogMessageModel>? = null

    constructor() {}

    constructor(data: List<ChatLogMessageModel>?) : super() {
        this.data = data
    }

}