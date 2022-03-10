package com.share.domain.exceptions

/**
 * @author Juan Sebastian Niño
 */
data class ConnectionException(
    override val message: String = "Connectivity issue"
) : Exception()