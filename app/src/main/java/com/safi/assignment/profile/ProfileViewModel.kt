package com.safi.assignment.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.safi.assignment.roomDB.RoomDB
import com.safi.assignment.roomDB.tables.User
import com.safi.assignment.utils.DataPreference
import kotlinx.coroutines.*

class ProfileViewModel(
    application: Application,
    private val lifecycleOwner: LifecycleOwner,
    context: Context
) :
    ViewModel() {

    private val dataPreference = DataPreference(context)
    private val db: RoomDB = RoomDB.getAppDataBase(application)!!
    val onProfileDataGet = MutableLiveData<User>()
    val onProgress = MutableLiveData<Boolean>()

    fun getUserData() {
        onProgress.postValue(true)
        var password = ""
        var username = ""
        dataPreference.passwordFlow.asLiveData().observe(lifecycleOwner, {
            password = it
        })

        dataPreference.usernameFlow.asLiveData().observe(lifecycleOwner, {
            username = it
        })

        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            withContext(Dispatchers.Main) {
                db.dao().getUser(password, username).observe(lifecycleOwner, {
                    onProgress.postValue(false)
                    onProfileDataGet.postValue(it)
                })
            }
        }
    }

    fun logout() {
        dataPreference.clearAllSessionData()
    }

}