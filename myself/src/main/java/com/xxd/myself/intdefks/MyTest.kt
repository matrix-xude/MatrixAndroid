package com.xxd.myself.intdefks

import com.xxd.myself.intdef.DomainA

/**
 *    author : xxd
 *    date   : 2023/12/3
 *    desc   :
 */
class MyTest {

    private fun fun1(){
//        setDomain(1)
        setDomain(DomainB.type3)
    }

    private fun setDomain(@MyAnnotation i: Int){

    }
}