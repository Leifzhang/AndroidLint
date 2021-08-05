package com.kronos.sample

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 *
 *  @Author LiABao
 *  @Since 2021/6/23
 *
 */
class FileHelper {
    @SuppressLint("file_path_issue")
    @WorkerThread
    fun write(fileName: String, block: (FileInputStream) -> R) = apply {
        val file = File("Pictures", fileName)
        if (file.exists()) {

        }
        val fileInputStream = FileInputStream(file)
        fileInputStream.use { block.invoke(it) }
    }

    @SuppressLint("file_path_issue")
    @WorkerThread
    fun writeOverWrite(fileName: String, block: (FileInputStream) -> R) = apply {
        val file = File("Pictures", fileName)
        if (file.exists()) {
         //   return
        }
        val fileInputStream = FileInputStream(file)
        fileInputStream.use { block.invoke(it) }
    }

    @SuppressLint("file_path_issue")
    @WorkerThread
    fun read(fileName: String, block: (FileOutputStream) -> R) = apply {
        val file = File("Pictures", fileName)
        val fileInputStream = FileOutputStream(file)
        fileInputStream.use { block.invoke(it) }
    }
}