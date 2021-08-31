package com.kornos.lint.demo

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClass
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

/**
 *
 *  @Author LiABao
 *  @Since 2021/8/4
 *
 */
class ClassSampleDetector : Detector(), Detector.ClassScanner {

    override fun checkClass(context: ClassContext, classNode: ClassNode) {
        super.checkClass(context, classNode)
    }

    override fun checkCall(
        context: ClassContext,
        classNode: ClassNode,
        method: MethodNode,
        call: MethodInsnNode
    ) {
        super.checkCall(context, classNode, method, call)
        print("classNode:${classNode.name} MethodNode:${method.name} \r\n")
    }

    override fun getApplicableAsmNodeTypes(): IntArray? {
        return intArrayOf(AbstractInsnNode.METHOD_INSN, AbstractInsnNode.FIELD_INSN)
    }


    override fun checkInstruction(
        context: ClassContext,
        classNode: ClassNode,
        method: MethodNode,
        instruction: AbstractInsnNode
    ) {
        super.checkInstruction(context, classNode, method, instruction)
        if (classNode.name == "com/kronos/sample/Event" && method.name == "unknownNameFun") {
            print("checkInstruction AbstractInsnNode:${instruction.opcode} \r\n")
            context.report(
                ISSUE, context.getLocation(instruction),
                "外圈q+内圈"
            )

        }
    }

    companion object {
        val ISSUE = Issue.create(
            "ClassSampleDetector",  //唯一 ID
            "咦 ",  //简单描述
            "我只是想让你报错而已",  //详细描述
            Category.CORRECTNESS,  //问题种类（正确性、安全性等）
            6, Severity.ERROR,  //问题严重程度（忽略、警告、错误）
            Implementation( //实现，包括处理实例和作用域
                ClassSampleDetector::class.java,
                Scope.CLASS_FILE_SCOPE
            )
        )
    }
}