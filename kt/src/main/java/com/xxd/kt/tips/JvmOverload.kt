package com.xxd.kt.tips

/**
 *    author : xxd
 *    date   : 2023/12/27
 *    desc   :
 */

/**
 *    public MyView2(String var1, int var2, double var3, int var5, DefaultConstructorMarker var6) {
 *       if ((var5 & 1) != 0) {
 *          var1 = "";
 *       }
 *
 *       if ((var5 & 2) != 0) {
 *          var2 = 1;
 *       }
 *
 *       if ((var5 & 4) != 0) {
 *          var3 = 0.0;
 *       }
 *
 *       this(var1, var2, var3);
 *    }
 *
 *    @JvmOverloads
 *    public MyView2(@NotNull String a, int b) {
 *       this(a, b, 0.0, 4, (DefaultConstructorMarker)null);
 *    }
 *
 *    @JvmOverloads
 *    public MyView2(@NotNull String a) {
 *       this(a, 0, 0.0, 6, (DefaultConstructorMarker)null);
 *    }
 *
 *    @JvmOverloads
 *    public MyView2() {
 *       this((String)null, 0, 0.0, 7, (DefaultConstructorMarker)null);
 *    }
 */