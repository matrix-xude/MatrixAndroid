package com.xxd.myself.room.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *    author : xxd
 *    date   : 2024/4/25
 *    desc   :
 */
object DoorDatabaseHelper {

    private var db: DoorDatabase? = null

    fun getDb(context: Context): DoorDatabase {
        if (db == null) {
            synchronized(this::class.java) {
                if (db == null)
                    db = Room
                        .databaseBuilder(context = context, klass = DoorDatabase::class.java, "door.db")
                        .allowMainThreadQueries()
//                        .addMigrations(migration_1_2, migration_2_3)
                        .build()
            }
        }
        return db!!
    }

    // 升级数据库只能手动迁移，并且必须手动创建、修改表
    private val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            Log.d("xxd", "走到了1-2的升级数据库代码")

            db.execSQL("create table 'users2' (id INTEGER PRIMARY KEY NOT NULL, user_id INTEGER NOT NULL, user_name TEXT NOT NULL,user_icon TEXT, user_keys TEXT)")
        }
    }

    private val migration_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            Log.d("xxd", "走到了2-3的升级数据库代码")

            db.execSQL("drop table if exists 'door_users'")
        }
    }


}