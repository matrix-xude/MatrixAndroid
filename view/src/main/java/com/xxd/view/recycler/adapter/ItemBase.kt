package com.xxd.view.recycler.adapter

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *    author : xxd
 *    date   : 2021/8/16
 *    desc   :
 */
data class ItemBase(
    override val itemType: Int, // type类型
    val name: String,
) : MultiItemEntity
