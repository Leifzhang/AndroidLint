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
                methods.forEach {
                    it.excludes?.apply {
                        println("excludes:$this \r\n")
                    }
                }
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
        f = File(projectDir.parentFile.absolutePath + "/.codequality", DYNAMIC_FILE_NAME)
        if (f.exists()) {
            return f
        }

        return null
    }

    private const val DYNAMIC_FILE_NAME = "dynamic.json"
}