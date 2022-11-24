package com.payap.lienmallserver.common.exceptions

class NotFoundExceptions : RuntimeException {
    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}