package com.xxd.view.bigpic.photo

/**
 *    author : xxd
 *    date   : 2022/8/16
 *    desc   : 修改源码，增加一个onTouch的UP、CANCEL事件监听
 *          因为内部消费了onTouch事件，外部拿不到，必须通过此方法拿到事件
 */
internal interface OnViewUpCancelListener {

    /**
     * 当前View的UP or Cancel事件触发
     */
    fun onViewUpCancel()
}