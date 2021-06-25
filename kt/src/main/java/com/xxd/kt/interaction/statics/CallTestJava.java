package com.xxd.kt.interaction.statics;

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   : Java调用static相关测试
 */
public class CallTestJava {

    public static void main(String[] args) {
        CallTestJava testJava = new CallTestJava();
        testJava.callKtObject();
        testJava.callKtCompanion();
        testJava.callJavaStatic();
    }

    private void callKtObject() {
        System.out.println("KtObject: " + KtObject.INSTANCE.getI() + KtObject.INSTANCE.getStr());
        KtObject.INSTANCE.show();
        KtObject.show2();
    }

    private void callKtCompanion() {
        System.out.println("KtCompanion: " + KtCompanion.Companion.getI() + KtCompanion.Companion.getStr());
        KtCompanion.Companion.show();
        KtCompanion.show2(); // @JvmStatic 确实生成了2个方法
        KtCompanion.Companion.show();
    }

    // java调用java不再研究
    private void callJavaStatic(){}
}
