package com.datechnologies.androidtest.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class LogIn : Serializable {
    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    constructor() {}

    constructor(code: String?, message: String?) : super() {
        this.code = code
        this.message = message
    }

}