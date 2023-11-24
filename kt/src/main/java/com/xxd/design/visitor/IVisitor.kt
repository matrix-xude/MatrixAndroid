package com.xxd.design.visitor

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   : 访问者必须知道每个被访问元素的具体类型，这里违反了依赖倒置原则
 */
interface IVisitor {

    fun visitElementA(elementA: ElementA)
}