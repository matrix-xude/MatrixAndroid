package com.xxd.design.prototype;


import java.util.Arrays;
import java.util.List;

/**
 * author : xxd
 * date   : 2023/11/23
 * desc   : 浅拷贝
 */
public class PrototypeImpl implements IPrototype{

    int mInt = 1;
    List<Integer> mList = Arrays.asList(1,2,3,4);

    @Override
    protected PrototypeImpl clone() throws CloneNotSupportedException {
        return (PrototypeImpl) super.clone();
    }
}
