package com.xxd.myself.intdef;

/**
 * author : xxd
 * date   : 2023/12/3
 * desc   :
 */
public class MyTest {


    public static void main(String[] args) {
//        setNumber(3);  不能直接使用数字了
        setNumber(DomainA.Type1);
    }

    public static void setNumber(@MyAnnotation int i){

    }
}
