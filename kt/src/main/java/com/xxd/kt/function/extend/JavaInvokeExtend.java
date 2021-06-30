package com.xxd.kt.function.extend;

/**
 * author : xxd
 * date   : 2021/7/1
 * desc   : Java调用扩展函数
 */
public class JavaInvokeExtend {

    public static void main(String[] args) {
        // source对象没有plusPlus方法，所以kotlin函数并没有真正修改Source类
        Source source = new Source();
        source.plus(1, 2);

        // 扩展函数是 SourceExtendKt，没有plus方法，并且需要传入Source对象
        SourceExtendKt.plusPlus(source, 1, 2);
    }

}
