package com.kornos.lint.demo

import com.android.tools.lint.detector.api.*

/**
 *
 *  @Author LiABao
 *  @Since 2022/11/10
 *
 */
class KAEDetector : Detector(), Detector.GradleScanner {

    override fun checkMethodCall(
        context: GradleContext,
        statement: String,
        parent: String?,
        namedArguments: Map<String, String>,
        unnamedArguments: List<String>,
        cookie: Any
    ) {
        val plugin = namedArguments["plugin"]
        if (statement == "apply" && parent == null) {
            if (plugin == "kotlin-android-extensions") {
                report(context, cookie, ISSUE, "boom")
                return
            }
        }
     /*   if (statement == "plugins") {
            unnamedArguments.forEach {
                if (it.contains("kotlin-android-extensions")) {
                    report(context, cookie, ISSUE, "boom")
                    return
                }
            }
        }*/
        if (parent == "plugins") {
            if (statement == "id") {
                unnamedArguments.forEach {
                    if (it.contains("kotlin-android-extensions")) {
                        report(context, cookie, ISSUE, "boom")
                    }
                }
            }
        }
    }

    private fun report(
        context: Context,
        cookie: Any,
        issue: Issue,
        message: String,
        fix: LintFix? = null
    ) {
        // Some methods in GradleDetector are run without the PSI read lock in order
        // to accommodate network requests, so we grab the read lock here.
        context.client.runReadAction(Runnable {
            if (context.isEnabled(issue) && context is GradleContext) {
                // Suppressed?
                // Temporarily unconditionally checking for suppress comments in Gradle files
                // since Studio insists on an AndroidLint id prefix
                val checkComments = /*context.getClient().checkForSuppressComments() &&*/
                    context.containsCommentSuppress()
                if (checkComments && context.isSuppressedWithComment(cookie, issue)) {
                    return@Runnable
                }
                val location = context.getLocation(cookie)
                context.report(issue, location, message, fix)
            }
        })
    }


    companion object {
        @JvmField
        val ISSUE = Issue.create(
            "KAEDetector",
            "我不想让你们用呢",
            "从View、Fragment等处获取的Context对象，有时候不等于其所处的Activity对象，而是一个包裹了Activity的ContextWrapper。" + "这时，直接对Context引用进行类型转换往往会产生不正确的结果。" + "使用Context.findActivityOrNull()或Context.requireActivity()来获取Context中对应的Activity对象。",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            Implementation(KAEDetector::class.java, Scope.GRADLE_SCOPE)
        )
    }
}