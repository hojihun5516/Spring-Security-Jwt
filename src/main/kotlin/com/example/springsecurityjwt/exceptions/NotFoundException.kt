package com.example.springsecurityjwt.exceptions

open class NotFoundException(pair: Pair<String, String>, vararg args: Any?) :
    BaseException(404, pair.first, pair.second, args)
