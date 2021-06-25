package com.xxd.kt.interaction.keyword;

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   : Java文件，包含kotlin关键字
 */
public class JavaKeyword {

    // in 是kotlin关键字
    public static final int in = 1;

    public static void main(String[] args) {
        // 主动加上set、get方法解决关键字冲突问题
        KtKeyword.Companion.getStatic();
    }
}
