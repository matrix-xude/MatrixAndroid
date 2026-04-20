package com.xxd.myself.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xxd.myself.room.domain.User

/**
 *    author : xxd
 *    date   : 2024/4/24
 *    desc   :
 */

@Dao
interface UserDao {

    @Insert
    fun insert(vararg users: User)

    @Insert
    fun insert(users: List<User>)

    @Delete
    fun delete(vararg users: User)

    @Delete
    fun delete(users: List<User>)

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteById(id : Int)

    @Update
    fun update(vararg user: User): Int

    @Query("SELECT * FROM user")
    fun queryAll(): List<User>

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun queryById(id: Int): User?

}
