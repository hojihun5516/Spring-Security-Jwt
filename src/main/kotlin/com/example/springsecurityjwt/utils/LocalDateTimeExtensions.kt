package com.example.springsecurityjwt.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun LocalDateTime.toDate() = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())!!
