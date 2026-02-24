package com.xxd.myself.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxd.myself.room.domain.Fire
import com.xxd.myself.room.domain.User

/**
 *    author : xxd
 *    date   : 2024/6/28
 *    desc   :
 */
@Dao
interface FireDao {

    @Insert
    fun insert(vararg fires: Fire)

    @Query("SELECT * FROM fire")
    fun queryAll(): List<Fire>
}