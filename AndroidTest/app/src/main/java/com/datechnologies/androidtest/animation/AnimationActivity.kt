package com.datechnologies.androidtest.animation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R


/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */
class AnimationActivity : AppCompatActivity() {
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    private var btnFadeIn: Button? = null
    private var imgFadeIn: ImageView? = null
    private var xDelta = 0
    private var yDelta = 0
    private var mainLayout: ViewGroup? = null

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        btnFadeIn = findViewById<View>(R.id.btnFadeIn) as Button
        imgFadeIn = findViewById<View>(R.id.imgFadeIn) as ImageView
        mainLayout = findViewById<View>(R.id.main) as RelativeLayout

        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setTitle("Animation")

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.
        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!


        imgFadeIn!!.setOnTouchListener(onTouchListener());
        btnFadeIn!!.setOnClickListener {

            val animation = AnimationUtils.loadAnimation(this, R.anim.fade)
            imgFadeIn!!.startAnimation(animation)
        }

    }

    private fun onTouchListener(): OnTouchListener? {
        return OnTouchListener { view, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view
                            .layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    view.layoutParams = layoutParams
                }
            }
            mainLayout!!.invalidate()
            true
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        //==============================================================================================
        // Class Properties
        //==============================================================================================
        //==============================================================================================
        // Static Class Methods
        //==============================================================================================
        fun start(context: Context) {
            val starter = Intent(context, AnimationActivity::class.java)
            context.startActivity(starter)
        }
    }
}