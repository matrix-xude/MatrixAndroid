package com.xxd.kt.interaction.top;

import kotlin.Unit;

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   :
 */
public class CallTestJava {

    public static void main(String[] args) {
        int outI = KtOutKt.getOutI();
        String outStr = KtOutKt.getOutStr();
        KtOutKt.outShow();
        KtOutKt.outShow2("java参数", s -> {
                    System.out.println(s);
                    return Unit.INSTANCE;
                }
        );
    }
}
