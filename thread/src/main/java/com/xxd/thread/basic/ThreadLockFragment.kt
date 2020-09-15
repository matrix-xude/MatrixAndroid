package com.xxd.thread.basic

import com.xxd.common.base.BaseFragment
import com.xxd.common.util.log.LogUtil
import com.xxd.thread.R
import kotlinx.android.synthetic.main.thread_fragment_lock.*
import okhttp3.internal.notifyAll
import java.lang.Exception
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *    author : xxd
 *    date   : 2020/9/14
 *    desc   : Lock是一个主接口，ReentrantLock; ReadWriteLock是一个读写区分的接口，读取的时候可以异步，写入必须同步
 *      1.lock() 不能获取到的时候正常等待
 *      2.tryLock(）立马返回值 true or false，当为false的时候，不会等待，直接往下继续运行
 *      3.tryLock(time, unit）等待固定时间，等待完成后还没有执行，直接往下继续运行
 *      4.lockInterruptibly（）,可以在线程中断的时候立马中断，并抛出异常（不需要线程挂起才中断），这是synchronized做不到的
 *      5.condition ： await signal 必须是同一个
 *      6.ReentrantReadWriteLock()可以获取一个只读锁，一个写入锁
 *          当使用 readLock锁住某个代码块，只要没有写入锁持有，可以拿到当前锁，可以异步进行
 *          当使用 writeLock锁住某个代码块，需要等待其它所有结束才能拿到锁，同时期间其它所有lock无法拿到锁
 */
class ThreadLockFragment : BaseFragment() {

    private var indexFlag1 = 0
    private var indexFlag2 = 0
    private val lock = ReentrantLock()
    private val lock2 = ReentrantReadWriteLock()  // 读可以异步进行，写操作不可和其它操作异步进行
    private val readLock = lock2.readLock()
    private val writeLock = lock2.writeLock()

    override fun getLayoutId(): Int {
        return R.layout.thread_fragment_lock
    }

    // 测试 lockInterrupted函数
    private var thread6: Thread? = null

    // 测试 await()中断后能不能结束线程
    private var thread7: Thread? = null

    // 测试 condition
    private var condition: Condition? = null

    override fun initView() {
        super.initView()
        tv1.setOnClickListener {
            indexFlag1++
            indexFlag2++
            Thread1().start()
            Thread2().start()
        }
        tv2.setOnClickListener {
            indexFlag1++
            indexFlag2++
            Thread3().start()
            Thread4().start()
        }
        tv3.setOnClickListener {
            indexFlag1++
            indexFlag2++
            Thread5().start()
            thread6 = Thread6().apply {
                start()
            }
        }
        tv4.setOnClickListener {
            thread6?.interrupt()
        }
        tv5.setOnClickListener {
            condition = lock.newCondition()
            indexFlag1++
            indexFlag2++
            thread7 = Thread7().apply {
                start()
            }
            Thread8().start()
        }
        tv6.setOnClickListener {
            thread7?.interrupt()
        }
        tv7.setOnClickListener {
            indexFlag1++
            indexFlag2++
            Thread9().start()
            Thread10().start()
        }
        tv8.setOnClickListener {
            indexFlag1++
            indexFlag2++
            Thread9().start()
            Thread10().start()
            Thread11().start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        indexFlag1++
        indexFlag2++
    }

    // 普通lock测试
    inner class Thread1 : Thread() {
        override fun run() {
            val index = indexFlag1
            while (index == indexFlag1) {
                try {  // Lock需要保证异常等情况也释放锁，
                    lock.lock()
                    sleep(1000)
                    LogUtil.d("我是测试lock()函数的,sleep 1秒之后")
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    inner class Thread2 : Thread() {
        override fun run() {
            val index = indexFlag2
            while (index == indexFlag2) {
                try {  // Lock需要保证异常等情况也释放锁，
                    lock.lock()
                    LogUtil.d("我是测试lock()函数的，非 sleep")
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    // tryLock测试
    inner class Thread3 : Thread() {
        override fun run() {
            val index = indexFlag1
            while (index == indexFlag1) {
                try {  // Lock需要保证异常等情况也释放锁，
                    val tryLock = lock.tryLock() // 有返回值
                    sleep(1000)
                    LogUtil.d("我是测试 tryLock()函数的,sleep 1秒之后 $tryLock")
                } finally {
                    try {
                        lock.unlock()
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    inner class Thread4 : Thread() {
        override fun run() {
            sleep(10)
            val index = indexFlag2
            while (index == indexFlag2) {
                try {  // Lock需要保证异常等情况也释放锁，
                    val tryLock = lock.tryLock(2, TimeUnit.SECONDS)
                    LogUtil.d("我是测试 tryLock()函数的，非 sleep, $tryLock")
                    sleep(5000)
                } finally {
                    try {
                        lock.unlock()
                    } catch (e: Exception) {
                    }

                }
            }
        }
    }

    // lockInterruptibly测试,可以在中断线程的时候立马停止等待，并且中断线程的执行,抛出异常，synchronized做不到
    inner class Thread5 : Thread() {
        override fun run() {
            try {  // Lock需要保证异常等情况也释放锁，
                lock.lock()
                LogUtil.d("我是测试 lockInterruptibly()函数的,sleep 6秒之前")
                sleep(6000)
                LogUtil.d("我是测试 lockInterruptibly()函数的,sleep 6秒之后")
            } finally {
                lock.unlock()
            }
        }
    }

    inner class Thread6 : Thread() {
        override fun run() {
            sleep(100)
            try {  // Lock需要保证异常等情况也释放锁，
                lock.lockInterruptibly()
                LogUtil.d("我是测试 lockInterruptibly()函数的，中断打印")
            } catch (e: Exception) {
                LogUtil.e(e.stackTraceToString())
            } finally {
                try {
                    lock.unlock()
                } catch (e: Exception) {

                }
            }
        }
    }

    // condition测试
    inner class Thread7 : Thread() {
        override fun run() {
            val index = indexFlag1
            while (index == indexFlag1) {
                try { // Lock需要保证异常等情况也释放锁，
                    lock.lock()
                    condition?.awaitUninterruptibly()
                    LogUtil.d("我是测试 signalAll()函数的,await（）之后")
                } catch (e: Exception) {
                    LogUtil.e(e.stackTraceToString())
                }finally {
                    lock.unlock()
                }
            }
        }
    }

    inner class Thread8 : Thread() {
        override fun run() {
            val index = indexFlag2
            while (index == indexFlag2) {
                try {  // Lock需要保证异常等情况也释放锁，
                    lock.lock()
                    lock.newCondition().signalAll()
                    sleep(1000)
                    TimeUnit.SECONDS.sleep(1)
                    LogUtil.d("我是测试 signalAll()函数的，sleep之后")
                } finally {
                    lock.unlock()
                }
            }
        }
    }


    // condition测试
    inner class Thread9 : Thread() {
        override fun run() {
            val index = indexFlag1
            while (index == indexFlag1) {
                try { // Lock需要保证异常等情况也释放锁，
                    readLock.lock()
                    LogUtil.d("我是测试 readLock()函数的,111")
                    TimeUnit.SECONDS.sleep(1)
                } catch (e: Exception) {
                    LogUtil.e(e.stackTraceToString())
                }finally {
                    readLock.unlock()
                }
            }
        }
    }

    inner class Thread10 : Thread() {
        override fun run() {
            val index = indexFlag2
            while (index == indexFlag2) {
                try {  // Lock需要保证异常等情况也释放锁，
                    readLock.lock()
                    LogUtil.d("我是测试 readLock()函数的,222")
                    TimeUnit.SECONDS.sleep(5)
                } finally {
                    readLock.unlock()
                }
            }
        }
    }

    inner class Thread11 : Thread() {
        override fun run() {
            sleep(100)
            val index = indexFlag2
            while (index == indexFlag2) {
                try {  // Lock需要保证异常等情况也释放锁，
                    writeLock.lock()
                    LogUtil.d("我是测试 writeLock()函数的,333")
                    TimeUnit.MILLISECONDS.sleep(500)
                } finally {
                    writeLock.unlock()
                }
            }
        }
    }
}