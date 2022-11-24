package com.payap.lienmallserver.authentications.exceptions

class ExpiredTokenExceptions : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}