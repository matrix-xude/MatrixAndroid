package com.xxd.view.recycler.diff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxd.common.base.activity.BaseTitleActivity
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.databinding.CommonSimpleTextBinding
import com.xxd.common.util.log.LogUtil
import com.xxd.view.databinding.ViewActivityRecyclerBinding
import java.util.*

/**
 *    author : xxd
 *    date   : 2020/8/13
 *    desc   : 通过RecyclerView研究DiffUtil的效果
 */
class RecyclerDiffActivity : BaseTitleActivity() {

    private lateinit var viewBinding: ViewActivityRecyclerBinding
    private lateinit var adapter1: RecyclerView.Adapter<*>
    private lateinit var adapter3: RecyclerView.Adapter<*>

    private var listData: MutableList<String>? = null
    private var listData3: MutableList<Bean1>? = null

    override fun provideBaseTitleRootView(rootView: ViewGroup) {
        viewBinding = ViewActivityRecyclerBinding.inflate(layoutInflater, rootView, true)
    }

    override fun getTitleName(): CharSequence {
        return "DiffUtil"
    }

    override fun initView() {
        super.initView()
        initRecyclerView1()
        initRecyclerView2()
        initRecyclerView3()
    }

    override fun initData() {
        super.initData()
        listData = fakeData2()
    }

    private fun initRecyclerView3() {
        viewBinding.rv3.apply {
            layoutManager = LinearLayoutManager(this@RecyclerDiffActivity)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 30
            })
            adapter3 =
                object : RecyclerView.Adapter<BaseBindingViewHolder<CommonSimpleTextBinding>>() {

                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<CommonSimpleTextBinding> {
                        LogUtil.d("创建ViewHolder被执行了 $viewType")
                        val inflate = CommonSimpleTextBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                        return BaseBindingViewHolder(inflate)
                    }

                    override fun onBindViewHolder(holder: BaseBindingViewHolder<CommonSimpleTextBinding>, position: Int) {
                        LogUtil.d("onBindViewHolder $position")
                        listData3?.let {
                            val bean = it[position]
                            holder.binding.tvName.text = bean.bean2.name
                        }
                    }

                    override fun getItemCount(): Int {
                        return listData3?.size ?: 0
                    }
                }

            adapter = adapter3
        }
    }

    // 创建第一个RecyclerView
    private fun initRecyclerView1() {
        viewBinding.rv1.apply {
            layoutManager = LinearLayoutManager(this@RecyclerDiffActivity)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 30
            })
            adapter1 =
                object : RecyclerView.Adapter<BaseBindingViewHolder<CommonSimpleTextBinding>>() {

                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<CommonSimpleTextBinding> {
                        LogUtil.d("创建ViewHolder被执行了 $viewType")
                        val inflate = CommonSimpleTextBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                        return BaseBindingViewHolder(inflate)
                    }

                    override fun onBindViewHolder(holder: BaseBindingViewHolder<CommonSimpleTextBinding>, position: Int) {
                        LogUtil.d("onBindViewHolder $position")
                        listData?.let {
                            val bean = it[position]
                            holder.binding.tvName.text = bean
                        }
                    }

                    override fun getItemCount(): Int {
                        return listData?.size ?: 0
                    }
                }

            adapter = adapter1
        }
    }


    private fun fakeData(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        repeat(29) {
            mutableList.add("哥德尔不完备定理$it")
        }
        return mutableList
    }

    private fun fakeData2(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        repeat(9) {
            mutableList.add("量例力学$it")
        }
        return mutableList
    }

    // 直接插入，notifyDataSetChanged，会刷新之后的所有item,并且需要重新onCreateViewHolder
    private fun insert1(mutableList: MutableList<String>) {
        val size = mutableList.size
        // 随机插入
        val random = (0 until size).random()
        mutableList.add(random, "我是插入11")
    }

    // 直接插入，返回插入的值，精确更新
    private fun insert2(mutableList: MutableList<String>): Int {
        val size = mutableList.size
        // 随机插入
        val random = (0 until size).random()
        mutableList.add(random, "我是插入21")
        return random
    }

    // 修改值，精确更新
    private fun change1(mutableList: MutableList<String>): Int {
        val size = mutableList.size
        // 随机插入
        val random = (0 until size).random()
        mutableList.removeAt(random)
        mutableList.add(random, "我是插入31")
        return random
    }

    // 删除，精确值
    private fun delete1(mutableList: MutableList<String>): Int {
        val size = mutableList.size
        // 随机插入
        val random = (0 until size).random() // 0 -1，导致报错
        mutableList.removeAt(random)
        return random
    }

    // 创建第2个RecyclerView
    private fun initRecyclerView2() {
        viewBinding.rv2.apply {
            layoutManager = GridLayoutManager(this@RecyclerDiffActivity, 4)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 8
                spanInterval = 15
            })
            adapter = object : BaseBindingQuickAdapter<String, CommonSimpleTextBinding>() {
                override fun convert(
                    holder: BaseBindingViewHolder<CommonSimpleTextBinding>,
                    item: String
                ) {
                    holder.binding.tvName.text = item
                }
            }.apply {
                setNewInstance(
                    mutableListOf(
                        "notifyAll",
                        "插入2",
                        "修改1",
                        "删除1",
                        "listData赋新值",
                        "Diff赋新值",
                        "data检测",
                        "data检测2"
                    )
                )
                setOnItemClickListener { _, _, position ->
                    when (position) {
                        0 -> {
                            insert1(listData!!)
                            adapter1.notifyDataSetChanged()
                        }
                        1 -> {
                            val insert2 = insert2(listData!!)
                            adapter1.notifyItemInserted(insert2)
                        }
                        2 -> {
                            val change1 = change1(listData!!)
                            adapter1.notifyItemChanged(change1)
                        }
                        3 -> {
                            val delete1 = delete1(listData!!)
                            adapter1.notifyItemRemoved(delete1)
                        }
                        4 -> {
                            listData = fakeData()
                            adapter1.notifyDataSetChanged()
                        }
                        5 -> {
                            val newData = MutableList(listData?.size ?: 0) {
                                listData?.get(it) ?: ""
                            }
                            newData?.add("高等代数")
                            val cb1 = MyDiff(listData, newData)
                            val diffResult = DiffUtil.calculateDiff(cb1)
                            listData = newData
                            diffResult.dispatchUpdatesTo(adapter1)
                        }
                        6 -> {
                            val b1 = Bean1(1, Bean1.Bean2("jack"))
                            val b2 = Bean1(2, Bean1.Bean2("amber"))
                            val b3 = Bean1(1, Bean1.Bean2("youtube"))
                            val b4 = Bean1(1, Bean1.Bean2("jack"))


                            old.apply {
                                add(b1)
                                add(b2)
                                add(b3)
                            }
                            new.apply {
                                add(b1)
                                add(b2)
                                add(b4)
                            }
                            listData3 = old
                            adapter3.notifyDataSetChanged()

                        }
                        7 -> {
                            val myDiff = object : MyDiff<Bean1>(old, new) {
                                override fun areItemsTheSame(
                                    oldItemPosition: Int,
                                    newItemPosition: Int
                                ): Boolean {
                                    super.areItemsTheSame(oldItemPosition, newItemPosition)
                                    return old[oldItemPosition].id == new[newItemPosition].id
                                }
                            }
                            listData3 = new
                            val calculateDiff = DiffUtil.calculateDiff(myDiff, true)
                            val convertNewPositionToOld = calculateDiff.convertNewPositionToOld(2)
                            LogUtil.d("``` convertNewPositionToOld(2) = $convertNewPositionToOld")
                            val c2 = calculateDiff.convertOldPositionToNew(2)
                            LogUtil.d("``` convertOldPositionToNew(2) = $c2")
                            calculateDiff.dispatchUpdatesTo(MyListUpdateBack())
                            calculateDiff.dispatchUpdatesTo(adapter3)
                        }
                    }
                }
            }

        }
    }

    val old = mutableListOf<Bean1>()
    val new = mutableListOf<Bean1>()
}