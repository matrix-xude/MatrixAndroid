package com.xxd.common.costom.binding.helper

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *    author : xxd
 *    date   : 2021/7/13
 *    desc   : BaseViewHolder 扩展为带有viewBinding的类型
 */
class BaseBindingViewHolder<VB : ViewBinding>(val binding : VB) : BaseViewHolder(binding.root)