package com.textgame.domain.model

data class AttributeCategory(
    val name: String,
    val type: AttributeType,
    val minValue: Double? = null,
    val maxValue: Double? = null,
    val defaultValue: Any? = null,
    val enumOptions: List<String> = emptyList(),
    val description: String = ""
)

enum class AttributeType {
    NUMERIC,
    BOOLEAN,
    ENUM,
    TEXT
}
