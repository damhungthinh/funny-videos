package com.funny.videos.api.models.responses

import com.funny.videos.api.models.CustomPageable
import com.funny.videos.api.models.entities.SharedVideo

class SharedVideoPagination (content: List<SharedVideo>, pagable: CustomPageable) :
    PaginationResponse<SharedVideo>(content, pagable)