package com.funny.videos.api.utils

import com.funny.videos.api.exception.ValidationException

fun Map<String, Any>.throwIfNotEmpty(reason: String? = null) {
    if (this.isNotEmpty() || reason != null) {
        throw ValidationException(this, reason)
    }
}