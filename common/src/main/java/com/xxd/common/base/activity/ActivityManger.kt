package com.xxd.common.base.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.orhanobut.logger.Logger

/**
 *    author : xxd
 *    date   : 2021/9/13
 *    desc   :
 */
class ActivityManger private constructor() {

    private val activityList = mutableListOf<Activity>()

    companion object {
        val instance = ActivityManger()
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityList.add(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                activityList.remove(activity)
            }
        })
    }

    /**
     * 移除指定名字的Activity，除了Activity在top的情况
     * 目的：主要是为了防止页面循环跳转，但是又不方便使用singleTask的模式
     * @param activityName 全类名
     */
    fun removeNotTopActivity(activityName: String) {
        for (index in activityList.size - 2..0) {
            Logger.d(activityList[index].javaClass.name)
            if (activityList[index].javaClass.name == activityName) {
                activityList[index].finish()
            }
        }
    }

}