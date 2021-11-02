package com.safi.assignment.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.safi.assignment.roomDB.tables.User

@Dao
interface DAO {

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE password =:password AND username =:username")
    fun getUser(password: String, username: String): LiveData<User>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE password =:password AND username =:username)")
    fun isUserExist(password: String, username: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username =:username)")
    fun isExist(username: String): Boolean
}