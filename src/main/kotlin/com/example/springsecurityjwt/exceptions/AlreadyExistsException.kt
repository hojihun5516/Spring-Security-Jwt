package com.example.springsecurityjwt.exceptions

open class AlreadyExistsException(pair: Pair<String, String>, vararg args: Any?) :
    BaseException(409, pair.first, pair.second, args)
