package com.xxd.apt

import com.xxd.apt_annotation.AnnotationTest
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

/**
 *    author : xxd
 *    date   : 2020/8/10
 *    desc   : 注解处理器测试类
 *    1. 定义一个注解
 *    2. 定义一个类 继承 AbstractProcessor
 *    3. 将这个类注册到 META-INF/services/javax.annotation.processing.Processor 文件夹下，
 *       在gradle 3.0 后不支持 autoService 自动生成，最好手写，不能错一个字符，否则找不到（文件夹
 *       需要一个一个创建）
 *    4. 把需要解析的Annotation注册，通过SupportedAnnotationTypes 或者复写 getSupportedAnnotationTypes
 *    5. 需要引用这个 processor 的地方引入，注意：java工程中通过 annotationProcessor 引入，kotlin工程
 *       中通过 kapt 引入
 */
//@SupportedAnnotationTypes("com.xxd.apt_annotation.AnnotationTest")
class TestProcessor : AbstractProcessor() {

    // 打印用的
    private lateinit var message: Messager

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {

        message.printMessage(Diagnostic.Kind.NOTE, "~~~~~~~~自定义注解处理开始~~~~~~~~~~~\r\n")

        // 获取所有的 AnnotationTest 注解
        val elements = p1!!.getElementsAnnotatedWith(AnnotationTest::class.java)
        for (element in elements) {
            element as VariableElement
            val annotationTest = element.getAnnotation(AnnotationTest::class.java)
            message.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(
                    "~~~~~value=%s , code=%d~~~~",
                    annotationTest.value,
                    annotationTest.code
                )
            )
        }
        return false
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        message = p0!!.messager
        message.printMessage(Diagnostic.Kind.NOTE, "~~~~~~~~自定义注解初始化~~~~~~~~~~~\r\n")
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        // 必须将需要处理的注解加入返回的集合中，才能识别
        val supportTypes = mutableSetOf<String>()
        supportTypes.add(AnnotationTest::class.java.canonicalName)
        return supportTypes
    }
}