package com.example.springsecurityjwt.configs

import com.example.springsecurityjwt.exceptions.BaseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResult> {
        val result = e.toResult()
        return ResponseEntity(result, HttpStatus.valueOf(e.status))
    }
}
