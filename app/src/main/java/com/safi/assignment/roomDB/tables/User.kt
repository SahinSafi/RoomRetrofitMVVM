package com.safi.assignment.roomDB.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val username: String,
    val password: String,
    val phone: String
)