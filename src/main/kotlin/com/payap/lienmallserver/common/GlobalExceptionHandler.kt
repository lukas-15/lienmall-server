package com.payap.lienmallserver.common

import com.payap.lienmallserver.authentications.exceptions.ExpiredTokenExceptions
import com.payap.lienmallserver.buyers.exceptions.FailIssueNewAuthenticationExceptions
import com.payap.lienmallserver.common.exceptions.AlreadyExistExceptions
import com.payap.lienmallserver.common.exceptions.NotFoundExceptions
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [AlreadyExistExceptions::class])
    fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

    @ExceptionHandler(value = [NotFoundExceptions::class])
    fun handleNotFound(ex: RuntimeException, request: WebRequest): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [FailIssueNewAuthenticationExceptions::class, ExpiredTokenExceptions::class])
    fun handleFailedNewAuthentication(ex: RuntimeException, request: WebRequest): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request)
    }
}