package com.kronos.sample.uitls

import android.content.Context

/**
 *
 *  @Author LiABao
 *  @Since 2021/8/2
 *
 */
object EpicHook {

    fun hookManager(context: Context) {
        val steam = context.resources.assets.open("dynamic.json")
        val configEntity = GsonUtils.inflate(steam)
        configEntity.methods.forEach {
            start(it)
        }
    }


    fun start(entity: DynamicEntity) {
        if (entity.name_regex.isNotEmpty()) {
            val methodName = entity.name_regex.substring(entity.name_regex.lastIndexOf(".") + 1)
            val className = entity.name_regex.substring(0, entity.name_regex.lastIndexOf("."))
            val lintClass = Class.forName(className)
               /*DexposedBridge.hookAllMethods(lintClass, methodName, object : XC_MethodHook() {
                   override fun beforeHookedMethod(param: MethodHookParam?) {
                       super.beforeHookedMethod(param)

                       Log.i("EpicHook", "EpicHook")
                   }
               })*/
        }
    }
}