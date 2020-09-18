package com.xxd.thread.basic;

/**
 * author : xxd
 * date   : 2020/9/18
 * desc   : 测试编译、反编译后 synchronized 关键字怎么实现
 */
public class SynchronizedTest {

    private int i;

    public void add() {
        synchronized (this) {
            i++;
        }
    }
}

/*  public void add();
    descriptor: ()V
            flags: (0x0001) ACC_PUBLIC
            Code:
            stack=3, locals=3, args_size=1
            0: aload_0
            1: dup
            2: astore_1
            3: monitorenter
            4: aload_0
            5: dup
            6: getfield      #7                  // Field i:I
            9: iconst_1
            10: iadd
            11: putfield      #7                  // Field i:I
            14: aload_1
            15: monitorexit
            16: goto          24
            19: astore_2
            20: aload_1
            21: monitorexit
            22: aload_2
            23: athrow
            24: return
            Exception table:
            from    to  target type
            4    16    19   any
            19    22    19   any
            LineNumberTable:
            line 13: 0
            line 14: 4
            line 15: 14
            line 16: 24

*/