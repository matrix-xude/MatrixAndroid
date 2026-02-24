package com.xxd.myself.room.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxd.myself.room.dao.UserDao
import com.xxd.myself.room.domain.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

/**
 * author : xxd
 * date   : 2024/5/29
 * desc   :
 */
@RunWith(AndroidJUnit4::class)
class DoorDatabaseTest {

    private lateinit var userDao: UserDao
    private lateinit var db: DoorDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DoorDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUser() {
        val user = fakeUser()
        println(user.toString())
        Log.e("xxd", user.toString())
        userDao.insert(user)
        Log.d("xxd", "高级啊")
        val i = 1
    }


    private val names = listOf("张三", "李四", "一苇渡江王宝宝", "驴车漂移", "trump", "里宾特洛甫", "莫洛托夫", "叶海亚·辛瓦尔")

    private fun fakeUser(): User {
        val userId = UUID.randomUUID().leastSignificantBits // Long类型唯一di
        val userName = names[names.indices.random()] // 取随机名字
        return User(id = 0, userId = userId, userName = userName, userIcon = null, userKeys = null)
    }

    private fun fakeUsers(num: Int): List<User> {
        val list = mutableListOf<User>()
        repeat(num) {
            list.add(fakeUser())
        }
        Log.d("xxd", "当前list=$list size=${list.size}")
        return list
    }
}