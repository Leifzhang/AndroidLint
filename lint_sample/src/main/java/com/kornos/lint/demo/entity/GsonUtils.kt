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
        val f = File(projectDir.parentFile, "dynamic.json")
        val repoInfo = gson.fromJson(f.bufferedReader(), DynamicConfigEntity::class.java)
        return repoInfo
    }

}