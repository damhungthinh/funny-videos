package com.funny.videos.api.models.requests

import com.funny.videos.api.models.Conditions

class SharedConditions(page: Int = 0, size: Int = 20, sortBy: String? = "id, DESC") :
    Conditions(page, size, sortBy) {

    /** List of order columns */
    override val order_columns: Map<String, String>
        get() = mapOf(
            "id" to "shared_videos.id",
            "author" to "users.username"
        )
}