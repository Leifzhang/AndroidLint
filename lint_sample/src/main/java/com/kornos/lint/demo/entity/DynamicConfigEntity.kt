package com.kornos.lint.demo.entity

/**
 * @Author LiABao
 * @Since 2021/7/20
 */
class DynamicConfigEntity {
    val methods = mutableListOf<DynamicEntity>()

    val constructions = mutableListOf<DynamicEntity>()
}

data class DynamicEntity(
    val name_regex: String,
    val message: String
)


fun parser(map: LinkedHashMap<Any, Any>): DynamicEntity {
    val name = map["name_regex"].toString()
    val message = map["message"].toString()
    return DynamicEntity(name, message)
}