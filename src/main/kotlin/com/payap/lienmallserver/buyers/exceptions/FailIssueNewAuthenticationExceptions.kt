package com.payap.lienmallserver.buyers.exceptions

class FailIssueNewAuthenticationExceptions : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}