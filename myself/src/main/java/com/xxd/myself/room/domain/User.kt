package com.xxd.myself.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *    author : xxd
 *    date   : 2024/4/24
 *    desc   : 门的用户
 */

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "user_icon") val userIcon: String?,
    @ColumnInfo(name = "user_keys") val userKeys: String?, // 用户秘钥，用json表示，可以为多个
)
