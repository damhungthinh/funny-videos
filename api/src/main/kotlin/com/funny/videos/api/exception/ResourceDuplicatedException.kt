package com.funny.videos.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceDuplicatedException(
    private val resource: String
): BusinessException(
    message = "Duplicate $resource value.",
    reason = "$resource was existed! Please choose other value, then try again."
)