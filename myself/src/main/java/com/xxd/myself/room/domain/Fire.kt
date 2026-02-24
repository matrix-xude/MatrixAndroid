package com.xxd.myself.room.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *    author : xxd
 *    date   : 2024/6/24
 *    desc   :
 */

@Entity(tableName = "fire")
data class Fire(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "desc") val desc: String,

)
