package com.kornos.lint.demo

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.kornos.lint.demo.entity.DynamicConfigEntity
import com.kornos.lint.demo.entity.GsonUtils
import com.kornos.lint.demo.entity.LintMatcher
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.util.isConstructorCall
import org.jetbrains.uast.util.isMethodCall
import com.kornos.lint.demo.entity.report

/**
 *
 *  @Author LiABao
 *  @Since 2021/7/20
 *
 */
class DynamicLint : Detector(), Detector.UastScanner {

    lateinit var globalConfig: DynamicConfigEntity


    override fun beforeCheckRootProject(context: Context) {
        super.beforeCheckRootProject(context)
        println("beforeCheckRootProject:" + context.mainProject + "\r\n")
        globalConfig = GsonUtils.inflate(context.project.dir)
        println("dynamicConfig:" + globalConfig.methods.size + "\r\n")
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UCallExpression::class.java, UClass::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {

            override fun visitCallExpression(node: UCallExpression) {
                if (node.isMethodCall()) {
                    checkMethodCall(context, node)
                } else if (node.isConstructorCall()) {
                    checkConstructorCall(context, node)
                }
            }

            override fun visitClass(node: UClass) {
                checkInheritClass(context, node)
            }
        }
    }

    private fun checkMethodCall(context: JavaContext, node: UCallExpression) {
        globalConfig.methods.forEach {
            if (LintMatcher.matchMethod(it, node)) {
                context.report(ISSUE, context.getLocation(node), it)
                return
            }
        }
    }

    private fun checkConstructorCall(context: JavaContext, node: UCallExpression) {
        globalConfig.constructions.forEach {
            if (LintMatcher.matchConstruction(it, node)) {
                context.report(ISSUE, context.getLocation(node), it)
                return
            }
        }

    }

    private fun checkInheritClass(context: JavaContext, node: UClass) {
        /* globalConfig.inherit.forEach { avoidInheritClass ->
             if (LintMatcher.matchInheritClass(
                     avoidInheritClass,
                     node
                 )
             ) {
                 context.report(
                     ISSUE,
                     context.getLocation(node as UElement),
                     avoidInheritClass
                 )
                 return
             }
         }*/
    }

    companion object {
        val ISSUE = Issue.create(
            "file_path_issue",  //唯一 ID
            "该路径是保护路径,当前不允许直接使用",  //简单描述
            "当前项目已经集成了图片中间件，所有图片操作全部移动到中间件操作",  //详细描述
            Category.CORRECTNESS,  //问题种类（正确性、安全性等）
            6, Severity.ERROR,  //问题严重程度（忽略、警告、错误）
            Implementation( //实现，包括处理实例和作用域
                DynamicLint::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

}