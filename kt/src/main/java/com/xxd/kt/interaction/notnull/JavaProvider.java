package com.xxd.kt.interaction.notnull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * author : xxd
 * date   : 2021/6/25
 * desc   : Java类提供可null字段
 */
public class JavaProvider {

    @Nullable
    public static String str1 = "111";
    @NotNull
    public static String str2 = "222";
    // 没有标记的Java变量
    public static String str3 = "333";

}
