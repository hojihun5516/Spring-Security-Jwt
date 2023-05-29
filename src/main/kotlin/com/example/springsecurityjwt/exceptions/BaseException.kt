package com.example.springsecurityjwt.exceptions

import java.text.MessageFormat

open class BaseException(
    val status: Int,
    private val code: String,
    private var default: String? = null,
    args: Array<out Any?>? = null
) : RuntimeException(code) {
    init {
        if (args != null && args.isNotEmpty() && default != null) {
            val javaArgs = args.filterNotNull().toTypedArray()
            default = MessageFormat.format(default, *javaArgs)
        }
    }

    open fun toResult() = ErrorResult(code, default ?: "")
}
