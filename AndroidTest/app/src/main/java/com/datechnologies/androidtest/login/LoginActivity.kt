package com.datechnologies.androidtest.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.datechnologies.androidtest.Api
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.LogIn
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
class LoginActivity : AppCompatActivity() {
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    private var et_UserName: EditText? = null
    private var et_UserPassword: EditText? = null
    private var btnSignIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_UserName = findViewById<View>(R.id.et_UserName) as EditText
        et_UserPassword = findViewById<View>(R.id.et_UserPassword) as EditText
        btnSignIn = findViewById<View>(R.id.btnSignIn) as Button

        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setTitle("Login")


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.

        et_UserName!!.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (!hasFocus) {
                    hideSoftKeyboard(v)
                }
            }
        })

        et_UserPassword!!.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (!hasFocus) {
                    hideSoftKeyboard(v)
                }
            }
        })

        btnSignIn!!.setOnClickListener {

            checkConnection()

        }
    }

    fun checkConnection() {

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = connectivityManager.activeNetworkInfo

        if (ni != null && ni.isConnectedOrConnecting) {
            ValidateLogin()
        } else {
            Toast.makeText(applicationContext, R.string.networkError, Toast.LENGTH_SHORT).show()
        }
    }


    fun hideSoftKeyboard(v: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    private fun ValidateLogin() {

        var userName = et_UserName!!.text.toString()
        var Password = et_UserPassword!!.text.toString()


        if (TextUtils.isEmpty(userName)) {
            et_UserName!!.error = "Please enter email address."
        } else if (TextUtils.isEmpty(Password)) {
            et_UserPassword!!.error = "Please enter password."
        } else {

            val ROOT_URL = "http://dev.rapptrlabs.com/Tests/scripts/"
            fun getRetrofitInstance(): Retrofit {
                return Retrofit.Builder()
                        .baseUrl(ROOT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }

            fun getApiService(): Api {
                return getRetrofitInstance().create(Api::class.java)
            }

            val api = getApiService()
            val call = api.getLoginJSON(userName, Password)

            call?.enqueue(object : Callback<LogIn> {
                override fun onResponse(call: Call<LogIn>, response: Response<LogIn>) {

                    if (response != null) {
                        if (response.isSuccessful()) {

                            var userID = response.body()!!.code
                            if (userID.equals("Success")) {

                                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                                alertDialog.setTitle("AlertDialog")
                                alertDialog.setMessage(response.body()?.message)
                                alertDialog.setPositiveButton(
                                        "OK"
                                ) { _, _ ->
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                }
                                alertDialog.setNegativeButton(
                                        "Cancel"
                                ) { _, _ -> }
                                val alert: AlertDialog = alertDialog.create()
                                alert.setCanceledOnTouchOutside(false)
                                alert.show()
                            }
                        } else {

                            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                            alertDialog.setTitle("AlertDialog")
                            alertDialog.setMessage("Invalid Credentials!!")
                            alertDialog.setPositiveButton(
                                    "OK"
                            ) { _, _ ->
                                et_UserName!!.setText("")
                                et_UserPassword!!.setText("")
                            }
                            alertDialog.setNegativeButton(
                                    "Cancel"
                            ) { _, _ -> }
                            val alert: AlertDialog = alertDialog.create()
                            alert.setCanceledOnTouchOutside(false)
                            alert.show()

                        }
                    }
                }

                override fun onFailure(call: Call<LogIn>, t: Throwable) {

                }
            })
        }
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
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}