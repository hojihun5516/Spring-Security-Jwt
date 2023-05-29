package com.example.springsecurityjwt.exceptions

open class InvalidValueException(pair: Pair<String, String>, vararg args: Any?) :
    BaseException(400, pair.first, pair.second, args)
