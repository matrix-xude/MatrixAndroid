package com.xxd.myself.intdef;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : xxd
 * date   : 2023/12/3
 * desc   :
 */

@IntDef({DomainA.Type1,DomainA.Type2,DomainA.Type3})
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
public @interface MyAnnotation {
}
