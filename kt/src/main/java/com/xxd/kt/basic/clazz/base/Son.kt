package com.xxd.kt.basic.clazz.base

/**
 *    author : xxd
 *    date   : 2026/3/6
 *    desc   : 继承的各种写法
 */

// 1. 直接给父类的构造函数赋值
internal class Son1 : FatherClazz(1, "a") {
}

// 2. 在构造函数中调用父类的构造函数，与1反编译后相同 (tips:这种写法的继承，父类不带())
internal class Son2 : FatherClazz {

    constructor() : super(1, "a") {
    }
}

// 3. 从自己的构造函数取值，调用父类的构造函数
internal class Son3 constructor(val j: Int, val s2: String) : FatherClazz(j, s2) {
}

// 4. 构造函数写法不同，这里的 j,s2 不是成员变量 (tips:这种写法的继承，父类不带())
internal class Son4 : FatherClazz {

    constructor(j: Int, s2: String) : super(j, s2) {
    }
}

// 5. 构造函数接收变量，经过逻辑变换后调用父类构造函数 （利用次构造函数的“委托链”）
internal class Son5 : FatherClazz {

    // 调用 this or super, 必须写在第一行，导致没法进行复杂的逻辑处理
    constructor(map: Map<String, Any>) : this(
        map["id"] as Int,
        map["describe"] as String
    )

    private constructor(id: Int, describe: String) : super(id, describe) {

    }
}

// 6. 推荐写法，使用伴生对象工厂方法（最推荐）
internal class Son6 private constructor(id: Int, describe: String) : FatherClazz(id, describe) {
    // 私有化构造函数，只能通过工厂方法创建

    companion object {
        // 使用 operator invoke 可以让调用处看起来像普通构造函数：Son5(map)
        operator fun invoke(map: Map<String, Any>): Son6 {
            // 在这里执行复杂的逻辑
            val id = map["id"] as? Int ?: 0
            val describe = map["describe"] as? String ?: ""

            return Son6(id, describe)
        }

        // 不使用 operator, 外部调用需要加上方法名 m2
        fun m1(map: Map<String, Any>): Son6 {
            return Son6(1, "a")
        }
    }
}

// 7. 借用外部函数处理解析构造函数参数
internal class Son7(val map: Map<String, Any>) : FatherClazz(parseId(map), parseDescribe(map)) {
    // 这种方式的缺点是 map 可能会被解析两次

    companion object {
        // 在类外部或伴生对象中定义纯函数
        fun parseId(map: Map<String, Any>): Int {
            return map["id"] as? Int ?: 0
        }

        fun parseDescribe(map: Map<String, Any>): String {
            return map["describe"] as? String ?: ""
        }

    }
}


fun main() {
    val map = mapOf("id" to 1, "describe" to "a")
    // 这样写看起来像调用类，其实是调用函数
    Son6(map)

    Son6.m1(map)
}


