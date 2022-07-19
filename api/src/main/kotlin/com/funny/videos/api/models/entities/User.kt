package com.funny.videos.api.models.entities

import com.funny.videos.api.annotations.NoArgsConstructor
import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.ibatis.annotations.AutomapConstructor

@NoArgsConstructor
data class User @AutomapConstructor constructor(
    val id: Long = 0L,
    val username: String,
    @JsonIgnore
    val password: String?,
    val active: Boolean
)