package com.xxd.design.prototype;


import java.util.Arrays;
import java.util.List;

/**
 * author : xxd
 * date   : 2023/11/23
 * desc   : 深拷贝
 */
public class PrototypeImpl2 implements IPrototype{

    int mInt = 1;
    List<Integer> mList = Arrays.asList(1,2,3,4);

    @Override
    protected PrototypeImpl2 clone() throws CloneNotSupportedException {
        var clone= (PrototypeImpl2) super.clone();
        clone.mList = List.copyOf(mList);
        return clone;
    }
}
