package com.xxd.myself.room

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xxd.common.fast.SimpleListActivity
import com.xxd.myself.R
import com.xxd.myself.room.dao.UserDao
import com.xxd.myself.room.database.DoorDatabaseHelper
import com.xxd.myself.room.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

/**
 *    author : xxd
 *    date   : 2024/4/25
 *    desc   :
 */
class RoomActivity : SimpleListActivity<String>() {

    private val listData = listOf("insert 1条数据", "insert 2条数据", "insert n条数据", "delete 1条数据", "delete n条数据", "deleteById", "update 1条数据")

    override fun getDataList(): Collection<String> {
        return listData
    }

    override fun getItemLayoutResId(): Int {
        return R.layout.common_item_vertical_simple_text
    }

    override fun convertItem(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }

    override fun getTitleName(): CharSequence {
        return "Room数据库测试"
    }

    override fun initView() {
        super.initView()

        initListener()
    }

    private lateinit var userDao: UserDao

    override fun initData() {
        super.initData()

        userDao = DoorDatabaseHelper.getDb(this@RoomActivity).userDao()
    }

    private fun initListener() {
        simpleAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    userDao.insert(fakeUser())
                }

                1 -> {
                    userDao.insert(fakeUser(), fakeUser())
                }

                2 -> {
                    userDao.insert(fakeUsers(4))
                }

                3 -> {
                    userDao.delete(User(id = 1, userId = 0L, userName = "", userIcon = null, userKeys = null))
                }

                4 -> {
                    userDao.delete(
                        listOf(
                            User(id = 3, userId = 0L, userName = "", userIcon = null, userKeys = null),
                            User(id = 5, userId = 0L, userName = "", userIcon = null, userKeys = null),
                            User(id = 8, userId = 0L, userName = "", userIcon = null, userKeys = null)
                        )
                    )
                }

                5 -> {
                    userDao.deleteById(10)
                }

                6 -> {
                    val user = userDao.queryById(12)
                    val update = userDao.update(user!!.copy(userName = "王大锤"))
                    Log.d("xxd","update result = $update")
                }


                else -> {

                }
            }
        }
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