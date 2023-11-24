package com.xxd.design.interpreter

import com.xxd.kt.interaction.keyword.`in`.`in`
import java.util.Stack

/**
 *    author : xxd
 *    date   : 2023/11/25
 *    desc   : 如何生产解释器,做一个0-9的字符串加减法，如“1+2-3”
 */
object ExpressContext {

    fun calculate(info: String): Int {
        val expression = createExpression(info)
        return expression.interpret()
    }

    private fun createExpression(info: String): IExpression<Int> {
        val stack = Stack<IExpression<Int>>()
        val charArray = info.toCharArray()
        var index = 0

        while (index < charArray.size) {
            when (val it = charArray[index]) {
                '+' -> {
                    val left = stack.pop()
                    val right = TerminalExpression(charArray[++index].toString())
                    val addExpression = AddExpression(left, right)
                    stack.push(addExpression)
                }

                '-' -> {
                    val left = stack.pop()
                    val right = TerminalExpression(charArray[++index].toString())
                    val subtractExpression = SubtractExpression(left, right)
                    stack.push(subtractExpression)
                }

                else -> {
                    val expression = TerminalExpression(it.toString())
                    stack.push(expression)
                }
            }
            index++
        }

        return stack.pop()

    }

}