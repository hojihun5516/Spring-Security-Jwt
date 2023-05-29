package com.example.springsecurityjwt.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

object LocalDateTimeExtensions {
    fun LocalDateTime.toDate() = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())!!
}
