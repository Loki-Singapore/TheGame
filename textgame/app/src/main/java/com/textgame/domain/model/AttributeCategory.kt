package com.textgame.domain.model

/**
 * 表格属性的列定义。TABLE 类型属性由若干列组成，每列有自己的标量类型。
 * 列类型不允许嵌套 TABLE，只能是 NUMERIC / BOOLEAN / ENUM / TEXT。
 */
data class TableColumn(
    val name: String,
    val type: AttributeType,
    val enumOptions: List<String> = emptyList(),
    val description: String = ""
)

data class AttributeCategory(
    val name: String,
    val type: AttributeType,
    val minValue: Double? = null,
    val maxValue: Double? = null,
    val defaultValue: Any? = null,
    val enumOptions: List<String> = emptyList(),
    val description: String = "",
    // 仅 type == TABLE 时使用：定义表格的列结构。
    val columns: List<TableColumn> = emptyList()
)

enum class AttributeType {
    NUMERIC,
    BOOLEAN,
    ENUM,
    TEXT,
    // 表格类型：值为一组行（List<Map<String, Any>>），每行按 [columns] 定义的字段存放。
    // 适合记录技能列表、装备栏、任务清单、关系网络等结构化数据。
    TABLE
}
