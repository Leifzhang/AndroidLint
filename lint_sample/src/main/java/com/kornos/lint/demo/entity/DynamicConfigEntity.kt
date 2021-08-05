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
    val message: String,
    val excludes: MutableList<String>?
)

