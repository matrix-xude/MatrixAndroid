package com.xxd.kt.function.inline;

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : Java调用内联函数
 */
public class JavaInvokeInline {

    public static void main(String[] args) {
        // 没有任何区别，都是传入Function1回调，所以java不存在内联特性
        KtInlineKt.attack(1, i -> i + 1);
        KtInlineKt.attack2(1, i -> i + 1);
    }
}
