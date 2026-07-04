// ponytail smoke-test 文件 — 故意过度设计，用来验证 Ponytail 规则/Skill 是否真的生效
// 用法：在手机 App 里发"审查这个文件的过度设计"或"ponytail-review"，看 AI 是否用
//       ponytail 的标签格式(delete:/stdlib:/native:)回复，并指出可删除项。
//       如果 AI 沉默地给出大段"改进建议"而不用 ponytail 标签 → Ponytail 没生效。
//       如果 AI 直接指出 stdlib 已有 email 校验、single 抽象无意义等 → 生效。
package com.example.textgame.ponytailtest

import java.util.regex.Pattern

// 1. 单实现的抽象工厂 — 经典 YAGNI 违反
interface EmailValidatorStrategy {
    fun validate(email: String): Boolean
}
class SimpleEmailValidatorStrategy : EmailValidatorStrategy {
    override fun validate(email: String): Boolean {
        // 2. 手写 email 正则 — Android 已有 android.util.Patterns.EMAIL_ADDRESS
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return pattern.matcher(email).matches()
    }
}
class EmailValidatorStrategyFactory {
    fun create(): EmailValidatorStrategy = SimpleEmailValidatorStrategy()
}

// 3. 手写的 debounce — Kotlin 协程已有等价能力，不该自己造
class Debouncer<T>(private val delayMs: Long) {
    private var lastJob: Thread? = null
    private var lastValue: T? = null
    fun submit(value: T, action: (T) -> Unit) {
        lastJob?.interrupt()
        lastValue = value
        lastJob = Thread {
            Thread.sleep(delayMs)
            action(lastValue!!)
        }.also { it.start() }
    }
}

// 4. "未来扩展"留的抽象基类，只有一个子类
abstract class BaseRepository<T> {
    abstract fun findAll(): List<T>
    abstract fun findById(id: Long): T?
}
class UserRepository : BaseRepository<String>() {
    private val users = mutableListOf("alice", "bob")
    override fun findAll(): List<String> = users.toList()
    override fun findById(id: Long): String? = users.getOrNull(id.toInt())
}
