package com.bilibili.plugin.lint_sample_2

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.Companion.JAVA_FILE_SCOPE
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.getQualifiedName
import org.jetbrains.uast.util.isConstructorCall

/**
 * Author: Omooo
 * Date: 2019/7/5
 * Desc: Avoid call new Thread() directly
 */
class ThreadDetector : Detector(), Detector.UastScanner {
    private val NEW_THREAD = "java.lang.Thread"

    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                if (!node.isConstructorCall()) {
                    return
                }
                val className: String
                val classRef = node.classReference
                if (classRef != null) {
                    className = classRef.getQualifiedName().toString()
                    if (NEW_THREAD == className && context.project.isAndroidProject) {
                        context.report(
                            ISSUE,
                            node,
                            context.getLocation(node),
                            "\u21E2 Avoid call new Thread() directly"
                        )
                    }
                }
            }
        }
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            "ThreadUsage",
            "Thread Usage",
            "Please use ThreadPool,such as AsyncTask.SERIAL_EXECUTOR",
            CORRECTNESS,
            6, Severity.WARNING,
            Implementation(ThreadDetector::class.java, JAVA_FILE_SCOPE)
        )
    }
}