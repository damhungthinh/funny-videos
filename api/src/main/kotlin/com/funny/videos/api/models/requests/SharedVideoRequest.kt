package com.funny.videos.api.models.requests

import com.funny.videos.api.exception.ValidationException
import com.funny.videos.api.utils.throwIfNotEmpty
import java.net.URL

data class SharedVideoRequest(
    val url: String?
): BaseRequest<SharedVideoRequest>  {
    /**
     * Validate request value
     */
    override fun validate() = mutableMapOf<String, String>().also {
        if (url.isNullOrBlank() || url.isEmpty()) {
            it[this::url.name] = ValidationException.required()
        } else {
            try {
                URL(url.trim()).toURI()
            } catch (e: Exception) {
                it[this::url.name] = ValidationException.url()
            }
        }
    }.throwIfNotEmpty()

    /**
     * Trim and convert values
     */
    override fun toValidRequest() = this.copy(
        url = this.url?.trim()
    )
}