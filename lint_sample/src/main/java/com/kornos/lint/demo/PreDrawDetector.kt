package com.kornos.lint.demo

import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*
import java.util.ArrayList

/**
 *
 *  @Author LiABao
 *  @Since 2022/1/13
 *
 */
class PreDrawDetector : Detector(), SourceCodeScanner {


    companion object {
        val ISSUE = Issue.create(
            "PreDrawDetector",
            "我不想让你们用呢",
            "从View、Fragment等处获取的Context对象，有时候不等于其所处的Activity对象，而是一个包裹了Activity的ContextWrapper。" +
                    "这时，直接对Context引用进行类型转换往往会产生不正确的结果。" +
                    "使用Context.findActivityOrNull()或Context.requireActivity()来获取Context中对应的Activity对象。",
            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            Implementation(PreDrawDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun applicableSuperClasses(): List<String> {
        return mutableListOf<String>().apply {
            add("android.view.ViewTreeObserver.OnPreDrawListener")
        }
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        super.visitClass(context, declaration)
        context.report(
            ISSUE,
            context.getLocation(declaration as UElement),
            "请使用项目提供的路由中间件"
        )

    }

    override fun visitClass(context: JavaContext, lambda: ULambdaExpression) {
        super.visitClass(context, lambda)
        context.report(
            ISSUE,
            context.getLocation(lambda as UElement),
            "请使用项目提供的路由中间件"
        )
    }
}