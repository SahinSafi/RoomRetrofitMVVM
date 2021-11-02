package com.safi.assignment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.safi.assignment.loginSignup.LoginActivity
import com.safi.assignment.mainActivity.MainActivity
import com.safi.assignment.utils.DataPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var dataPreference: DataPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        dataPreference = DataPreference(this)

        var username = ""

        dataPreference.usernameFlow.asLiveData().observe(this, {
            username = it
        })

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if (username.isEmpty()) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            finish()
        }
    }
}