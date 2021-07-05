package com.xxd.common.base.fragment

/**
 *    author : xxd
 *    date   : 2020/8/21
 *    desc   : fragment init data 的触发时机，包括懒加载等
 */
interface IFragmentInitData {

    /**
     * 立即加载数据
     * 即fragment被挂载到可以显示的区域后立马加载
     */
    fun initDataImmediately()

    /**
     * 懒加载
     * 即fragment成为可见的前台界面再加载
     * 如：viewPager的预加载等，不会在预加载的时候加载数据
     */
    fun initDataLazy()

    /**
     * 每次都加载数据
     * 即fragment每次成为可见界面都调用
     */
    fun initDataEveryTime()

}