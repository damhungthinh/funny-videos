package com.funny.videos.api.repositories

import com.funny.videos.api.models.Conditions
import com.funny.videos.api.models.entities.SharedVideo
import org.springframework.stereotype.Repository

@Repository
interface SharedVideoRepository : CrudRepository<SharedVideo, Long> {
    /**
     * Find items by pagination information
     */
    fun findByConditions(conditions: Conditions): List<SharedVideo>
}