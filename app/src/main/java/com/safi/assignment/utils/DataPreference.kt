package com.safi.assignment.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preference")

class DataPreference(context: Context) {

    /**create datastore**/
    private val mDataStore: DataStore<Preferences> = context.dataStore

    /**(Data Keys) here you have to mention which type of data you will be store**/
    companion object {
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }

    /**Set Data**/
    suspend fun setUsername(username: String) {
        mDataStore.edit {
            it[USER_NAME] = username
        }
    }

    suspend fun setPassword(password: String) {
        mDataStore.edit {
            it[PASSWORD] = password
        }
    }

    /**Get Data**/
    val usernameFlow: Flow<String> = mDataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val passwordFlow: Flow<String> = mDataStore.data.map {
        it[PASSWORD] ?: ""
    }

    @DelicateCoroutinesApi
    fun clearAllSessionData() {
        GlobalScope.launch {
            setUsername("")
            setPassword("")
        }

    }
}

/**
 * Example set and get data
 *
 * private fun exampleOfDataStore() {
dataPreference = DataPreference(applicationContext)
lifecycleScope.launch {
dataPreference.setUsername("Shahin")
}

dataPreference.usernameFlow.asLiveData().observe(this){
Log.i("CheckData", "onCreate: $it")
}
}**/