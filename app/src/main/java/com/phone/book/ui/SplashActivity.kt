package com.phone.book.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.phone.book.R
import com.phone.book.common.BuildConfig
import com.phone.book.common.Intents

class SplashActivity : AppCompatActivity() {
    private var handler: Handler? = Handler()
    private val runnable = Runnable {

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        handler!!.postDelayed(runnable, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler!!.removeCallbacks(runnable)
        handler = null
    }
}