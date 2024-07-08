package com.xxd.myself.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.xxd.myself.room.domain.Fire

/**
 *    author : xxd
 *    date   : 2024/6/28
 *    desc   :
 */
@Dao
interface FireDao {

    @Insert
    fun insert(vararg fires: Fire)
}