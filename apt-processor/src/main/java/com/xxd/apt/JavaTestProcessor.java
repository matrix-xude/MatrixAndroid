package com.xxd.apt;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * author : xxd
 * date   : 2020/8/11
 * desc   :
 */
@SupportedAnnotationTypes("com.xxd.apt_annotation.AnnotationTest")
public class JavaTestProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.WARNING, "TTTTTTTTTTTTTT\r\n");
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
