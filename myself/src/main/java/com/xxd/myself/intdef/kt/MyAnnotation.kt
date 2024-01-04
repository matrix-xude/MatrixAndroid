package com.xxd.myself.intdef.kt

import androidx.annotation.IntDef

/**
 *    author : xxd
 *    date   : 2023/12/3
 *    desc   :
 */
@IntDef(DomainB.type1, DomainB.type2, DomainB.type3)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class MyAnnotation {
}