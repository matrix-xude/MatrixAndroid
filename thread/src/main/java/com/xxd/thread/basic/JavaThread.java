package com.xxd.thread.basic;

import com.xxd.common.base.BaseFragment;
import com.xxd.common.util.log.LogUtil;

/**
 * author : xxd
 * date   : 2020/9/17
 * desc   :
 */
public class JavaThread {

    private volatile int a = 0;

    public void doIt() {
        new AddThread().start();
        new AddThread().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("累加后的结果是" + a);
    }

    class AddThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                addOne();
            }
            System.out.println("累加后的结果是" + a);
        }
    }

    private synchronized int addOne() {
        return a++;
    }



}
