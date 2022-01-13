package com.kornos.lint.demo

import com.android.tools.lint.detector.api.*
import com.kornos.lint.demo.privacy.PrivacyAsmEntity
import com.kornos.lint.demo.privacy.PrivacyHelper
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
class PrivacyClassDetector : Detector(), Detector.ClassScanner {

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
    }

    override fun getApplicableAsmNodeTypes(): IntArray? {
        return intArrayOf(AbstractInsnNode.METHOD_INSN)
    }


    override fun checkInstruction(
        context: ClassContext,
        classNode: ClassNode,
        method: MethodNode,
        instruction: AbstractInsnNode
    ) {
        super.checkInstruction(context, classNode, method, instruction)
        if (instruction is MethodInsnNode) {
            if (instruction.isPrivacy() != null) {
                print("checkInstruction AbstractInsnNode:${instruction.opcode} \r\n")
                context.report(
                    ISSUE, context.getLocation(instruction),
                    "外圈q+内圈"
                )

            }
        }

    }

    private fun MethodInsnNode.isPrivacy(): PrivacyAsmEntity? {
        val pair = PrivacyHelper.privacyList.firstOrNull {
            val first = it
            first.owner == owner && first.code == opcode && first.name == name && first.desc == desc
        }
        return pair

    }

    companion object {
        val ISSUE = Issue.create(
            "ClassSampleDetector",  //唯一 ID
            "咦 ",  //简单描述
            "我只是想让你报错而已",  //详细描述
            Category.CORRECTNESS,  //问题种类（正确性、安全性等）
            6, Severity.ERROR,  //问题严重程度（忽略、警告、错误）
            Implementation( //实现，包括处理实例和作用域
                PrivacyClassDetector::class.java,
                Scope.CLASS_FILE_SCOPE
            )
        )
    }
}