package com.funny.videos.api.models

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlin.math.abs
import kotlin.math.ceil

/**
 * The paging information for pagination
 */
data class CustomPageable(
    /** Total elements found in query */
    val totalElements: Long = 0L,

    /** Current page */
    val page: Int = 0,

    /** Page size */
    val size: Int = 20
) {
    /** Get total pages base on total elements and page size */
    val totalPages = ceil(x = totalElements.toDouble() / size).toInt()

    /** Is current page first page of whole pages */
    val isFirst = page == 0

    /** Is current page last page of whole pages */
    val isLast = page == totalPages

    /** Number of elements in current page */
    val numberOfElements = if (isLast) {
        abs( n = totalElements - page * size)
    } else {
        // Number of page will be page size if there is not a last page
        size
    }

    /**
     * Re-mapping pagable value if totalElements = 0
     * Note: This function WILL NOT response in JSON
     * */
    @JsonIgnore
    fun toValidPagable(): CustomPageable {
        // Back to first page if there is no items found in query
        if (totalElements ==  0L) return this.copy(page = 0)
        return this
    }
}