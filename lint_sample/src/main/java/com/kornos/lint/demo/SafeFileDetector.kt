package com.kornos.lint.demo

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.CORRECTNESS
import com.android.tools.lint.detector.api.Scope.Companion.JAVA_FILE_SCOPE
import com.kornos.lint.demo.entity.GsonUtils
import org.jetbrains.uast.*
import org.jetbrains.uast.util.isMethodCall

/**
 *
 *  @Author LiABao
 *  @Since 2021/6/18
 *
 */
class SafeFileDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        val types: MutableList<Class<out UElement?>> = mutableListOf()
        types.add(UCallExpression::class.java)
        types.add(UVariable::class.java)
        return types
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return SafeFileHandler(context)
    }

    override fun beforeCheckFile(context: Context) {
        super.beforeCheckFile(context)
        println("beforeCheckFile:" + context.project + "\r\n")
    }

    override fun beforeCheckEachProject(context: Context) {
        super.beforeCheckEachProject(context)
        println("beforeCheckEachProject:" + context.project + "\r\n")
    }

    override fun beforeCheckRootProject(context: Context) {
        super.beforeCheckRootProject(context)
        println("beforeCheckRootProject:" + context.mainProject + "\r\n")
        val dynamicConfig = GsonUtils.inflate(context.project.dir)
        println("dynamicConfig:" + dynamicConfig.methods.size + "\r\n")
    }

    companion object {
        val ISSUE = Issue.create(
            "file_path_issue",  //唯一 ID
            "该路径是保护路径,当前不允许直接使用",  //简单描述
            "当前项目已经集成了图片中间件，所有图片操作全部移动到中间件操作",  //详细描述
            CORRECTNESS,  //问题种类（正确性、安全性等）
            6, Severity.ERROR,  //问题严重程度（忽略、警告、错误）
            Implementation( //实现，包括处理实例和作用域
                SafeFileDetector::class.java,
                JAVA_FILE_SCOPE
            )
        )
    }
}

class SafeFileHandler(private val context: JavaContext) : UElementHandler() {
    override fun visitCallExpression(node: UCallExpression) {
        checkIsMethodCall(node)
        checkIsConstructorCall(node)
    }

    override fun visitVariable(node: UVariable) {
        try {
            val value = node.uastInitializer
            value?.apply {
                checkUQualifiedReferenceExpression(node, value)
                if (this is ULiteralExpression) {
                    checkULiteralExpression(node, this)
                }
            }
        } catch (e: Exception) {

        }
    }


    private fun checkIsConstructorCall(node: UCallExpression) {
        val classRef = node.classReference
        try {
            if (classRef != null) {
                val args = node.valueArguments
                for (element in args) {
                    if (element is ULiteralExpression) {
                        checkULiteralExpression(node, element)
                    } else {
                        //   print("\r\n  ---------visitVariable:${element}  value:${element}--------- \r\n")
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun checkIsMethodCall(node: UCallExpression) {
        if (!node.isMethodCall()) {
            return
        }
        try {
            if (node.receiver != null && !node.methodName.isNullOrBlank()) {
                node.methodName
                val method = node.resolve()
                val value = "android.os.Environment"
                //  if (methodName == "getExternalStorageDirectory") {
                if (context.evaluator.isMemberInClass(method, value)) {
                    context.report(
                        SafeFileDetector.ISSUE, node, context.getLocation(node),
                        "请使用项目提供的路由中间件"
                    )
                }
                //   }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkUQualifiedReferenceExpression(uVariable: UVariable, node: UExpression) {
        if (node is UQualifiedReferenceExpression) {
            val text = uVariable.text
            if (text.contains("Environment.DIRECTORY_PICTURES")) {
                uVariable.toUElement()?.apply {
                    context.report(
                        SafeFileDetector.ISSUE, this, context.getLocation(this),
                        "请使用项目提供的路由中间件"
                    )
                }

            }
        }
    }

    private fun checkULiteralExpression(node: UElement, element: ULiteralExpression) {
        val stringValue = element.value
        if (stringValue == "Pictures") {
            context.report(
                SafeFileDetector.ISSUE, node, context.getLocation(node),
                "谁给你的胆子用空格的"
            )
        }
    }


}