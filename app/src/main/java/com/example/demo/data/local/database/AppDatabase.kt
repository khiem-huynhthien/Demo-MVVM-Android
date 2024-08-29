package com.example.demo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo.data.local.dao.UserDao
import com.example.demo.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
