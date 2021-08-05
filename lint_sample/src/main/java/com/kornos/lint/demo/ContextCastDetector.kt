package com.kornos.lint.demo

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.TextFormat
import org.jetbrains.uast.UBinaryExpressionWithType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UastBinaryExpressionWithTypeKind

class ContextCastDetector : Detector(), SourceCodeScanner {
    companion object {
        val ISSUE = Issue.create(
            "ContextCast",
            "不要对context引用进行类型检查和类型转换。",
            "从View、Fragment等处获取的Context对象，有时候不等于其所处的Activity对象，而是一个包裹了Activity的ContextWrapper。" +
                "这时，直接对Context引用进行类型转换往往会产生不正确的结果。" +
                "使用Context.findActivityOrNull()或Context.requireActivity()来获取Context中对应的Activity对象。",
            Category.CORRECTNESS,
            7,
            Severity.WARNING,
            Implementation(ContextCastDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }


    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UBinaryExpressionWithType::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitBinaryExpressionWithType(node: UBinaryExpressionWithType) {
                if (node.operand.getExpressionType()?.canonicalText == "android.content.Context" &&
                    (node.operationKind is UastBinaryExpressionWithTypeKind.TypeCast || node.operationKind is UastBinaryExpressionWithTypeKind.InstanceCheck) &&
                    node.type.canonicalText != "android.content.ContextWrapper" &&
                    node.type.canonicalText != "android.app.Application"
                ) {
                    val location = context.getLocation(node)
                    context.report(ISSUE, location, ISSUE.getBriefDescription(TextFormat.TEXT))
                }
            }
        }
    }
}
