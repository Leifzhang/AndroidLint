package com.kornos.lint.demo

import com.android.tools.lint.detector.api.*
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement

/**
 *
 *  @Author LiABao
 *  @Since 2022/11/9
 *
 */
class FragmentHiddenDetector : Detector(), Detector.UastScanner {

    override fun applicableSuperClasses(): List<String> {
        return mutableListOf<String>().apply {
            add("androidx.fragment.app.Fragment")
        }
    }

    override fun visitClass(context: JavaContext, declaration: UClass) {
        super.visitClass(context, declaration)
        declaration.methods.forEach {
            if (it.name == "onHiddenChanged" && it.uastParameters.size == 1) {
                context.report(
                    ISSUE, context.getLocation(it),
                    "凉凉了啊"
                )
            }
        }
    }


    companion object {
        val ISSUE = Issue.create(
            "FragmentHidden",
            "我不想让你们用呢",
            "从View、Fragment等处获取的Context对象，有时候不等于其所处的Activity对象，而是一个包裹了Activity的ContextWrapper。" +
                    "这时，直接对Context引用进行类型转换往往会产生不正确的结果。" +
                    "使用Context.findActivityOrNull()或Context.requireActivity()来获取Context中对应的Activity对象。",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            Implementation(FragmentHiddenDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }
}