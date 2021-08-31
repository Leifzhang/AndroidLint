package com.kornos.lint.demo.entity

import com.google.gson.Gson
import java.io.File

/**
 *
 *  @Author LiABao
 *  @Since 2021/4/20
 *
 */
object GsonUtils {

    fun inflate(projectDir: File): DynamicConfigEntity {
        val gson = Gson()
        val f = getFile(projectDir)
        return f?.let { file ->
            gson.fromJson(file.bufferedReader(), DynamicConfigEntity::class.java).apply {
            }
        } ?: DynamicConfigEntity()
    }


    private fun getFile(projectDir: File): File? {
        var f = File(projectDir.parentFile, DYNAMIC_FILE_NAME)
        if (f.exists()) {
            return f
        }
        f = File(projectDir, DYNAMIC_FILE_NAME)
        if (f.exists()) {
            return f
        }
        val codeQualifiedFile = findCodeQuality(projectDir) ?: return null
        println("codeQualifiedFile:${codeQualifiedFile.path} \r\n")
        f = File(codeQualifiedFile, DYNAMIC_FILE_NAME)
        if (f.exists()) {
            return f
        }
        return null
    }

    private fun findCodeQuality(projectDir: File): File? {
        if (projectDir.parent != null) {
            val parent = projectDir.parentFile
            val file = parent.listFiles()?.firstOrNull {
                it.name == ".codequality" && it.isDirectory
            }
            return file ?: findCodeQuality(parent)
        }
        return null
    }


    private const val DYNAMIC_FILE_NAME = "dynamic.json"
}