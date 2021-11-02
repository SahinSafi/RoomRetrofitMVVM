package com.safi.assignment.loginSignup

import android.app.Application
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.safi.assignment.roomDB.RoomDB
import com.safi.assignment.roomDB.tables.User
import kotlinx.coroutines.*

class LoginSignupViewModel(application: Application, private val lifecycleOwner: LifecycleOwner) :
    ViewModel() {

    private val db: RoomDB = RoomDB.getAppDataBase(application)!!

    val onProgress = MutableLiveData<Boolean>()
    val isRegistrationSuccess = MutableLiveData<Boolean>()
    val onLoginSuccess = MutableLiveData<User>()


    fun registerUser(user: User, view: View) {
        onProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val isExist: Boolean = db.dao().isExist(user.username)
            delay(1500)
            withContext(Dispatchers.Main) {
                if (isExist) {
                    onProgress.postValue(false)
                    Snackbar.make(view, "Already Registered", Snackbar.LENGTH_LONG).show()
                } else {
                    val result = db.dao().insertUser(user)
                    withContext(Dispatchers.Main) {
                        onProgress.postValue(false)
                        if (result > 0) {
                            isRegistrationSuccess.postValue(true)
                        } else {
                            isRegistrationSuccess.postValue(false)
                            Snackbar.make(view, "Registration Failed", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    fun loginUser(password: String, username: String, view: View) {
        onProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val isExist = db.dao().isUserExist(password, username)
            delay(1500)
            withContext(Dispatchers.Main) {
                if (isExist) {
                    db.dao().getUser(password, username).observe(lifecycleOwner, {
                        onProgress.postValue(false)
                        onLoginSuccess.postValue(it)
                    })
                } else {
                    onProgress.postValue(false)
                    Snackbar.make(view, "Incorrect credential", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}