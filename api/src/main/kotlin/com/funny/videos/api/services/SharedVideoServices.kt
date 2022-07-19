package com.funny.videos.api.services

import com.funny.videos.api.models.Conditions
import com.funny.videos.api.models.CustomPageable
import com.funny.videos.api.models.entities.SharedVideo
import com.funny.videos.api.models.entities.User
import com.funny.videos.api.models.requests.SharedConditions
import com.funny.videos.api.models.requests.SharedVideoRequest
import com.funny.videos.api.models.responses.SharedVideoPagination
import com.funny.videos.api.repositories.SharedVideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SharedVideoServices {

    @Autowired
    private lateinit var repository: SharedVideoRepository

    /**
     * Find shared videos by [conditions]
     * @return result wrapped in pagination objects
     */
    fun findSharedVideos(conditions: SharedConditions): SharedVideoPagination {
        val sharedVideos = repository.findByConditions(conditions)

        val pageable = CustomPageable(
            totalElements = sharedVideos.firstOrNull()?.totalElements ?: 0,
            page = conditions.page,
            size = conditions.size
        ).toValidPagable()

        return SharedVideoPagination(
            content = sharedVideos,
            pagable = pageable
        )
    }

    /**
     * Insert new shared videos' link to database
     */
    fun insert(request: SharedVideoRequest, auth: User) {
        val url = request.url!!
        val key = url.substring(
            url.lastIndexOf("/")
        )
        SharedVideo(
            active = true,
            videoUrl = url,
            videoKey = key,
            sharedBy = auth
        ).let {
            repository.insert(it)
        }
    }
}