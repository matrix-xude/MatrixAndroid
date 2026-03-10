package com.xxd.kt.basic.clazz.single;

/**
 * author : xxd
 * date   : 2026/3/10
 * desc   :
 */
public class MyJava {

    // INSTANCE 变量写在 static 块上方. 构造函数先打印
    private static final MyJava INSTANCE = new MyJava();

    public static final String CONST_TAG = "COMPILER_CONSTANT";

    static {
        System.out.println("MyJava 被加载并初始化了！ (static 代码块执行)");
    }

    private MyJava() {
        System.out.println("MyJava 被加载并初始化了！ (构造函数执行)");
    }

    public static MyJava getInstance(){
        System.out.println("MyJava getInstance()！");
        return INSTANCE;
    }

    // 如果在这里写main函数测试，会导致类一开始就被初始化（因为main是入口，导致类初始化）
    public static void main(String[] args) {
        System.out.println("main 执行");
        /*MyJava 被加载并初始化了！ (构造函数执行)
        MyJava 被加载并初始化了！ (static 代码块执行)
        main 执行*/

    }
}
