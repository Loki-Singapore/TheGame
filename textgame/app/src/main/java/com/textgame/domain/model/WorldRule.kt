package com.textgame.domain.model

data class WorldRule(
    val id: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

fun generateNextRuleId(existingRules: List<WorldRule>): String {
    val maxNum = existingRules.maxOfOrNull { rule ->
        rule.id.removePrefix("rule_").toIntOrNull()
    } ?: 0
    return "rule_${(maxNum + 1).toString().padStart(3, '0')}"
}
