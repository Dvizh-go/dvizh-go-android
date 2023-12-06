package com.start.eventgo.network

/**
 * @author marshal@kolesa.kz
 */
class DefaultApiErrorExceptionFactory() : ApiErrorExceptionFactory {

    override fun createException(
        httpStatusCode: Int,
        message: String?
    ): Exception = Exception(message)
}
