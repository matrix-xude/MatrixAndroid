package com.xxd.myself.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxd.myself.room.dao.UserDao
import com.xxd.myself.room.domain.User

/**
 *    author : xxd
 *    date   : 2024/4/25
 *    desc   :
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DoorDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}