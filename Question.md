### 问题记录
1. 为什么 buildscript 中定义的成员变量 ext.kotlin_version 可以在外部引入的 config.build 中找到，而反之不行
    answer: 因为 buildscript 先于 config.build 中执行，原理是gradle加载的机制，必须先加载自己的脚本