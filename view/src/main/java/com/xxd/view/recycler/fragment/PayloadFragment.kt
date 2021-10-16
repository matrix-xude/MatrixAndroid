package com.xxd.view.recycler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.xxd.common.base.fragment.BaseFragment
import com.xxd.common.costom.binding.helper.BaseBindingQuickAdapter
import com.xxd.common.costom.binding.helper.BaseBindingViewHolder
import com.xxd.common.costom.decoration.CommonItemDecoration
import com.xxd.common.extend.*
import com.xxd.common.util.log.LogUtil
import com.xxd.view.R
import com.xxd.view.databinding.ViewFragmentRecyclerPayloadBinding
import com.xxd.view.databinding.ViewItemNineViewSpecialBinding
import com.xxd.view.myself.nine.NineControlSpecialView

/**
 *    author : xxd
 *    date   : 2021/10/16
 *    desc   : RecyclerView局部刷新的完整测试
 */
class PayloadFragment : BaseFragment() {

    private var viewBinding by binding<ViewFragmentRecyclerPayloadBinding>()
    private lateinit var adapter: BaseBindingQuickAdapter<Data1, ViewItemNineViewSpecialBinding>

    private var list1: MutableList<Data1>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = ViewFragmentRecyclerPayloadBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun initView() {
        super.initView()

        initRecycler()
        initClick()
    }

    override fun initDataLazy() {
        super.initDataLazy()

        list1 = createData1()
        adapter.setList(list1)


    }

    private fun initClick() {
        viewBinding.tv1.onClick {
            adapter.notifyDataSetChanged()
        }
        viewBinding.tv2.onClick {
            adapter.notifyItemChanged(0)
        }
        viewBinding.tv3.onClick {
            adapter.notifyItemChanged(0, 1)
        }
        viewBinding.tv4.onClick {
            adapter.notifyItemRangeChanged(0, 3, 2)  // 0，1，2条目使用了局部刷新，payload为2
            adapter.notifyItemRangeChanged(0, 2, 5)// 0，1条目使用了局部刷新，payload为5
            adapter.notifyItemChanged(0) // 0条目使用了全局刷新，理论上则不会进入到带payloads的刷新方法
            adapter.notifyItemChanged(1, 2)  // 再次使得position=1的条目局部刷新payload=2，测试是否会去重
        }

        viewBinding.tv6.onClick {
            list1!![0].name = "修改后的"
            adapter.notifyItemChanged(0)
        }
        viewBinding.tv7.onClick {
            list1!![0].name = "局部修改后的"
            adapter.notifyItemChanged(0, 0 or Data1.NAME_CHANGE or Data1.IMAGE_NUMBER_CHANGE)
        }
        viewBinding.tv8.onClick {
            list1!![0].name = "局部修改后的2"
            adapter.notifyItemChanged(0, 0 or Data1.NAME_CHANGE)
        }

        viewBinding.tv9.onClick {
            val list2 = list1!!.toMutableList()
            list2.removeAt(0)
            list2.add(0, Data1(0, "Diff之后1", 1))
            val diffBack = object : Diff<Data1>(list1!!, list2) {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return list1!![oldItemPosition].id == list2[newItemPosition].id
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return if (list1!![oldItemPosition].name != list2[newItemPosition].name)
                        Data1.NAME_CHANGE
                    else
                        null
                }
            }

            val calculateDiff = DiffUtil.calculateDiff(diffBack)
            adapter.setListNoLoadMore(list2)
            calculateDiff.dispatchUpdatesTo(adapter)
        }

        viewBinding.tv10.onClick {
            val list2 = list1!!.toMutableList()
            list2.removeAt(0)
            list2.add(0, Data1(0, "Diff之后2", 1))
            val diffBack = object : Diff<Data1>(list1!!, list2) {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return list1!![oldItemPosition].id == list2[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return true  // 这里返回true,不再调用之后的getChangePayload
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    return if (list1!![oldItemPosition].name != list2[newItemPosition].name)
                        Data1.NAME_CHANGE
                    else
                        null
                }
            }

            val calculateDiff = DiffUtil.calculateDiff(diffBack)
            adapter.setListNoLoadMore(list2)
            calculateDiff.dispatchUpdatesTo(adapter)
        }
    }

    private fun initRecycler() {

        adapter = object : BaseBindingQuickAdapter<Data1, ViewItemNineViewSpecialBinding>() {

            override fun convert(holder: BaseBindingViewHolder<ViewItemNineViewSpecialBinding>, item: Data1) {
                val position = getPositionFromHolder(holder)
                LogUtil.d("非局部刷新被调用 position=$position data=$item")

                holder.binding.tv1.text = item.name

                holder.binding.nineControlView.setAdapter(object : NineControlSpecialView.Adapter {
                    override fun getCount(): Int {
                        return item.imageNumber
                    }

                    override fun createView(position: Int): View {
                        return ImageView(context).apply {
//                            layoutParams = ViewGroup.LayoutParams(400, 600)
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            Glide.with(context).load(R.drawable.view_bg_2).into(this)
                        }
                    }
                })
            }


            override fun convert(holder: BaseBindingViewHolder<ViewItemNineViewSpecialBinding>, item: Data1, payloads: List<Any>) {
                val position = getPositionFromHolder(holder)
                LogUtil.d("~~局部刷新被调用 position=$position data=$item payloads=$payloads")
                super.convert(holder, item, payloads)

                var change = 0
                payloads.forEach {
                    it as Int
                    change = change or it
                }

                if (change and Data1.NAME_CHANGE != 0) {
                    holder.binding.tv1.text = item.name
                }
                if (change and Data1.IMAGE_NUMBER_CHANGE != 0) {
                    holder.binding.nineControlView.setAdapter(object : NineControlSpecialView.Adapter {
                        override fun getCount(): Int {
                            return item.imageNumber
                        }

                        override fun createView(position: Int): View {
                            return ImageView(context).apply {
//                                layoutParams = ViewGroup.LayoutParams(1700, 800)
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                Glide.with(context).load(R.drawable.view_bg_4).into(this)
                            }
                        }
                    })
                }
            }

        }

        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CommonItemDecoration().apply {
                boundary = 10
                interval = 20
            })
            adapter = this@PayloadFragment.adapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun createData1(): MutableList<Data1> {
        val list = mutableListOf<Data1>()
        val random = (5..20).random()
        repeat(random) {
            if (it == 0)
                list.add(Data1(it, "名字$it", 1))
            else
                list.add(Data1(it, "名字$it", (1..9).random()))
        }
        return list
    }


    data class Data1(
        var id: Int, // DiffUtil测试使用
        var name: String, // 名称
        var imageNumber: Int // 图片数量
    ) {
        companion object {
            const val NAME_CHANGE = 1
            const val IMAGE_NUMBER_CHANGE = 4
        }
    }

    abstract class Diff<T>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            LogUtil.d("oldItemPosition=${oldList[oldItemPosition].toString()}\nnewItemPosition=${newList[newItemPosition].toString()}")
            return oldList[oldItemPosition].toString() == newList[newItemPosition].toString()
        }


    }
}