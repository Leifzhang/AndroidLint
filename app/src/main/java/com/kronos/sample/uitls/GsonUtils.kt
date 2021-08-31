package com.kronos.sample.uitls

import com.google.gson.Gson
import java.io.File
import java.io.InputStream

/**
 *
 *  @Author LiABao
 *  @Since 2021/4/20
 *
 */
object GsonUtils {

    fun inflate(projectDir: InputStream?): DynamicConfigEntity {
        val gson = Gson()
        return projectDir?.let { file ->
            gson.fromJson(file.bufferedReader(), DynamicConfigEntity::class.java).apply {
                methods.forEach {
                    it.excludes?.apply {
                        println("excludes:$this \r\n")
                    }
                }
            }
        } ?: DynamicConfigEntity()
    }


    private const val DYNAMIC_FILE_NAME = "dynamic.json"
}