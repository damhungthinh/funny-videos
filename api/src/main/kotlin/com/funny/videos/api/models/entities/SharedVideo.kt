package com.funny.videos.api.models.entities

import com.funny.videos.api.annotations.NoArgsConstructor
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.ibatis.annotations.AutomapConstructor
import java.time.LocalDateTime

@NoArgsConstructor
data class SharedVideo @AutomapConstructor constructor(
    val id: Long = 0L,
    val videoKey: String,
    val videoUrl: String,
    val active: Boolean,
    val sharedBy: User,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    val sharedAt: LocalDateTime? = null,

    @JsonIgnore
    val totalElements: Long = 0L
)