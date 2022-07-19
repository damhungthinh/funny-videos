package com.funny.videos.api.models.responses

import com.funny.videos.api.models.CustomPageable

/**
 * The Pagination response for all list/search items
 */
abstract class PaginationResponse<E>(
    val content: List<E>,
    val pageable: CustomPageable
)